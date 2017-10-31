package com.bigroi.stock.controller.resource;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;
import com.google.gson.Gson;

@Controller
@RequestMapping("/lot/json")
public class LotResourseController extends BaseResourseController {

	@RequestMapping(value = "/Form.spr")
	public @ResponseBody String form(
			@RequestParam(value = "id", defaultValue = "-1") long id,
			HttpSession session) 
					throws ServiceException {
		
		User user = (User) session.getAttribute("user");
		Lot lot = ServiceFactory.getLotService().getLot(id, user.getCompanyId());
		return new ResultBean(1, lot).toString();
		
	}
	
	@RequestMapping(value = "/MyList.spr")
	public @ResponseBody String myLotList(
			HttpSession session) 
					throws ServiceException {
		
		User userBean = (User) session.getAttribute("user");
		List<Lot> lots = ServiceFactory.getLotService().getBySellerId(userBean.getCompanyId());
		return new ResultBean(1, lots).toString();
		
	}

	@RequestMapping(value = "/Save.spr")
	public @ResponseBody String lotSave(
			@RequestParam("jsonLot") String jsonLot, 
			HttpSession session) 
					throws ServiceException {
		
		Lot lotBean = new Gson().fromJson(jsonLot, Lot.class);
		ServiceFactory.getLotService().merge(lotBean);
		return new ResultBean(1, "lot.update.success").toString();
		
	}

	@RequestMapping(value = "/StartTrading.spr")
	@ResponseBody
	public String startTrading(@RequestParam("id") long id, 
			HttpSession session) 
					throws ServiceException {
		
		ServiceFactory.getLotService().startTrading(id);
		return new ResultBean(1, "lot.update.success").toString();
	}

	@RequestMapping(value = "/Cancel.spr")
	@ResponseBody
	public String cancel(
			@RequestParam("id") long id, 
			HttpSession session) 
					throws ServiceException {

		ServiceFactory.getLotService().cancel(id);
		return new ResultBean(1, "lot.update.success").toString();
	}

}
