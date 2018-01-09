package com.bigroi.stock.controller.resource;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.Table;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/tender/json")
public class TenderResourseController extends BaseResourseController {
	
	@RequestMapping(value = "/Form.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String form( @RequestParam(value = "id", defaultValue = "-1") long id,
			Authentication loggedInUser) throws ServiceException {
		checkTender(id);
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		Tender tender = ServiceFactory.getTenderService().getTender(id, user.getCompanyId());
		return new ResultBean(1, tender).toString();
	}

	@RequestMapping(value = "/Save.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String save(
			@RequestParam("json") String json,
			Authentication loggedInUser) throws ServiceException, ParseException {
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		
		Tender newTender = gson.fromJson(json, Tender.class);
		checkTender(newTender.getId());
		List<String> errors = activationCheck(newTender);
		if (errors.size() > 0){
			return new ResultBean(-1, errors).toString();
		}
		
		if (newTender.getId() < 0) {
			newTender.setCustomerId(user.getCompanyId());;
			newTender.setStatus(BidStatus.INACTIVE);
		} else {
			Tender oldTender = ServiceFactory.getTenderService().getTender(newTender.getId(), user.getCompanyId());
			newTender.setStatus(oldTender.getStatus());
			newTender.setCustomerId(oldTender.getCustomerId());
		}
		
		ServiceFactory.getTenderService().merge(newTender);
		return new ResultBean(0, "/tender/MyTenders.spr").toString();
	}
	
	@RequestMapping(value = "/SaveAndActivate.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String saveAndActivate(@RequestParam("json") String json,
			Authentication loggedInUser) throws ServiceException, ParseException {
		Tender newTender = gson.fromJson(json, Tender.class);
		checkTender(newTender.getId());
		List<String> errors = activationCheck(newTender);
		if (errors.size() > 0){
			return new ResultBean(-1, errors).toString();
		}
		
		if (newTender.getId() < 0) {
			StockUser user = (StockUser)loggedInUser.getPrincipal();
			newTender.setCustomerId(user.getCompanyId());;
			newTender.setStatus(BidStatus.ACTIVE);
			ServiceFactory.getTenderService().merge(newTender);
			return new ResultBean(0, "/tender/MyTenders.spr").toString();
		} else {
			return new ResultBean(-1, "lable.tender.sna_error").toString();
		}
	}

	@RequestMapping("/MyList.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String myList(Authentication loggedInUser) throws ServiceException, TableException {
		StockUser user = (StockUser) loggedInUser.getPrincipal();
		List<Tender> tenders = ServiceFactory.getTenderService().getMyList(user.getCompanyId());
		Table<Tender> table = new Table<>(Tender.class, tenders);
		return new ResultBean(1, table).toString();
	}

	@RequestMapping("/StartTrading.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String StartTrading(@RequestParam("json") String json) throws ServiceException {
		Tender tender = gson.fromJson(json, Tender.class);
		tender = ServiceFactory.getTenderService().getTender(tender.getId(), 0);
		checkTender(tender.getId());
		List<String> errors = activationCheck(tender);
		if (errors.size() > 0){
			return new ResultBean(-1, errors).toString();
		}
		
		ServiceFactory.getTenderService().startTrading(tender.getId());
		return new ResultBean(0, "/tender/MyTenders.spr").toString();
	}

	@RequestMapping("/Cancel.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String cancel(@RequestParam("json") String json) throws ServiceException {
		Tender tender = gson.fromJson(json, Tender.class);
		checkTender(tender.getId());
		ServiceFactory.getTenderService().deleteById(tender.getId());
		return new ResultBean(0, "/tender/MyTenders.spr").toString();
	}

	private void checkTender(long id) throws ServiceException{
		if (id < 0){
			return;
		}
		StockUser user = (StockUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Tender tender = ServiceFactory.getTenderService().getTender(id, 0);
		if (tender.getCustomerId() != user.getCompanyId()){
			throw new SecurityException("User have no permission to modify Lot with id = " + id);
		}
	}
	
	private List<String> activationCheck(Tender tender){
		List<String> errors = new ArrayList<String>();
		
		if (tender.getMaxPrice() < 0.1) {
			errors.add("tender.maxPrice.error");
		}
		if (tender.getMinVolume() < 1) {
			errors.add("tender.minVolume.error");
		}
		if (tender.getMaxVolume() <= tender.getMinVolume()) {
			errors.add("tender.maxVolume.error");
		}
		if( new Date(tender.getExpDate().getTime()).getTime() < new Date().getTime()){
			errors.add("lot.ExpDate.error");
		}
		return errors;
	}
}
