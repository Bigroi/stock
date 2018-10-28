package com.bigroi.stock.controller.resource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Bid;
import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.controller.BaseResourseController;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.AddressService;
import com.bigroi.stock.service.BidService;
import com.bigroi.stock.service.LabelService;
import com.bigroi.stock.util.DateUtil;

public abstract class BidResourceController<B extends Bid, U> extends BaseResourseController{
	
	private static final String LABEL_PREFIX = "label.";
	
	@Autowired
	private AddressService addressService;
	
	@Autowired 
	protected LabelService labelService;
			
	protected abstract BidService<B> getBidService();
	
	protected abstract Function<B, U> getObjectForUIConstructor();
	
	protected abstract Class<B> getBidClass(); 
	
	protected abstract Class<U> getForUIClass(); 
	
	protected abstract String getPropertyFileName();
	
	protected abstract boolean isEmptyCategoryAllowed();
	
	protected ResultBean bidForm(long id) {
		B bid = getBidService().getById(id, getUserCompanyId());
		if (bid.getId() == -1){
			StockUser user = (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			bid.setAddressId(user.getCompany().getCompanyAddress().getId());
			bid.setCompanyAddress(user.getCompany().getCompanyAddress());
		}
		return new ResultBean(1, bid, null);
	}

	protected ResultBean bidList() {
		List<B> bids = getBidService().getByCompanyId(getUserCompanyId());
		List<U> objectForUI = bids.stream()
				.map((b) -> {
					b.getProduct().setName(labelService.getLabel(b.getProduct().getName(), "name", getLanguage()));
					return getObjectForUIConstructor().apply(b);
				})
				.collect(Collectors.toList());
		Class<U> clazz = getForUIClass();
		TableResponse<U> table = new TableResponse<>(clazz, objectForUI);
		return new ResultBean(1, table, null);
	}

	protected ResultBean saveBid(String json) {
		B bid = null;
		try {
			bid = GsonUtil.getGson().fromJson(json, getBidClass());
		}finally {
			if (bid == null) {
				return new ResultBean(-1, LABEL_PREFIX + getPropertyFileName() +".too_long_number");
			}
		}
		if (bid.getId() < 0) {
			bid.setStatus(BidStatus.INACTIVE);
		} else {
			B oldLot = getBidService().getById(bid.getId(), getUserCompanyId());
			bid.setStatus(oldLot.getStatus());
		}
		return save(bid);

	}
	
	protected ResultBean testSaveBid(String json){
		B bid = GsonUtil.getGson().fromJson(json, getBidClass());
		bid.setDescription(getSessionId());
		bid.setStatus(BidStatus.ACTIVE);
		bid.setExparationDate(DateUtil.shiftMonths(new Date(), 1));
		return save(bid);
	}

	protected ResultBean saveAndActivateBid(String json){
		B bid = null;
		try {
			bid = GsonUtil.getGson().fromJson(json, getBidClass());
		}finally {
			if (bid == null) {
				return new ResultBean(-1, LABEL_PREFIX + getPropertyFileName() +".too_long_number");
			}
		}
		bid.setStatus(BidStatus.ACTIVE);
		return save(bid);
	}

	private ResultBean save(B bid){
		List<String> errors = activationCheck(bid, getUserCompanyId());
		if (!errors.isEmpty()) {
			String str = errors.toString().substring(1, errors.toString().length() - 1);
			return new ResultBean(-1, str);
		}

		getBidService().merge(bid, getUserCompanyId());
		return new ResultBean(1, getObjectForUIConstructor().apply(bid), "");
	}

	protected ResultBean startTradingBid(long id){
		List<String> errors = activationCheck(id, getUserCompanyId());
		if (!errors.isEmpty()) {
			String str = errors.toString().substring(1, errors.toString().length() - 1);
			return new ResultBean(-1, str);
		}
		getBidService().activate(id, getUserCompanyId());
		return new ResultBean(1, null);
	}

	protected ResultBean stopTradingBid(long id){
		getBidService().deactivate(id, getUserCompanyId());
		return new ResultBean(1, null);
	}

	protected ResultBean deleteBid(long id){
		getBidService().delete(id, getUserCompanyId());
		return new ResultBean(1, null);
	}

	protected long getUserCompanyId() {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		if (loggedInUser != null && loggedInUser.getPrincipal() instanceof StockUser){
			StockUser user = (StockUser) loggedInUser.getPrincipal();
			return user.getCompanyId();
		} else {
			return 0;
		}
	}

	private List<String> activationCheck(long id, long companyId){
		B bid = getBidService().getById(id, getUserCompanyId());
		return activationCheck(bid, companyId);
	}

	private List<String> activationCheck(B bid, long companyId){
		List<String> errors = new ArrayList<>();

		List<Long> addressIds = addressService
				.getCompanyAddresses(companyId)
				.stream().map(CompanyAddress::getId)
				.collect(Collectors.toList());
		if (!addressIds.contains(bid.getAddressId())) {
			errors.add(LABEL_PREFIX + getPropertyFileName() + ".address_error");
		}
		if (bid.getPrice() < 0.1) {
			errors.add(LABEL_PREFIX + getPropertyFileName() + ".price_error");
		}
		if (bid.getProductId() < 0) {
			errors.add(LABEL_PREFIX + getPropertyFileName() + ".product_error");
		}
		if (bid.getMinVolume() < 1) {
			errors.add(LABEL_PREFIX + getPropertyFileName() + ".minVolume_error");
		}
		if (bid.getMaxVolume() <= bid.getMinVolume()) {
			errors.add(LABEL_PREFIX + getPropertyFileName() + ".maxVolume_error");
		}
		if (bid.getCategoryId() == -1 && !isEmptyCategoryAllowed()) {
			errors.add(LABEL_PREFIX + getPropertyFileName() + ".category_error");
		}

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		if (bid.getExparationDate().getTime() < calendar.getTimeInMillis()) {
			errors.add(LABEL_PREFIX + getPropertyFileName() + ".expDate_error");
		}
		return errors;
	}

}
