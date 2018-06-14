package com.bigroi.stock.controller.rendering;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.controller.BaseRenderingController;

@Controller
@RequestMapping("Message.spr")
public class MessageRenderingController extends BaseRenderingController {

	@RequestMapping
	public ModelAndView getMessage(
			@RequestParam("message") String message, 
			@RequestParam("type") String type){
		ModelAndView modelAndView = createModelAndView("message");
		modelAndView.addObject("message", getLabelValue(message));
		modelAndView.addObject("title", getLabelValue("label.message." + type));
		return modelAndView;
	}

}
