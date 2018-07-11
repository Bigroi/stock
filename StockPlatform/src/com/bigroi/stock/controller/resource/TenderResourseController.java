package com.bigroi.stock.controller.resource;

import java.text.ParseException;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.bean.ui.TenderForUI;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.service.BidService;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.TenderService;

@Controller
@RequestMapping("/tender/json")
public class TenderResourseController extends BidResourceController<Tender, TenderForUI> {
	
	@Autowired
	private TenderService tenderService;
	
	@RequestMapping(value = "/Form.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String form( @RequestParam(value = "id", defaultValue = "-1") long id) throws ServiceException {
		return bidForm(id).toString();
	}

	@RequestMapping(value = "/Save.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String save(@RequestParam("json") String json, Authentication loggedInUser) throws ServiceException, ParseException {
		return saveBid(json, loggedInUser).toString();
	}
	
	@RequestMapping(value = "/SaveAndActivate.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String saveAndActivate(@RequestParam("json") String json, Authentication loggedInUser) throws ServiceException, ParseException {
		return saveAndActivateBid(json, loggedInUser).toString();
	}
	
	@RequestMapping("/MyList.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String myList() throws ServiceException, TableException {
		return bidList().toString();
	}

	@RequestMapping("/StartTrading.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String startTrading(@RequestParam("id") long id, Authentication loggedInUser) throws ServiceException {
		return startTradingBid(id, loggedInUser).toString();
	}
	
	@RequestMapping("/StopTrading.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String stopTrading(@RequestParam("id") long id, Authentication loggedInUser) throws ServiceException {
		return stopTradingBid(id).toString();
	}

	@RequestMapping("/Delete.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String delete(@RequestParam("id") long id) throws ServiceException {
		return deleteBid(id).toString();
	}

	@Override
	protected BidService<Tender> getBidService() {
		return tenderService;
	}

	@Override
	protected Function<Tender, TenderForUI> getObjectForUIConstructor() {
		return TenderForUI::new;
	}

	@Override
	protected Class<Tender> getBidClass() {
		return Tender.class;
	}

	@Override
	protected String getPropertyFileName() {
		return "tender";
	}
	
	protected Class<TenderForUI> getForUIClass(){
		return TenderForUI.class;
	}
}
