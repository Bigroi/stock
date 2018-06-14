package com.bigroi.stock.controller.rendering;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.controller.BaseRenderingController;

@Controller
@RequestMapping("feedback")
public class FeedBackRenderingController extends BaseRenderingController {

	@RequestMapping("/Form.spr")
	public ModelAndView getForm(){
		return createModelAndView("feedback");
	}

}
