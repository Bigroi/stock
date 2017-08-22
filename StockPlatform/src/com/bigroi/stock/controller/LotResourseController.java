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
import com.google.gson.Gson;

@Controller
@RequestMapping("/json")
public class LotResourseController extends ResourseBeanException {

	private static final Logger logger = Logger.getLogger(LotResourseController.class);
	
	@RequestMapping(value = "/MyLotListAuth.spr")
	@ResponseBody
	public String myLotList(@RequestParam("json") String json, HttpSession session) throws DaoException {
		logger.info("exection LotResourseController.myLotList");
		logger.info(json);
		logger.info(session);
		User userBean = new Gson().fromJson(json, User.class);
		userBean = (User) session.getAttribute("user");
			List<Lot> lots = DaoFactory.getLotDao().getBySellerId(userBean.getCompanyId());
			logger.info("exection LotResourseController.myLotList successfully finished");
			return new ResultBean(1, lots).toString();	
	}

	@RequestMapping(value = "/LotFormAuth.spr")
	@ResponseBody
	public String getLot(@RequestParam("id") long id,
			@RequestParam("jsonUser") String jsonUser,  
			@RequestParam("jsonLot") String jsonLot,
			HttpSession session) throws DaoException  {
		logger.info("exection LotResourseController.getLot");
		logger.info(id);
		logger.info(jsonUser);
		logger.info(jsonLot);
		logger.info(session);
		User userBean = new Gson().fromJson(jsonUser, User.class);
			Lot lotBean = new Gson().fromJson(jsonLot, Lot.class);
			if (id == -1) {
				lotBean = new Lot();
				 userBean = (User) session.getAttribute("user");
				 lotBean.setSellerId(userBean.getCompanyId());
				 lotBean.setStatus(Status.DRAFT);
			} else {
				lotBean = DaoFactory.getLotDao().getById(id);
			}
			logger.info("exection LotResourseController.getLot successfully finished");
			return new ResultBean(1, lotBean).toString();
		
	}
	
	@RequestMapping(value = "/LotSaveAuth.spr")
	@ResponseBody
	public String lotSave(@RequestParam("id") long id,
			//@RequestParam("jsonUser") String jsonUser,
			@RequestParam("jsonLot") String jsonLot,
			 HttpSession session) throws DaoException, ParseException{
		logger.info("exection LotResourseController.lotSave");
		logger.info(id);
		//logger.info(jsonUser);
		logger.info(jsonLot);
		logger.info(session);
		//User userBean = new Gson().fromJson(jsonUser, User.class);
		//userBean = (User) session.getAttribute("user");
		Lot lotBean = new Gson().fromJson(jsonLot, Lot.class);//TODO: ������ ����� ���������  -1/1
			if (id == -1) {
				DaoFactory.getLotDao().add(lotBean);
				id = lotBean.getId();
				logger.info("exection LotResourseController.lotSave - 'lot.added.success', successfully finished");
			} else {
				lotBean.setId(id);
				DaoFactory.getLotDao().updateById(lotBean);
			}
			logger.info("exection LotResourseController.lotSave - 'lot.update.success', successfully finished");
			return new ResultBean(1, "lot.update.success").toString();
		
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
