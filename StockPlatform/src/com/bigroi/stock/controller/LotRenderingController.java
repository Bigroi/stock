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

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
public class LotRenderingController {
	
	private static final Logger logger = Logger.getLogger(LotRenderingController.class);
	
	@RequestMapping("/LotFormAuth.spr")
	public ModelAndView lotEdit(@RequestParam("id") long id, HttpSession session) throws ServiceException {
		logger.info("execution LotRenderingController.lotEdit");
		logger.info(id);
		logger.info(session);
		ModelMap model = new ModelMap();
		Lot lot;
		if (id == -1) {
			lot = new Lot();			
			User user = (User) session.getAttribute("user");
			lot.setSellerId(user.getCompanyId());
			lot.setStatus(Status.DRAFT);
			lot.setId(-1);
			logger.info("execution LotRenderingController.lotEdit - create new lot");
		} else {
			lot = ServiceFactory.getLotService().getById(id);
			model.addAttribute("id", lot.getId());
			logger.info("execution LotRenderingController.lotEdit - get edited lot");
		}		
		model.addAttribute("lot", lot);
		//model.put("listOfProducts", DaoFactory.getProductDao().getAllProduct());
		logger.info("exection LotRenderingController.lotEdit successfully finished");
		return new ModelAndView("lotForm", model);
	}
	
	@RequestMapping("/LotSaveAuth.spr")
	public ModelAndView lotSave(@RequestParam("id") long id, 
			@RequestParam("description") String description,			
			@RequestParam("productId") long productId,
			@RequestParam("minPrice") double minPrice,
			@RequestParam("sellerId") long salerId,
			@RequestParam("expDate") String expDateStr,
			@RequestParam("volumeOfLot") int volumeOfLot,
			@RequestParam("status") Status status,
			HttpSession session) throws DaoException, ParseException, ServiceException {
		logger.info("exection LotRenderingController.lotSave");
		logger.info(description);
		logger.info(productId);
		logger.info(minPrice);
		logger.info(salerId);
		logger.info(expDateStr);
		logger.info(volumeOfLot);
		logger.info(status);
		logger.info(session);
		
		Lot lot = new Lot();
		lot.setDescription(description);
		lot.setPoductId(productId);
		lot.setMinPrice(minPrice);
		lot.setSellerId(salerId);
		lot.setDateStr(expDateStr);
		lot.setVolumeOfLot(volumeOfLot);
		lot.setStatus(status);		
		
		if (id == -1) {
			//ServiceFactory.getLotService().addLot(lot);
			id = lot.getId();//TODO: Lot not get Id, bug    ------------------------------!!!!-------------------------
			DaoFactory.getLotDao().add(lot);
			logger.info("execution LotRenderingController.lotSave - save new lot");
		} else {
			lot.setId(id);
			ServiceFactory.getLotService().updateByIdLot(lot);
			logger.info("execution LotRenderingController.lotSave - update lot");
		}
//		return lotEdit(id, session);
		logger.info("exection LotRenderingController.lotSave successfully finished");
		return myLotList(session);
	}
	
	@RequestMapping("/MyLotListAuth.spr")
	public ModelAndView myLotList(HttpSession session) throws  ServiceException {
		logger.info("exection LotRenderingController.myLotList");
		logger.info(session);
		User user = (User) session.getAttribute("user");		
		List<Lot> lots = ServiceFactory.getLotService().getBySellerId(user.getCompanyId());
		logger.info("exection LotRenderingController.myLotList successfully finished");
		return new ModelAndView("myLotList", "listOfLots", lots);
	}
	
	@RequestMapping("/LotInGameAuth.spr")
	public ModelAndView lotInGame(@RequestParam("id") long id, HttpSession session) throws ServiceException {
		logger.info("exection LotRenderingController.lotInGame");
		logger.info(id);
		logger.info(session);
		ServiceFactory.getLotService().setStatusInGame(id);
		logger.info("exection LotRenderingController.lotInGame successfully finished");
		return myLotList(session);
	}
	
	@RequestMapping("/LotCancelAuth.spr")
	public ModelAndView lotCancel(@RequestParam("id") long id, HttpSession session) throws ServiceException {
		logger.info("exection LotRenderingController.lotCancel");
		logger.info(id);
		logger.info(session);
		ServiceFactory.getLotService().setStatusCancel(id);
		logger.info("exection LotRenderingController.lotCancel successfully finished");
		return myLotList(session);
	}
}
