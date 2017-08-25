package com.bigroi.stock.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.ResultBean;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.google.gson.Gson;

@Controller
@RequestMapping("/json")
public class TenderResourseController extends ResourseBeanException {

	private static final Logger logger = Logger.getLogger(TenderRenderingController.class);

	@RequestMapping(value = "/TenderFormAuth.spr")
	@ResponseBody
	public String tenderEdit(@RequestParam("id") long id, HttpSession session) throws DaoException {
		logger.info("exection TenderRenderingController.tenderEdit");
		logger.info(id);
		logger.info(session);
		Map<String, Object> map = new HashMap<>();
		Tender tender;
		if (id == -1) {
			tender = new Tender();
			User user = (User) session.getAttribute("user");
			tender.setCustomerId(user.getCompanyId());
			tender.setStatus(Status.DRAFT);
			map.put("id", -1);
			logger.info("execution TenderRenderingController.tenderEdit - create new tender");
		} else {
			tender = DaoFactory.getTenderDao().getById(id);
			map.put("id", tender.getId());
			logger.info("execution TenderRenderingController.tenderEdit - get edited tender");
		}
		map.put("tender", tender);
		map.put("listOfProducts", DaoFactory.getProductDao().getAllProduct());
		logger.info("exection TenderRenderingController.tenderEdit successfully finished");
		return new ResultBean(1, map).toString();
	}

	@RequestMapping(value = "/TenderSaveAuth.spr")
	@ResponseBody
	public String tenderSave(@RequestParam("id") long id, @RequestParam("json") String json, HttpSession session)
			throws DaoException, ParseException {
		logger.info("exection TenderRenderingController.tenderSave");
		logger.info(json);
		logger.info(session);
		Tender tenderBean = new Gson().fromJson(json, Tender.class);
		if (id == -1) {
			DaoFactory.getTenderDao().add(tenderBean);
			id = tenderBean.getId();
			logger.info("execution TenderRenderingController.tenderSave - save new tender");
		} else {
			tenderBean.setId(id);
			DaoFactory.getTenderDao().updateById(tenderBean);
			logger.info("execution TenderRenderingController.tenderSave - update tender");
		}
		logger.info("exection TenderRenderingController.tenderSave successfully finished");
		return new ResultBean(1, myTenderList(session)).toString();
	}

	@RequestMapping("/MyTenderListAuth.spr")
	@ResponseBody
	public String myTenderList(HttpSession session) throws DaoException {
		logger.info("exection TenderRenderingController.myTenderList");
		logger.info(session);
		User user = (User) session.getAttribute("user");
		List<Tender> tenders = DaoFactory.getTenderDao().getByCustomerId(user.getCompanyId());
		logger.info("exection TenderRenderingController.myTenderList successfully finished");
		return new ResultBean(1, tenders).toString();
	}

	@RequestMapping("/TenderInGameAuth.spr")
	@ResponseBody
	public String tenderInGame(@RequestParam("id") long id, @RequestParam("json") String json, HttpSession session)
			throws DaoException {
		logger.info("exection TenderRenderingController.tenderInGame");
		logger.info(id);
		logger.info(json);
		logger.info(session);
		Tender tenderBean = new Gson().fromJson(json, Tender.class);
		Tender tender = DaoFactory.getTenderDao().getById(id);
		if (tender.getStatus() == Status.DRAFT) {
			tender.setStatus(Status.IN_GAME);
			DaoFactory.getTenderDao().updateById(tenderBean);
			logger.info("execution TenderRenderingController.tenderInGame - change status IN_GAME");
		}
		logger.info("exection TenderRenderingController.tenderInGame successfully finished");
		return new ResultBean(1, myTenderList(session)).toString();
	}

	@RequestMapping("/TenderCancelAuth.spr")
	@ResponseBody
	public String lotCancel(@RequestParam("id") long id, @RequestParam("json") String json, HttpSession session)
			throws DaoException {
		logger.info("exection TenderRenderingController.lotCancel");
		logger.info(id);
		logger.info(json);
		logger.info(session);
		Tender tenderBean = new Gson().fromJson(json, Tender.class);
		Tender tender = DaoFactory.getTenderDao().getById(id);
		if ((tender.getStatus() == Status.DRAFT) || (tender.getStatus() == Status.IN_GAME)) {
			tender.setStatus(Status.CANCELED);
			DaoFactory.getTenderDao().updateById(tenderBean);
			logger.info("execution TenderRenderingController.lotCancel - change status CANCELED");
		}
		logger.info("exection TenderRenderingController.lotCancel successfully finished");
		return new ResultBean(1, myTenderList(session)).toString();
	}

}
