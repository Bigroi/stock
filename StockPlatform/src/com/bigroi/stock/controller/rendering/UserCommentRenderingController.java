package com.bigroi.stock.controller.rendering;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.controller.BaseRenderingController;

@Controller
@RequestMapping("deal/feedback")
public class UserCommentRenderingController extends BaseRenderingController{
	
	@RequestMapping("/Form.spr")
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView form(@RequestParam(value = "id", defaultValue = "-1") long id) {
		return createModelAndView("feedbackDealForm");
	}

}
