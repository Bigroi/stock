package com.bigroi.stock.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.DaoException;

@Controller
public class TenderRenderingController {

	@RequestMapping("/TenderFormAuth.spr")
	public ModelAndView lotEdit(@RequestParam("id") long id, HttpSession session) throws DaoException {
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
			// TODO Заглушка
			tender = new Tender();//
			tender.setDescription("454454545454545454");//

			// lot = DaoFactory.getLotDao().getById(id);
			model.addAttribute("id", tender.getId());
		}
		model.addAttribute("tender", tender);
		return new ModelAndView("tenderForm", model);
	}

}
