package com.bigroi.stock.controller.resource;

import java.util.Date;
import java.util.List;

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
//		if (jsonLot.contains("productId"+":"+"")) {
//			return new ResultBean(-1, "lot.product.error").toString();
//		}
		Lot newLot = gson.fromJson(jsonLot, Lot.class);
		checkLot(newLot.getId());
		
		if (newLot.getMinPrice() < 0.1) {
			return new ResultBean(-1, "lot.minPrice.error").toString();
		}
		if (newLot.getMinVolume() < 1) {
			return new ResultBean(-1, "lot.minVolume.error").toString();
		}
		if (newLot.getMaxVolume() <= newLot.getMinVolume()) {
			return new ResultBean(-1, "lot.maxVolume.error").toString();
		}
		if( new Date(newLot.getExpDate().getTime()).getTime() < new Date().getTime()){
			return new ResultBean(-1, "lot.ExpDate.error").toString();
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
	
	@RequestMapping(value = "/SaveAndActivate.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String lotSaveAndActivate(@RequestParam("json") String json, 
				Authentication loggedInUser) throws ServiceException {
//		if (json.contains("productId"+":"+" ")) {
//			return new ResultBean(-1, "lot.product.error").toString();
//		}
		Lot newLot = gson.fromJson(json, Lot.class);
		checkLot(newLot.getId());
		if (newLot.getMinPrice() < 0.1) {
			return new ResultBean(-1, "lot.minPrice.error").toString();
		}
		if (newLot.getMinVolume() < 1) {
			return new ResultBean(-1, "lot.minVolume.error").toString();
		}
		if (newLot.getMaxVolume() <= newLot.getMinVolume()) {
			return new ResultBean(-1, "lot.maxVolume.error").toString();
		}
		if( new Date(newLot.getExpDate().getTime()).getTime() < new Date().getTime()){
			return new ResultBean(-1, "lot.ExpDate.error").toString();
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
		checkLot(lot.getId());
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
