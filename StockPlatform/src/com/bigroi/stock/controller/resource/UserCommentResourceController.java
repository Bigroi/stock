package com.bigroi.stock.controller.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.bean.db.UserComment;
import com.bigroi.stock.controller.BaseResourseController;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.service.UserCommentService;

@Controller
@RequestMapping("feedback/json")
public class UserCommentResourceController extends BaseResourseController {
	
	@Autowired
	private UserCommentService userCommentService;
	
	@RequestMapping("List.spr")
	@ResponseBody
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	public String getList(@RequestParam("companyId") long companyId){
		return new ResultBean(1, userCommentService.getComments(companyId), null).toString();
	}
	
	@RequestMapping("Feedback.spr")
	@ResponseBody
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	public String getFeedbackForm(@RequestParam(value = "id", defaultValue = "-1") long id){
		UserComment userComment = userCommentService.userCommentById(id);
		return new ResultBean(1, new UserComment(userComment), "").toString();
		
	}
	
	@RequestMapping("SaveFeedback.spr")
	@ResponseBody
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	public String save(@RequestParam("json") String json, Authentication loggedInUser){
		StockUser user =  (StockUser) loggedInUser.getPrincipal();
		UserComment userComment = GsonUtil.getGson().fromJson(json, UserComment.class);
		userCommentService.merge(userComment, user.getCompanyId());
		return new ResultBean(1, new UserComment(userComment), null).toString();
	}
	
	@RequestMapping(value = "/Delete.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String delete(@RequestParam("id") long id, Authentication loggedInUser){
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		userCommentService.delete(id, user.getCompany().getId());
		return new ResultBean(1, null).toString();
	}
	
}
