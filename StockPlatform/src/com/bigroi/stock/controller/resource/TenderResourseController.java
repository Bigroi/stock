package com.bigroi.stock.controller.resource;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;
import com.google.gson.Gson;

@Controller
@RequestMapping("/tender/json")
public class TenderResourseController extends BaseResourseController {

	@RequestMapping(value = "/Form.spr")
	public @ResponseBody String form(
			@RequestParam(value = "id", defaultValue = "-1") long id,
			HttpSession session) 
					throws ServiceException {
		User user = (User)session.getAttribute("user");
		Tender tender = ServiceFactory.getTenderService()
				.getTender(id, user.getCompanyId());
		return new ResultBean(1, tender).toString();
	}

	@RequestMapping(value = "/Save.spr")
	public @ResponseBody String save(
			@RequestParam("json") String json)
					throws ServiceException, ParseException {
		Tender tender = new Gson().fromJson(json, Tender.class);
		ServiceFactory.getTenderService().merge(tender);
		return new ResultBean(1, "tender.update.success").toString();
	}

	@RequestMapping("/MyList.spr")
	public @ResponseBody String myList(
			HttpSession session) 
					throws ServiceException {
		User user = (User) session.getAttribute("user");
		List<Tender> tenders = ServiceFactory.getTenderService().getMyList(user.getCompanyId());
		return new ResultBean(1, tenders).toString();
	}

	@RequestMapping("/StartTrading.spr")
	public @ResponseBody String StartTrading(
			@RequestParam("id") long id, 
			HttpSession session)
					throws ServiceException {
		ServiceFactory.getTenderService().startTrading(id);
		return new ResultBean(1, "tender.update.success").toString();
	}

	@RequestMapping("/Cancel.spr")
	public @ResponseBody String cancel(
			@RequestParam("id") long id, 
			HttpSession session)
					throws ServiceException {
		ServiceFactory.getTenderService().cancel(id);
		return new ResultBean(1, "tender.update.success").toString();
	}

}
