package com.bigroi.stock.controller.resource;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Bid;
import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.Table;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/lot/json")
public class LotResourseController extends BaseResourseController {

	private Gson gson = new GsonBuilder().setDateFormat(Bid.FORMATTER.toPattern()).create();
	
	@RequestMapping(value = "/Form.spr")
	@ResponseBody
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
	public String myLotList(Authentication loggedInUser) throws ServiceException, TableException {
		StockUser userBean = (StockUser) loggedInUser.getPrincipal();
		List<Lot> lots = ServiceFactory.getLotService().getBySellerId(userBean.getCompanyId());
		Table<Lot> table = new Table<>(Lot.class, lots);
		return new ResultBean(1, table).toString();
	}

	@RequestMapping(value = "/Save.spr")
	@ResponseBody
	public String lotSave(@RequestParam("json") String jsonLot, 
				Authentication loggedInUser) throws ServiceException {
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		
		Lot newLot = gson.fromJson(jsonLot, Lot.class);
		checkLot(newLot.getId());
		
		if (newLot.getId() < 0) {
			newLot.setSellerId(user.getCompanyId());;
			newLot.setStatus(Status.DRAFT);
		} else {
			Lot oldLot = ServiceFactory.getLotService().getLot(newLot.getId(), user.getCompanyId());
			newLot.setProductId(oldLot.getProductId());
			newLot.setStatus(oldLot.getStatus());
			newLot.setSellerId(oldLot.getSellerId());
		}
		
		ServiceFactory.getLotService().merge(newLot);
		return new ResultBean(0, "/lot/MyLots.spr").toString();
	}

	@RequestMapping(value = "/StartTrading.spr")
	@ResponseBody
	public String startTrading(@RequestParam("json") String jsonLot) throws ServiceException {
		Lot lot = gson.fromJson(jsonLot, Lot.class);
		checkLot(lot.getId());
		ServiceFactory.getLotService().startTrading(lot.getId());
		return new ResultBean(0, "/lot/MyLots.spr").toString();
	}

	@RequestMapping(value = "/Cancel.spr")
	@ResponseBody
	public String cancel(@RequestParam("json") String jsonLot) throws ServiceException {
		Lot lot = gson.fromJson(jsonLot, Lot.class);
		checkLot(lot.getId());
		ServiceFactory.getLotService().cancel(lot.getId());
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
