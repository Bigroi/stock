package com.bigroi.stock.controller.resource;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.Table;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;
import com.google.gson.Gson;

@Controller
@RequestMapping("/lot/json")
public class LotResourseController extends BaseResourseController {

	@RequestMapping(value = "/Form.spr")
	@ResponseBody
	public String form(
			@RequestParam(value = "id", defaultValue = "-1") long id,
			Authentication loggedInUser) throws ServiceException {

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
	public String lotSave(@RequestParam("jsonLot") String jsonLot) throws ServiceException {
		Lot lotBean = new Gson().fromJson(jsonLot, Lot.class);
		ServiceFactory.getLotService().merge(lotBean);
		return new ResultBean(1, "lot.update.success").toString();
	}

	@RequestMapping(value = "/StartTrading.spr")
	@ResponseBody
	public String startTrading(@RequestParam("id") long id) throws ServiceException {
		ServiceFactory.getLotService().startTrading(id);
		return new ResultBean(1, "lot.update.success").toString();
	}

	@RequestMapping(value = "/Cancel.spr")
	@ResponseBody
	public String cancel(@RequestParam("id") long id) throws ServiceException {
		ServiceFactory.getLotService().cancel(id);
		return new ResultBean(1, "lot.update.success").toString();
	}
}
