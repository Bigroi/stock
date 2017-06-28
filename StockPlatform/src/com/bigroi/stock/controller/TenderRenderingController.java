package com.bigroi.stock.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

@Controller
public class TenderRenderingController {

	@RequestMapping("/TenderFormAuth.spr")
	public ModelAndView tenderEdit(@RequestParam("id") long id, HttpSession session) throws DaoException {
		ModelMap model = new ModelMap();
		Tender tender;
		if (id == -1) {
			tender = new Tender();
			User user = (User) session.getAttribute("user");
			tender.setCustomerId(user.getCompanyId());
			// TODO Какой статус будем присваивать
			tender.setStatus((byte) 1);
			model.addAttribute("id", -1);
		} else {
			tender = DaoFactory.getTenderDao().getById(id);
			model.addAttribute("id", tender.getId());
		}
		model.addAttribute("tender", tender);
		return new ModelAndView("tenderForm", model);
	}

	@RequestMapping("/TenderSaveAuth.spr")
	public ModelAndView tenderSave(@RequestParam("id") long id, 
			@RequestParam("description") String description,			
			@RequestParam("productId") long productId,
			@RequestParam("maxPrice") double maxPrice,
			@RequestParam("customerId") long customerId,
			@RequestParam("expDate") String expDateStr,
			@RequestParam("status") byte status,
			HttpSession session) throws DaoException, ParseException {

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
		} else {
			tender.setId(id);
			DaoFactory.getTenderDao().update(tender.getId(), tender);			
		}
//		return tenderEdit(id, session);
		return myTenderList(session);
	}
	
	@RequestMapping("/MyTenderListAuth.spr")
	public ModelAndView myTenderList(HttpSession session) throws DaoException {
		User user = (User) session.getAttribute("user");
		List<Tender> tenders = DaoFactory.getTenderDao().getByCustomerId(user.getCompanyId());
		return new ModelAndView("myTenderList", "listOfTenders", tenders);
	}
}
