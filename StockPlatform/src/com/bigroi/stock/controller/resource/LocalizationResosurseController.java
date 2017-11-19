package com.bigroi.stock.controller.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.controller.rendering.BaseRenderingController;
import com.bigroi.stock.json.ResultBean;
import com.google.gson.Gson;

@Controller
@RequestMapping("/l10n/json")
public class LocalizationResosurseController extends BaseRenderingController{

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Lables.spr")
	@ResponseBody
	public String Lables(@RequestParam String json) {
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> lables = createModelAndView("").getModel();
		for (Object key : new Gson().fromJson(json, List.class)) {
			String[] keyPath = key.toString().split("\\.");
			Object map = lables;
			for (String keyPathElement : keyPath){
				map = ((Map<String, Object>)map).get(keyPathElement);
			}
			
			result.put(key.toString(), map);
		}
		return new ResultBean(1, result).toString();
	}

}
