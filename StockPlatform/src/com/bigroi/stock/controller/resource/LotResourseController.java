package com.bigroi.stock.controller.resource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONObject;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.Table;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/lot/json")
public class LotResourseController extends BaseResourseController {

	@RequestMapping(value = "/Form.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String form(
			@RequestParam(value = "id", defaultValue = "-1") long id,
			Authentication loggedInUser) throws ServiceException {
		checkLot(id);
		StockUser user = (StockUser) loggedInUser.getPrincipal();
		Lot lot = ServiceFactory.getLotService().getLot(id, user.getCompanyId());
		return new ResultBean(1, lot).toString();
	}
	
	@RequestMapping(value = "/MyList.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String myLotList(Authentication loggedInUser) throws ServiceException, TableException {
		StockUser userBean = (StockUser) loggedInUser.getPrincipal();
		List<Lot> lots = ServiceFactory.getLotService().getBySellerId(userBean.getCompanyId());
		Table<Lot> table = new Table<>(Lot.class, lots);
		return new ResultBean(1, table).toString();
	}

	@RequestMapping(value = "/Save.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String lotSave(@RequestParam("json") String jsonLot, 
				Authentication loggedInUser) throws ServiceException {
		StockUser user = (StockUser) loggedInUser.getPrincipal();
		JSONObject obj = new JSONObject(jsonLot);
		if(obj.getString("productId").equals("")){
			return new ResultBean(-1, "lot.product.error").toString();
		}
		Lot newLot = gson.fromJson(jsonLot, Lot.class);
		checkLot(newLot.getId());
		List<String> errors = activationCheck(newLot);
		if (errors.size() > 0) {
			return new ResultBean(-1, errors).toString();
		}
		
		if (newLot.getId() < 0) {
			newLot.setSellerId(user.getCompanyId());;
			newLot.setStatus(BidStatus.INACTIVE);
		} else {
			Lot oldLot = ServiceFactory.getLotService().getLot(newLot.getId(), user.getCompanyId());
			newLot.setStatus(oldLot.getStatus());
			newLot.setSellerId(oldLot.getSellerId());
		}
		ServiceFactory.getLotService().merge(newLot);
		return new ResultBean(0, "/lot/MyLots.spr").toString();
	}
	
	private List<String> activationCheck(Lot newLot){
		List<String> errors = new ArrayList<String>();
		
		if (newLot.getMinPrice() < 0.1) {
			errors.add("lot.minPrice.error");
		}
		if (newLot.getMinVolume() < 1) {
			errors.add("lot.minVolume.error");
		}
		if (newLot.getMaxVolume() <= newLot.getMinVolume()) {
			errors.add("lot.maxVolume.error");
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		if(newLot.getExpDate().getTime() < calendar.getTimeInMillis()){
			errors.add("lot.expDate.error");
		}
		return errors;
	}
	
	@RequestMapping(value = "/SaveAndActivate.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String lotSaveAndActivate(@RequestParam("json") String json, 
				Authentication loggedInUser) throws ServiceException {
		Lot newLot = gson.fromJson(json, Lot.class);
		checkLot(newLot.getId());
		List<String> errors = activationCheck(newLot);
		if (errors.size() > 0) {
			return new ResultBean(-1, errors).toString();
		}
		
		if (newLot.getId() < 0) {
			StockUser user = (StockUser)loggedInUser.getPrincipal();
			newLot.setSellerId(user.getCompanyId());;
			newLot.setStatus(BidStatus.ACTIVE);
			ServiceFactory.getLotService().merge(newLot);
			return new ResultBean(0, "/lot/MyLots.spr").toString();
		} else {
			return new ResultBean(-1, "lable.lot.sna_error").toString();
		}
	}

	@RequestMapping(value = "/StartTrading.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String startTrading(@RequestParam("json") String jsonLot) throws ServiceException {
		Lot lot = gson.fromJson(jsonLot, Lot.class);
		lot = ServiceFactory.getLotService().getLot(lot.getId(), 0);
		checkLot(lot.getId());
		List<String> errors = activationCheck(lot);
		if (errors.size() > 0) {
			return new ResultBean(-1, errors).toString();
		}
		
		ServiceFactory.getLotService().activate(lot.getId());
		return new ResultBean(0, "/lot/MyLots.spr").toString();
	}

	@RequestMapping(value = "/Cancel.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String cancel(@RequestParam("json") String jsonLot) throws ServiceException {
		Lot lot = gson.fromJson(jsonLot, Lot.class);
		checkLot(lot.getId());
		ServiceFactory.getLotService().deleteById(lot.getId());
		return new ResultBean(0, "/lot/MyLots.spr").toString();
	}
	
	private void checkLot(long id) throws ServiceException{
		if (id < 0){
			return;
		}
		StockUser user = (StockUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Lot lot = ServiceFactory.getLotService().getLot(id, 0);
		if (lot.getSellerId() != user.getCompanyId()){
			throw new SecurityException("User have no permission to modify Lot with id = " + id);
		}
	}
}
