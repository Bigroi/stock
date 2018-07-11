package com.bigroi.stock.controller.resource;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.ui.LotForUI;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.service.BidService;
import com.bigroi.stock.service.LotService;
import com.bigroi.stock.service.ServiceException;

@Controller
@RequestMapping("/lot/json")
public class LotResourseController extends BidResourceController<Lot, LotForUI> {

	@Autowired
	private LotService lotService;
	
	@RequestMapping(value = "/Form.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String form(@RequestParam(value = "id", defaultValue = "-1") long id) throws ServiceException {
		return bidForm(id).toString();
	}

	@RequestMapping(value = "/MyList.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String lotList() throws ServiceException, TableException {
		return bidList().toString();
	}

	@RequestMapping(value = "/Save.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String save(@RequestParam("json") String json, Authentication loggedInUser) throws ServiceException {
		return saveBid(json, loggedInUser).toString();
	}

	@RequestMapping(value = "/SaveAndActivate.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String saveAndActivate(@RequestParam("json") String json, Authentication loggedInUser) throws ServiceException {
		return saveAndActivateBid(json, loggedInUser).toString();
	}

	@RequestMapping(value = "/StartTrading.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String startTrading(@RequestParam("id") long id, Authentication loggedInUser) throws ServiceException {
		return startTradingBid(id, loggedInUser).toString();
	}

	@RequestMapping(value = "/StopTrading.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String stopTrading(@RequestParam("id") long id) throws ServiceException {
		return stopTradingBid(id).toString();
	}

	@RequestMapping(value = "/Delete.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String delete(@RequestParam("id") long id) throws ServiceException {
		return deleteBid(id).toString();
	}

	@Override
	protected BidService<Lot> getBidService() {
		return lotService;
	}

	@Override
	protected Function<Lot, LotForUI> getObjectForUIConstructor() {
		return LotForUI::new;
	}

	@Override
	protected Class<Lot> getBidClass() {
		return Lot.class;
	}

	@Override
	protected String getPropertyFileName() {
		return "lot";
	}
	
	protected Class<LotForUI> getForUIClass(){
		return LotForUI.class;
	}
}
