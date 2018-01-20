package com.bigroi.stock.controller.resource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.access.annotation.Secured;
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
	public String form(@RequestParam(value = "id", defaultValue = "-1") long id) 
			throws ServiceException {
		Lot lot = ServiceFactory.getLotService().getLot(id, getUserCompanyId());
		return new ResultBean(1, lot).toString();
	}
	
	@RequestMapping(value = "/MyList.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String myList() throws ServiceException, TableException {
		List<Lot> lots = ServiceFactory.getLotService().getBySellerId(getUserCompanyId());
		Table<Lot> table = new Table<>(Lot.class, lots);
		return new ResultBean(1, table).toString();
	}

	@RequestMapping(value = "/Save.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String save(@RequestParam("json") String jsonLot) 
			throws ServiceException {
		Lot lot = gson.fromJson(jsonLot, Lot.class);
		if (lot.getId() < 0) {
			lot.setStatus(BidStatus.INACTIVE);
		} else {
			Lot oldLot = (Lot)ServiceFactory.getLotService().getLot(lot.getId(), getUserCompanyId());
			lot.setStatus(oldLot.getStatus());
		}
		return save(lot);
	}
	
	@RequestMapping(value = "/SaveAndActivate.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String saveAndActivate(@RequestParam("json") String json) 
			throws ServiceException {
		Lot lot = gson.fromJson(json, Lot.class);
		lot.setStatus(BidStatus.ACTIVE);
		return save(lot);
	}
	
	private String save(Lot lot) throws ServiceException{
		List<String> errors = activationCheck(lot);
		if (errors.size() > 0) {
			return new ResultBean(-1, errors).toString();
		}
		
		ServiceFactory.getLotService().merge(lot, getUserCompanyId());
		return new ResultBean(0, "/lot/MyLots.spr").toString();
	}

	@RequestMapping(value = "/StartTrading.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String startTrading(@RequestParam("json") String jsonLots) throws ServiceException {
		Ids ids = gson.fromJson(jsonLots, Ids.class);
		Set<String> errors = activationCheck(ids.getId());
		if (errors.size() > 0) {
			return new ResultBean(-1, errors).toString();
		}
		ServiceFactory.getLotService().activate(ids.getId(), getUserCompanyId());
		return new ResultBean(0, "/lot/MyLots.spr").toString();
	}
	
	@RequestMapping(value = "/StopTrading.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String stopTrading(@RequestParam("json") String jsonLots) throws ServiceException {
		Ids ids = gson.fromJson(jsonLots, Ids.class);
		ServiceFactory.getLotService().deactivate(ids.getId(), getUserCompanyId());
		return new ResultBean(0, "/lot/MyLots.spr").toString();
	}

	@RequestMapping(value = "/Delete.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String delete(@RequestParam("json") String jsonLots) throws ServiceException {
		Ids ids = gson.fromJson(jsonLots, Ids.class);
		ServiceFactory.getLotService().delete(ids.getId(), getUserCompanyId());
		return new ResultBean(0, "/lot/MyLots.spr").toString();
	}
	
	private long getUserCompanyId(){
		StockUser user = (StockUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getCompanyId();
	}
	
	private Set<String> activationCheck(List<Long> ids) throws ServiceException{
		Set<String> errors = new HashSet<String>();
		for (long id : ids){
			Lot lot = ServiceFactory.getLotService().getLot(id, getUserCompanyId());
			errors.addAll(activationCheck(lot));
		}
		return errors;
	}
	
	private List<String> activationCheck(Lot newLot){
		List<String> errors = new ArrayList<String>();
		
		if (newLot.getMinPrice() < 0.1) {
			errors.add("lot.minPrice.error");
		}
		if (newLot.getProductId() < 0) {
			errors.add("lot.product.error");
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
	
	public static class Ids{
		private List<Long> id;
		
		public List<Long> getId() {
			return id;
		}
		
		public void setId(List<Long> id) {
			this.id = id;
		}
		
	}
}
