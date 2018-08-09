package com.bigroi.stock.controller.rendering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.controller.BaseRenderingController;
import com.bigroi.stock.service.LabelService;
import com.bigroi.stock.service.ServiceException;

@Controller
@RequestMapping("Message.spr")
public class MessageRenderingController extends BaseRenderingController {

	@Autowired
	private LabelService labelService;
	
	@RequestMapping
	public ModelAndView getMessage(
			@RequestParam("message") String message, 
			@RequestParam("type") String type) throws ServiceException{
		ModelAndView modelAndView = createModelAndView("message");
		String tmp[] = message.split("\\.");
		modelAndView.addObject("message", labelService.getLabel(tmp[1], tmp[2], getLanguage()));
		modelAndView.addObject("title", labelService.getLabel("message", type, getLanguage()));
		return modelAndView;
	}

}
