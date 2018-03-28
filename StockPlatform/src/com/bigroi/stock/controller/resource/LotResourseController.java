package com.bigroi.stock.controller.resource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/lot/json")
public class LotResourseController extends BaseResourseController {

	@RequestMapping(value = "/Form.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String form(@RequestParam(value = "id", defaultValue = "-1") long id) throws ServiceException {
		Lot lot = ServiceFactory.getLotService().getLot(id, getUserCompanyId());
		return new ResultBean(1, lot, null).toString();
	}

	@RequestMapping(value = "/MyList.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String myList() throws ServiceException, TableException {
		List<Lot> lots = ServiceFactory.getLotService().getBySellerId(getUserCompanyId());
		TableResponse<Lot> table = new TableResponse<>(Lot.class, lots);
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
			Lot oldLot = (Lot) ServiceFactory.getLotService().getLot(lot.getId(), getUserCompanyId());
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
		if (errors.size() > 0) {
			String str = errors.toString().substring(1, errors.toString().length() - 2);
			return new ResultBean(-1, str).toString();
		}

		ServiceFactory.getLotService().merge(lot, getUserCompanyId());
		return new ResultBean(1, lot, "label.lot.save_success").toString();
	}

	@RequestMapping(value = "/StartTrading.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String startTrading(@RequestParam("id") long id) throws ServiceException {
		List<String> errors = activationCheck(id);
		if (errors.size() > 0) {
			String str = errors.toString().substring(1, errors.toString().length() - 2);
			return new ResultBean(-1, str).toString();
		}
		ServiceFactory.getLotService().activate(id, getUserCompanyId());
		return new ResultBean(1, "success").toString();
	}

	@RequestMapping(value = "/StopTrading.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String stopTrading(@RequestParam("id") long id) throws ServiceException {
		ServiceFactory.getLotService().deactivate(id, getUserCompanyId());
		return new ResultBean(1, "success").toString();
	}

	@RequestMapping(value = "/Delete.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String delete(@RequestParam("id") long id) throws ServiceException {
		ServiceFactory.getLotService().delete(id, getUserCompanyId());
		return new ResultBean(1, "success").toString();
	}

	private long getUserCompanyId() {
		StockUser user = (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getCompanyId();
	}

	private List<String> activationCheck(long id) throws ServiceException {
		Lot lot = ServiceFactory.getLotService().getLot(id, getUserCompanyId());
		return activationCheck(lot);
	}

	private List<String> activationCheck(Lot newLot) {
		List<String> errors = new ArrayList<String>();

		if (newLot.getMinPrice() < 0.1) {
			errors.add("label.lot.minPrice_error");
		}
		if (newLot.getProductId() < 0) {
			errors.add("label.lot.product_error");
		}
		if (newLot.getMinVolume() < 1) {
			errors.add("label.lot.minVolume_error");
		}
		if (newLot.getMaxVolume() <= newLot.getMinVolume()) {
			errors.add("label.lot.maxVolume_error");
		}

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		if (newLot.getExpDate().getTime() < calendar.getTimeInMillis()) {
			errors.add("label.lot.expDate_error");
		}
		return errors;
	}
}
