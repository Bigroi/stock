package com.bigroi.stock.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
public class TenderRenderingController {
	
	private static final Logger logger = Logger.getLogger(TenderRenderingController.class);

	@RequestMapping("/TenderFormAuth.spr")
	@Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
	public ModelAndView tenderEdit(@RequestParam("id") long id, HttpSession session) throws ServiceException {
		logger.info("exection TenderRenderingController.tenderEdit");
		logger.info(id);	
		logger.info(session);	
		ModelMap model = ServiceFactory.getTenderService().callEditTender(id, session);
		logger.info("exection TenderRenderingController.tenderEdit successfully finished");
		return new ModelAndView("tenderForm", model);
	}

	@RequestMapping("/TenderSaveAuth.spr")
	@Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
	public ModelAndView tenderSave(@RequestParam("id") long id, 
			@RequestParam("description") String description,			
			@RequestParam("productId") long productId,
			@RequestParam("maxPrice") double maxPrice,
			@RequestParam("customerId") long customerId,
			@RequestParam("expDate") String expDateStr,
			@RequestParam("volumeOfTender") int volumeOfTender,
			@RequestParam("status") Status status,
			HttpSession session) throws DaoException, ParseException, ServiceException {
		
		logger.info("exection TenderRenderingController.tenderSave");
		logger.info(description);
		logger.info(productId);
		logger.info(maxPrice);
		logger.info(customerId);
		logger.info(expDateStr);
		logger.info(volumeOfTender);
		logger.info(status);
		logger.info(session);
		
		Tender tender = new Tender();
		tender.setDescription(description);
		tender.setProductId(productId);
		tender.setMaxPrice(maxPrice);
		tender.setCustomerId(customerId);
		tender.setDateStr(expDateStr);
		tender.setVolumeOfTender(volumeOfTender);
		tender.setStatus(status);
		if (id == -1) {
			DaoFactory.getTenderDao().add(tender);
			id = tender.getId();//TODO: Tender not get Id, !ERROR!    ------------------------------!!!!-------------------------
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
	@Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
	public ModelAndView myTenderList(HttpSession session) throws  ServiceException {
		logger.info("exection TenderRenderingController.myTenderList");
		logger.info(session);
		List<Tender> tenders = ServiceFactory.getTenderService().getMyTenderList(session);
		logger.info("exection TenderRenderingController.myTenderList successfully finished");
		return new ModelAndView("myTenderList", "listOfTenders", tenders);
	}
	
	@RequestMapping("/TenderInGameAuth.spr")
	@Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
	public ModelAndView tenderInGame(@RequestParam("id") long id, HttpSession session) throws  ServiceException {
		logger.info("exection TenderRenderingController.tenderInGame");
		logger.info(id);
		logger.info(session);
		ServiceFactory.getTenderService().setTenderInGame(id);
		logger.info("exection TenderRenderingController.tenderInGame successfully finished");
		return myTenderList(session);
	}
	
	@RequestMapping("/TenderCancelAuth.spr")
	@Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
	public ModelAndView lotCancel(@RequestParam("id") long id, HttpSession session) throws  ServiceException {
		logger.info("exection TenderRenderingController.lotCancel");
		logger.info(id);
		logger.info(session);
		ServiceFactory.getTenderService().setLotCancel(id);
		logger.info("exection TenderRenderingController.lotCancel successfully finished");
		return myTenderList(session);
	}
}
