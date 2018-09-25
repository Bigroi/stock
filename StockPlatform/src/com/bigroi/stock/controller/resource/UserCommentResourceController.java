package com.bigroi.stock.controller.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.controller.BaseResourseController;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.service.UserCommentService;

@Controller
@RequestMapping("feedback/json")
public class UserCommentResourceController extends BaseResourseController {

	@Autowired
	private UserCommentService userCommentService;
	
	@RequestMapping("List.spr")
	@ResponseBody
	public String getList(@RequestParam("companyId") long companyId){
		return new ResultBean(1, userCommentService.getComments(companyId), null).toString();
	}
	
}
