package com.bigroi.stock.controller.resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.controller.BaseRenderingController;
import com.bigroi.stock.json.ResultBean;

@Controller
@RequestMapping("/l10n/json")
public class LocalizationResosurseController extends BaseRenderingController{

	@RequestMapping(value = "/Labels.spr")
	@ResponseBody
	public String Labels() {
		Map<String, String> result = new HashMap<>();
		Map<String, Object> labels = createModelAndView("").getModel();
		
		addValues(labels, null, result);
		return new ResultBean(1, result, "").toString();
	}

	@SuppressWarnings("unchecked")
	private void addValues(Object value, String key, Map<String, String> result){
		if (value instanceof Map){
			for (String subKey : ((Map<String, ?>) value).keySet()){
				String newKey = (key == null? "" : key +".") + subKey;
				addValues(((Map<?,?>) value).get(subKey), newKey, result);
			}
		} else {
			result.put(key, value.toString());
		}
	}
}
