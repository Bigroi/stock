package com.bigroi.stock.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

@Controller
public class TenderRenderingController {
	
	private static final Logger logger = Logger.getLogger(TenderRenderingController.class);

	@RequestMapping("/TenderFormAuth.spr")
	public ModelAndView tenderEdit(@RequestParam("id") long id, HttpSession session) throws DaoException {
		logger.info("exection TenderRenderingController.tenderEdit");
		logger.info(id);	
		logger.info(session);	
		ModelMap model = new ModelMap();
		Tender tender;
		if (id == -1) {
			tender = new Tender();
			User user = (User) session.getAttribute("user");
			tender.setCustomerId(user.getCompanyId());
			tender.setStatus(Status.DRAFT);
			model.addAttribute("id", -1);
			logger.info("execution TenderRenderingController.tenderEdit - create new tender");
		} else {
			tender = DaoFactory.getTenderDao().getById(id);
			model.addAttribute("id", tender.getId());
			logger.info("execution TenderRenderingController.tenderEdit - get edited tender");
		}
		model.addAttribute("tender", tender);
		model.put("listOfProducts", DaoFactory.getProductDao().getAllProduct());
		logger.info("exection TenderRenderingController.tenderEdit successfully finished");
		return new ModelAndView("tenderForm", model);
	}

	@RequestMapping("/TenderSaveAuth.spr")
	public ModelAndView tenderSave(@RequestParam("id") long id, 
			@RequestParam("description") String description,			
			@RequestParam("productId") long productId,
			@RequestParam("maxPrice") double maxPrice,
			@RequestParam("customerId") long customerId,
			@RequestParam("expDate") String expDateStr,
			@RequestParam("status") Status status,
			HttpSession session) throws DaoException, ParseException {
		
		logger.info("exection TenderRenderingController.tenderSave");
		logger.info(description);
		logger.info(productId);
		logger.info(maxPrice);
		logger.info(customerId);
		logger.info(expDateStr);
		logger.info(status);
		logger.info(session);
		
		Tender tender = new Tender();
		tender.setDescription(description);
		tender.setProductId(productId);
		tender.setMaxPrice(maxPrice);
		tender.setCustomerId(customerId);
		tender.setDateStr(expDateStr);
		tender.setStatus(status);
		if (id == -1) {
			DaoFactory.getTenderDao().add(tender);
			id = tender.getId();
			logger.info("execution TenderRenderingController.tenderSave - save new tender");
		} else {
			tender.setId(id);
			DaoFactory.getTenderDao().updateById(tender);
			logger.info("execution TenderRenderingController.tenderSave - update tender");
		}
//		return tenderEdit(id, session);
		logger.info("exection TenderRenderingController.tenderSave successfully finished");
		return myTenderList(session);
	}
	
	@RequestMapping("/MyTenderListAuth.spr")
	public ModelAndView myTenderList(HttpSession session) throws DaoException {
		logger.info("exection TenderRenderingController.myTenderList");
		logger.info(session);
		User user = (User) session.getAttribute("user");
		List<Tender> tenders = DaoFactory.getTenderDao().getByCustomerId(user.getCompanyId());
		logger.info("exection TenderRenderingController.myTenderList successfully finished");
		return new ModelAndView("myTenderList", "listOfTenders", tenders);
	}
	
	@RequestMapping("/TenderInGameAuth.spr")
	public ModelAndView tenderInGame(@RequestParam("id") long id, HttpSession session) throws DaoException {
		logger.info("exection TenderRenderingController.tenderInGame");
		logger.info(id);
		logger.info(session);
		Tender tender = DaoFactory.getTenderDao().getById(id);
		if (tender.getStatus() == Status.DRAFT){
			tender.setStatus(Status.IN_GAME);
			DaoFactory.getTenderDao().updateById(tender);
			logger.info("execution TenderRenderingController.tenderInGame - change status IN_GAME");
		}
		logger.info("exection TenderRenderingController.tenderInGame successfully finished");
		return myTenderList(session);
	}
	
	@RequestMapping("/TenderCancelAuth.spr")
	public ModelAndView lotCancel(@RequestParam("id") long id, HttpSession session) throws DaoException {
		logger.info("exection TenderRenderingController.lotCancel");
		logger.info(id);
		logger.info(session);
		Tender tender = DaoFactory.getTenderDao().getById(id);
		if ((tender.getStatus() == Status.DRAFT) || (tender.getStatus() == Status.IN_GAME)){
			tender.setStatus(Status.CANCELED);
			DaoFactory.getTenderDao().updateById(tender);
			logger.info("execution TenderRenderingController.lotCancel - change status CANCELED");
		}
		logger.info("exection TenderRenderingController.lotCancel successfully finished");
		return myTenderList(session);
	}
}
