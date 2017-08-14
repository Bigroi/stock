package com.bigroi.stock.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

	@RequestMapping(value = "/MyLotListAuthJSON.spr")
	@ResponseBody
	public String myLotList(HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			List<Lot> lots = DaoFactory.getLotDao().getBySellerId(user.getCompanyId());
			return new ResultBean(1, lots).toString();
		} catch (DaoException e) {
			return new ResultBean(-1, e.getMessage()).toString();
		}
	}

	@RequestMapping(value = "/LotFormAuthJSON.spr")
	@ResponseBody
	public String getLot(@RequestParam("id") long id, HttpSession session)  {
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
			return new ResultBean(1, lot).toString();
		}catch(DaoException e){
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
			@RequestParam("status") Status status, HttpSession session){
		try {
			Lot lot = new Lot();
			lot.setDescription(description);
			lot.setPoductId(productId);
			lot.setMinPrice(minPrice);
			lot.setSellerId(salerId);
			lot.setDateStr(expDateStr);
			lot.setStatus(status);

			if (id == -1) {
				DaoFactory.getLotDao().add(lot);
				id = lot.getId();
			} else {
				lot.setId(id);
				DaoFactory.getLotDao().updateById(lot);
			}
			return new ResultBean(1, "lot.update.success").toString();
		} catch (DaoException | ParseException e) {
			return new ResultBean(-1, e.getMessage()).toString();
		}
	}
	
	@RequestMapping(value = "/LotInGameAuthJSON.spr")
	@ResponseBody
	public String setLotInGame(@RequestParam("id") long id, HttpSession session) {
		try{
			Lot lot = DaoFactory.getLotDao().getById(id);
			if (lot.getStatus() == Status.DRAFT){
				lot.setStatus(Status.IN_GAME);
				DaoFactory.getLotDao().updateById(lot);
			}
			return new ResultBean(1, "lot.update.success").toString();//nullPointerException
		}catch(DaoException e){
			return new ResultBean(-1, e.getMessage()).toString();
		}
	}
	
	
	@RequestMapping(value = "/LotCancelAuthJSON.spr")
	@ResponseBody
	public String lotCancel(@RequestParam("id") long id) {
		try{
			Lot lot = DaoFactory.getLotDao().getById(id);
			if ((lot.getStatus() == Status.DRAFT) || (lot.getStatus() == Status.IN_GAME)){
				lot.setStatus(Status.CANCELED);
				DaoFactory.getLotDao().updateById(lot);
			}
			return new ResultBean(1, "lot.update.success").toString();//nullPointerException
		}catch(DaoException e){
			return new ResultBean(-1, e.getMessage()).toString();
		}
	}
	
}
