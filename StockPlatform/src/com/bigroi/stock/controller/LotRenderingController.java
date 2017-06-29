package com.bigroi.stock.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

@Controller
public class LotRenderingController {
	@RequestMapping("/LotFormAuth.spr")
	public ModelAndView lotEdit(@RequestParam("id") long id, HttpSession session) throws DaoException {
		ModelMap model = new ModelMap();
		Lot lot;
		if (id == -1) {
			lot = new Lot();			
			User user = (User) session.getAttribute("user");
			lot.setSalerId(user.getCompanyId());
			//TODO Какой статус будем присваивать
			//TODO Status.ENUM lot.setStatus((byte) 1);
			model.addAttribute("id", -1);
		} else {
			lot = DaoFactory.getLotDao().getById(id);
			model.addAttribute("id", lot.getId());
		}		
		model.addAttribute("lot", lot);
		return new ModelAndView("lotForm", model);
	}
	
	@RequestMapping("/LotSaveAuth.spr")
	public ModelAndView lotSave(@RequestParam("id") long id, 
			@RequestParam("description") String description,			
			@RequestParam("productId") long productId,
			@RequestParam("minPrice") double minPrice,
			@RequestParam("salerId") long salerId,
			@RequestParam("expDate") String expDateStr,
			@RequestParam("status") byte status,
			HttpSession session) throws DaoException, ParseException {

		Lot lot = new Lot();
		lot.setDescription(description);
		lot.setPoductId(productId);
		lot.setMinPrice(minPrice);
		lot.setSalerId(salerId);
		lot.setDateStr(expDateStr);
		//TODO Status.ENUM lot.setStatus(status);		
		
		if (id == -1) {
			DaoFactory.getLotDao().add(lot);
			id = lot.getId();
		} else {
			lot.setId(id);
			DaoFactory.getLotDao().update(lot.getId(), lot);			
		}
//		return lotEdit(id, session);
		return myLotList(session);
	}
	
	@RequestMapping("/MyLotListAuth.spr")
	public ModelAndView myLotList(HttpSession session) throws DaoException {
		User user = (User) session.getAttribute("user");
		List<Lot> lots = DaoFactory.getLotDao().getBySalerId(user.getCompanyId());
		return new ModelAndView("myLotList", "listOfLots", lots);
	}

}
