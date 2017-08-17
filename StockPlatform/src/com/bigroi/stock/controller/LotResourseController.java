package com.bigroi.stock.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.ResultBean;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

@Controller
public class LotResourseController {

	private static final Logger logger = Logger.getLogger(LotResourseController.class);
	
	@RequestMapping(value = "/MyLotListAuthJSON.spr")
	@ResponseBody
	public String myLotList(HttpSession session) {
		logger.info("exection LotResourseController.myLotList");
		logger.info(session);
		try {
			User user = (User) session.getAttribute("user");
			List<Lot> lots = DaoFactory.getLotDao().getBySellerId(user.getCompanyId());
			logger.info("exection LotResourseController.myLotList successfully finished");
			return new ResultBean(1, lots).toString();
		} catch (DaoException e) {
			logger.info("execution LotResourseController.myLotList - catch DaoException");
			return new ResultBean(-1, e.getMessage()).toString();
		}
	}

	@RequestMapping(value = "/LotFormAuthJSON.spr")
	@ResponseBody
	public String getLot(@RequestParam("id") long id, HttpSession session)  {
		logger.info("exection LotResourseController.getLot");
		logger.info(id);
		logger.info(session);
		try{
			Lot lot;
			if (id == -1) {
				lot = new Lot();
				User user = (User) session.getAttribute("user");
				lot.setSellerId(user.getCompanyId());
				lot.setStatus(Status.DRAFT);
			} else {
				lot = DaoFactory.getLotDao().getById(id);
			}
			logger.info("exection LotResourseController.getLot successfully finished");
			return new ResultBean(1, lot).toString();
		}catch(DaoException e){
			logger.info("execution LotResourseController.getLot - catch DaoException");
			return new ResultBean(-1, e.getMessage()).toString();
		}
	}
	
	@RequestMapping(value = "/LotSaveAuthJSON.spr")//TODO: не корректно работает JSON
	@ResponseBody
	public String lotSave(@RequestParam("id") long id, 
			@RequestParam("description") String description,			
			@RequestParam("productId") long productId,
			@RequestParam("minPrice") double minPrice,
			@RequestParam("sellerId") long salerId,
			@RequestParam("expDate") String expDateStr,
			@RequestParam("volumeOfLot") int volumeOfLot,
			@RequestParam("status") Status status, HttpSession session){
		logger.info("exection LotResourseController.lotSave");
		logger.info(id);
		logger.info(description);
		logger.info(productId);
		logger.info(minPrice);
		logger.info(salerId);
		logger.info(expDateStr);
		logger.info(volumeOfLot);
		logger.info(status);
		logger.info(session);
		try {
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
			} else {
				lot.setId(id);
				DaoFactory.getLotDao().updateById(lot);
			}
			logger.info("exection LotResourseController.lotSave - 'lot.update.success', successfully finished");
			return new ResultBean(1, "lot.update.success").toString();
		} catch (DaoException | ParseException e) {
			logger.info("execution LotResourseController.lotSave - catch DaoException | ParseException");
			return new ResultBean(-1, e.getMessage()).toString();
		}
	}
	
	@RequestMapping(value = "/LotInGameAuthJSON.spr")
	@ResponseBody
	public String setLotInGame(@RequestParam("id") long id, HttpSession session) {
		logger.info("exection LotResourseController.setLotInGame");
		logger.info(id);
		logger.info(session);
		try{
			Lot lot = DaoFactory.getLotDao().getById(id);
			if (lot.getStatus() == Status.DRAFT){
				lot.setStatus(Status.IN_GAME);
				DaoFactory.getLotDao().updateById(lot);
			}
			logger.info("exection LotResourseController.setLotInGame - 'lot.update.success', successfully finished");
			return new ResultBean(1, "lot.update.success").toString();//nullPointerException
		}catch(DaoException e){
			logger.info("execution LotResourseController.lotSave - catch DaoException");
			return new ResultBean(-1, e.getMessage()).toString();
		}
	}
	
	
	@RequestMapping(value = "/LotCancelAuthJSON.spr")
	@ResponseBody
	public String lotCancel(@RequestParam("id") long id) {
		logger.info("exection LotResourseController.lotCancel");
		logger.info(id);
		try{
			Lot lot = DaoFactory.getLotDao().getById(id);
			if ((lot.getStatus() == Status.DRAFT) || (lot.getStatus() == Status.IN_GAME)){
				lot.setStatus(Status.CANCELED);
				DaoFactory.getLotDao().updateById(lot);
			}
			logger.info("exection LotResourseController.lotCancel - 'lot.update.success', successfully finished");
			return new ResultBean(1, "lot.update.success").toString();//nullPointerException
		}catch(DaoException e){
			logger.info("execution LotResourseController.lotCancel - catch DaoException");
			return new ResultBean(-1, e.getMessage()).toString();
		}
	}
	
}
