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

@Controller
public class LotRenderingController {
	
	private static final Logger logger = Logger.getLogger(LotRenderingController.class);
	
	@RequestMapping("/LotFormAuth.spr")
	public ModelAndView lotEdit(@RequestParam("id") long id, HttpSession session) throws DaoException {
		
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
			model.addAttribute("id", -1);
			logger.info("execution LotRenderingController.lotEdit - create new lot");
		} else {
			lot = DaoFactory.getLotDao().getById(id);
			model.addAttribute("id", lot.getId());
			logger.info("execution LotRenderingController.lotEdit - get edited lot");
		}		
		model.addAttribute("lot", lot);
		model.put("listOfProducts", DaoFactory.getProductDao().getAllProduct());
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
			HttpSession session) throws DaoException, ParseException {

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
			DaoFactory.getLotDao().add(lot);
			id = lot.getId();
			logger.info("execution LotRenderingController.lotSave - save new lot");
		} else {
			lot.setId(id);
			DaoFactory.getLotDao().updateById(lot);
			logger.info("execution LotRenderingController.lotSave - update lot");
		}
//		return lotEdit(id, session);
		logger.info("exection LotRenderingController.lotSave successfully finished");
		return myLotList(session);
	}
	
	@RequestMapping("/MyLotListAuth.spr")
	public ModelAndView myLotList(HttpSession session) throws DaoException {
		logger.info("exection LotRenderingController.myLotList");
		logger.info(session);
		User user = (User) session.getAttribute("user");		
		List<Lot> lots = DaoFactory.getLotDao().getBySellerId(user.getCompanyId());
		logger.info("exection LotRenderingController.myLotList successfully finished");
		return new ModelAndView("myLotList", "listOfLots", lots);
	}
	
	@RequestMapping("/LotInGameAuth.spr")
	public ModelAndView lotInGame(@RequestParam("id") long id, HttpSession session) throws DaoException {
		logger.info("exection LotRenderingController.lotInGame");
		logger.info(id);
		logger.info(session);
		Lot lot = DaoFactory.getLotDao().getById(id);
		if (lot.getStatus() == Status.DRAFT){
			lot.setStatus(Status.IN_GAME);
			DaoFactory.getLotDao().updateById(lot);
			logger.info("execution LotRenderingController.lotInGame - change status IN_GAME");
		}
		logger.info("exection LotRenderingController.lotInGame successfully finished");
		return myLotList(session);
	}
	
	@RequestMapping("/LotCancelAuth.spr")
	public ModelAndView lotCancel(@RequestParam("id") long id, HttpSession session) throws DaoException {
		logger.info("exection LotRenderingController.lotCancel");
		logger.info(id);
		logger.info(session);
		Lot lot = DaoFactory.getLotDao().getById(id);
		if ((lot.getStatus() == Status.DRAFT) || (lot.getStatus() == Status.IN_GAME)){
			lot.setStatus(Status.CANCELED);
			DaoFactory.getLotDao().updateById(lot);
			logger.info("execution LotRenderingController.lotCancel - change status CANCELED");
		}
		logger.info("exection LotRenderingController.lotCancel successfully finished");
		return myLotList(session);
	}
}
