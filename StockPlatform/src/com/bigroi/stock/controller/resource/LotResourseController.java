package com.bigroi.stock.controller.resource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.bean.ui.LotForUI;
import com.bigroi.stock.controller.BaseResourseController;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.LotService;
import com.bigroi.stock.service.ServiceException;

@Controller
@RequestMapping("/lot/json")
public class LotResourseController extends BaseResourseController {

	private static final String MIN_PRICE_ERROR_LABEL = "label.lot.minPrice_error";
	private static final String PRODUCT_ERROR_LABEL = "label.lot.product_error";
	private static final String MIN_VOLUME_ERROR_LABEL = "label.lot.minVolume_error";
	private static final String MAX_VOLUME_ERROR_LABEL = "label.lot.maxVolume_error";
	private static final String EXP_DATE_ERROR_LABEL= "label.lot.expDate_error";
	
	@Autowired
	private LotService lotService;
			
	@RequestMapping(value = "/Form.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String form(@RequestParam(value = "id", defaultValue = "-1") long id) throws ServiceException {
		Lot lot = lotService.getLot(id, getUserCompanyId());
		if (lot.getId() == -1){
			StockUser user = (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			lot.setAddressId(user.getCompany().getCompanyAddress().getId());
		}
		return new ResultBean(1, lot, null).toString();
	}

	@RequestMapping(value = "/MyList.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String myList() throws ServiceException, TableException {
		List<Lot> lots = lotService.getByCompanyId(getUserCompanyId());
		List<LotForUI> lotrForUI = lots.stream().map(LotForUI::new).collect(Collectors.toList());
		TableResponse<LotForUI> table = new TableResponse<>(LotForUI.class, lotrForUI);
		return new ResultBean(1, table, null).toString();
	}

	@RequestMapping(value = "/Save.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String save(@RequestParam("json") String jsonLot) throws ServiceException {
		Lot lot = GsonUtil.getGson().fromJson(jsonLot, Lot.class);
		if (lot.getId() < 0) {
			lot.setStatus(BidStatus.INACTIVE);
		} else {
			Lot oldLot = lotService.getLot(lot.getId(), getUserCompanyId());
			lot.setStatus(oldLot.getStatus());
		}
		return save(lot);
	}

	@RequestMapping(value = "/SaveAndActivate.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String saveAndActivate(@RequestParam("json") String json) throws ServiceException {
		Lot lot = GsonUtil.getGson().fromJson(json, Lot.class);
		lot.setStatus(BidStatus.ACTIVE);
		return save(lot);
	}

	private String save(Lot lot) throws ServiceException {
		List<String> errors = activationCheck(lot);
		if (!errors.isEmpty()) {
			String str = errors.toString().substring(1, errors.toString().length() - 1);
			return new ResultBean(-1, str).toString();
		}

		lotService.merge(lot, getUserCompanyId());
		return new ResultBean(1, new LotForUI(lot), "").toString();
	}

	@RequestMapping(value = "/StartTrading.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String startTrading(@RequestParam("id") long id) throws ServiceException {
		List<String> errors = activationCheck(id);
		if (!errors.isEmpty()) {
			String str = errors.toString().substring(1, errors.toString().length() - 1);
			return new ResultBean(-1, str).toString();
		}
		lotService.activate(id, getUserCompanyId());
		return new ResultBean(1, "").toString();
	}

	@RequestMapping(value = "/StopTrading.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String stopTrading(@RequestParam("id") long id) throws ServiceException {
		lotService.deactivate(id, getUserCompanyId());
		return new ResultBean(1, "").toString();
	}

	@RequestMapping(value = "/Delete.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String delete(@RequestParam("id") long id) throws ServiceException {
		lotService.delete(id, getUserCompanyId());
		return new ResultBean(1, "").toString();
	}

	private long getUserCompanyId() {
		StockUser user = (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getCompanyId();
	}

	private List<String> activationCheck(long id) throws ServiceException {
		Lot lot = lotService.getLot(id, getUserCompanyId());
		return activationCheck(lot);
	}

	private List<String> activationCheck(Lot newLot) {
		List<String> errors = new ArrayList<>();

		if (newLot.getPrice() < 0.1) {
			errors.add(MIN_PRICE_ERROR_LABEL);
		}
		if (newLot.getProductId() < 0) {
			errors.add(PRODUCT_ERROR_LABEL);
		}
		if (newLot.getMinVolume() < 1) {
			errors.add(MIN_VOLUME_ERROR_LABEL);
		}
		if (newLot.getMaxVolume() <= newLot.getMinVolume()) {
			errors.add(MAX_VOLUME_ERROR_LABEL);
		}

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		if (newLot.getExparationDate().getTime() < calendar.getTimeInMillis()) {
			errors.add(EXP_DATE_ERROR_LABEL);
		}
		return errors;
	}
}
