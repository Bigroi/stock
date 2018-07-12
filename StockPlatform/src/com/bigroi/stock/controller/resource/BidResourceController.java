package com.bigroi.stock.controller.resource;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.AddressService;
import com.bigroi.stock.service.BidService;
import com.bigroi.stock.service.ServiceException;

public abstract class BidResourceController<B extends Bid, U> extends BaseResourseController{
	
	private static final String LABEL_PREFIX = "label.";
	
	@Autowired
	private AddressService addressService;
			
	protected abstract BidService<B> getBidService();
	
	protected abstract Function<B, U> getObjectForUIConstructor();
	
	protected abstract Class<B> getBidClass(); 
	
	protected abstract Class<U> getForUIClass(); 
	
	protected abstract String getPropertyFileName();
	
	protected ResultBean bidForm(long id) throws ServiceException {
		B bid = getBidService().getById(id, getUserCompanyId());
		if (bid.getId() == -1){
			StockUser user = (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			bid.setAddressId(user.getCompany().getCompanyAddress().getId());
			bid.setCompanyAddress(user.getCompany().getCompanyAddress());
		}
		return new ResultBean(1, bid, null);
	}

	protected ResultBean bidList() throws ServiceException, TableException {
		List<B> bids = getBidService().getByCompanyId(getUserCompanyId());
		List<U> objectForUI = bids.stream().map(getObjectForUIConstructor()).collect(Collectors.toList());
		Class<U> clazz = getForUIClass();
		TableResponse<U> table = new TableResponse<>(clazz, objectForUI);
		return new ResultBean(1, table, null);
	}

	protected ResultBean saveBid(String json, Authentication loggedInUser) throws ServiceException {
		StockUser user = (StockUser) loggedInUser.getPrincipal();
		B bid = GsonUtil.getGson().fromJson(json, getBidClass());
		if (bid.getId() < 0) {
			bid.setStatus(BidStatus.INACTIVE);
		} else {
			B oldLot = getBidService().getById(bid.getId(), getUserCompanyId());
			bid.setStatus(oldLot.getStatus());
		}
		return save(bid, user);
	}

	protected ResultBean saveAndActivateBid(String json, Authentication loggedInUser) throws ServiceException {
		StockUser user = (StockUser) loggedInUser.getPrincipal();
		B bid = GsonUtil.getGson().fromJson(json, getBidClass());
		bid.setStatus(BidStatus.ACTIVE);
		return save(bid, user);
	}

	private ResultBean save(B bid, StockUser user) throws ServiceException {
		List<String> errors = activationCheck(bid, user);
		if (!errors.isEmpty()) {
			String str = errors.toString().substring(1, errors.toString().length() - 1);
			return new ResultBean(-1, str);
		}

		getBidService().merge(bid, getUserCompanyId());
		return new ResultBean(1, getObjectForUIConstructor().apply(bid), "");
	}

	protected ResultBean startTradingBid(long id, Authentication loggedInUser) throws ServiceException {
		StockUser user = (StockUser) loggedInUser.getPrincipal();
		List<String> errors = activationCheck(id, user);
		if (!errors.isEmpty()) {
			String str = errors.toString().substring(1, errors.toString().length() - 1);
			return new ResultBean(-1, str);
		}
		getBidService().activate(id, getUserCompanyId());
		return new ResultBean(1, null);
	}

	protected ResultBean stopTradingBid(long id) throws ServiceException {
		getBidService().deactivate(id, getUserCompanyId());
		return new ResultBean(1, null);
	}

	protected ResultBean deleteBid(long id) throws ServiceException {
		getBidService().delete(id, getUserCompanyId());
		return new ResultBean(1, null);
	}

	private long getUserCompanyId() {
		StockUser user = (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getCompanyId();
	}

	private List<String> activationCheck(long id, StockUser user) throws ServiceException {
		B bid = getBidService().getById(id, getUserCompanyId());
		return activationCheck(bid, user);
	}

	private List<String> activationCheck(B bid, StockUser user) throws ServiceException {
		List<String> errors = new ArrayList<>();

		List<Long> addressIds = addressService
				.getCompanyAddresses(user.getCompanyId())
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
			errors.add(LABEL_PREFIX + getPropertyFileName() + ".minPrice_error");
		}
		if (bid.getMaxVolume() <= bid.getMinVolume()) {
			errors.add(LABEL_PREFIX + getPropertyFileName() + ".maxVolume_error");
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
