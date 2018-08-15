package com.bigroi.stock.controller.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.db.Label;
import com.bigroi.stock.bean.ui.LabelForUI;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.LabelService;

@Controller
@RequestMapping("/label/json/admin")
public class LabelResourseController {
	
	@Autowired
	private LabelService labelService;
	
	@RequestMapping("/List.spr")
	@ResponseBody
	@Secured(value = {"ROLE_ADMIN"})
	public String getAllLabels(){
		List<Label> list = labelService.getAllLabel();
		List<LabelForUI> listForUI = list.stream().map(LabelForUI::new).collect(Collectors.toList());
		TableResponse<LabelForUI> tableResponse = new TableResponse<>(LabelForUI.class, listForUI);
		return new ResultBean(1, tableResponse, null).toString();
	}
	
	@RequestMapping(value = "/Form.spr", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	@Secured(value = {"ROLE_ADMIN"})
	public String form(@RequestParam(value = "id", defaultValue = "-1") long id, Authentication loggedInUser){
		if(loggedInUser.isAuthenticated()){
			Label label = labelService.getLabelById(id);
			return new ResultBean(1, new LabelForUI(label), null).toString();
		}
		return null;
	}
	
	@RequestMapping("/Save.spr")
	@ResponseBody
	@Secured(value = {"ROLE_ADMIN"})
	public String save(@RequestParam("json") String json){
		Label label = GsonUtil.getGson().fromJson(json, Label.class);
		labelService.merge(label);
		return new ResultBean(1, new LabelForUI(label), null).toString();
	}
	
	@RequestMapping("/Delete.spr")
	@ResponseBody
	@Secured(value = {"ROLE_ADMIN"})
	public String delete(@RequestParam("json") String json){
		Label label = GsonUtil.getGson().fromJson(json, Label.class);
		labelService.delete(label.getId());
		return new ResultBean(1, new LabelForUI(label), null).toString();
	}
}
