package com.bigroi.stock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.dao.DaoException;

@Controller
public class LotRenderingController {
	@RequestMapping("/LotFormAuth.spr")
	public ModelAndView lotEdit(@RequestParam("id") long id) throws DaoException {
		ModelMap model = new ModelMap();
		Lot lot;
		if (id == -1) {
			lot = new Lot();
			model.addAttribute("id", -1);
		} else {
//TODO getById			lot = DaoFactory.
//			model.addAttribute("id", lot.getId());
		}		
//		model.addAttribute("lot", lot);
		return new ModelAndView("lotForm", model);
	}

}
