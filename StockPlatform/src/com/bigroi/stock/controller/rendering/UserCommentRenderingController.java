package com.bigroi.stock.controller.rendering;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.controller.BaseRenderingController;

@Controller
@RequestMapping("/feedback")
public class UserCommentRenderingController extends BaseRenderingController{
	
	@RequestMapping("/Feedback.spr")
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	public ModelAndView form(@RequestParam(value = "id", defaultValue = "-1") long id) {
		ModelAndView modelAndView = createModelAndView("feedbackDeal");
		modelAndView.addObject("id", id);
		return modelAndView;
	}

}
