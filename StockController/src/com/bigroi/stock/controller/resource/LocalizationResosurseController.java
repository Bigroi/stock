package com.bigroi.stock.controller.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.controller.BaseResourseController;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.service.LabelService;
import com.bigroi.stock.util.LabelUtil;

@Controller
@RequestMapping("/l10n/json")
public class LocalizationResosurseController extends BaseResourseController{

	@Autowired
	private LabelService labelService;
	
	@RequestMapping(value = "/Labels.spr")
	@ResponseBody
	public String labels() {
		return new ResultBean(1, labelService.getAllLabel(getLanguage()), "").toString();
	}
	
	@RequestMapping(value = "/ChangeLanguage.spr")
	@ResponseBody
	public String changeLanguage(@RequestParam("lang") String lang) {
		setLanguage(LabelUtil.parseString(lang));
		return new ResultBean(1, null).toString();
	}
	
}
