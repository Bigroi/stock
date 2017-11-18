package com.bigroi.stock.controller.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.json.ResultBean;
import com.google.gson.Gson;

@Controller
@RequestMapping("/l10n/json")
public class LocalizationResosurseController {

	@RequestMapping(value = "/Lables.spr")
	@ResponseBody
	//FIXME think about localization
	public String Lables(@RequestParam String json) {
		Map<String, String> map = new HashMap<>();
		for (Object key : new Gson().fromJson(json, List.class)) {
			String str = ResourceBundle.getBundle("messages").getString(key.toString());
			map.put(key.toString(), str);
		}
		return new ResultBean(1, map).toString();
	}

}
