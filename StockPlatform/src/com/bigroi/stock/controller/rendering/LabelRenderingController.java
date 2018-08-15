package com.bigroi.stock.controller.rendering;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.controller.BaseRenderingController;

@Controller
@RequestMapping("/label/admin/")
public class LabelRenderingController extends BaseRenderingController{
	
	@RequestMapping("/List.spr")
	@Secured("ROLE_ADMIN")
	public ModelAndView getListLablesAll() {
		return createModelAndView("lables");
	}
	
	@RequestMapping("/Form.spr")
	@Secured("ROLE_ADMIN")
	public ModelAndView form(@RequestParam(value = "id", defaultValue = "-1") long id) {
		return createModelAndView("labelForm");
	}

}
