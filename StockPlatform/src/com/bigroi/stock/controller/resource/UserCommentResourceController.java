package com.bigroi.stock.controller.resource;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.bean.db.UserComment;
import com.bigroi.stock.controller.BaseResourseController;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.service.DealService;
import com.bigroi.stock.service.UserCommentService;

@Controller
@RequestMapping("deal/feedback/json")
public class UserCommentResourceController extends BaseResourseController {
	
	private static final String MARK_ERROR_LABEL = "label.user_comment.mark";

	private static final String UNAUTHORIZED_ACCESS_LABEL = "label.user_comment.unauthorized";
	
	@Autowired
	private UserCommentService userCommentService;
	
	@Autowired
	private DealService dealService;
	
	@RequestMapping("List")
	@ResponseBody
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	public String getList(@RequestParam("companyId") long companyId){
		return new ResultBean(1, userCommentService.getComments(companyId), null).toString();
	}
	
	@RequestMapping("Form")
	@ResponseBody
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	public String getFeedbackForm(
			@RequestParam(value = "dealId") long dealId,
			Authentication loggedInUser){
		StockUser user = (StockUser) loggedInUser.getPrincipal();
		UserComment userComment = userCommentService.getUserCommentByDealId(dealId, user.getCompanyId());
		return new ResultBean(1, userComment, "").toString();
		
	}
	
	@RequestMapping("Save")
	@ResponseBody
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	public String save(@RequestParam("json") String json, Authentication loggedInUser){
		StockUser user = (StockUser) loggedInUser.getPrincipal();
		UserComment userComment = GsonUtil.getGson().fromJson(json, UserComment.class);
		if(userComment.getMark() > 5 || userComment.getMark() < 1){
			return new ResultBean(-1, MARK_ERROR_LABEL).toString();
		}
		Deal deal = dealService.getById(userComment.getDealId(), user.getCompanyId());
		if (deal == null){
			return new ResultBean(-1, UNAUTHORIZED_ACCESS_LABEL).toString();
		}
		
		userComment.setReporterId(user.getCompanyId());
		userComment.setCommentDate(new Date());
		if (user.getCompanyId() == deal.getBuyerCompanyId()){
			userComment.setCompanyId(deal.getSellerCompanyId());
		} else {
			userComment.setCompanyId(deal.getBuyerCompanyId());
		}
		
		userCommentService.merge(userComment);
		return new ResultBean(1, userComment, null).toString();
	}
}
