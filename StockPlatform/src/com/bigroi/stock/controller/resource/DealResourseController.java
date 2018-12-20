package com.bigroi.stock.controller.resource;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.common.DealStatus;
import com.bigroi.stock.bean.db.Comment;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.bean.db.UserComment;
import com.bigroi.stock.bean.ui.CommentForUI;
import com.bigroi.stock.bean.ui.DealForUI;
import com.bigroi.stock.bean.ui.TestDealForUI;
import com.bigroi.stock.controller.BaseResourseController;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.CommentService;
import com.bigroi.stock.service.DealService;
import com.bigroi.stock.service.LabelService;
import com.bigroi.stock.service.TradeService;
import com.bigroi.stock.service.UserCommentService;

@Controller
@RequestMapping(value = "/deal/json", produces = "text/plain;charset=UTF-8")
public class DealResourseController extends BaseResourseController {

	private static final String NOT_AUTORISED_ERROR_LABEL = "label.deal.not_authorized";
	private static final String TRANSPORT_SUCCESS_LABEL = "label.deal.transport";
	private static final String REJECT_SUCCESS_LABEL = "label.deal.rejected";
	private static final String APPROVE_SUCCESS_LABEL = "label.deal.approved";
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private DealService dealService;
	
	@Autowired
	private LabelService labelService;
	
	@Autowired
	private UserCommentService userCommentService;
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value = "/Form", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String form(@RequestParam("id") long id, Authentication loggedInUser) {
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		Deal deal = dealService.getById(id, user.getCompanyId());
		int parterMark = getPartnerMark(user.getCompanyId(), deal);
		if (user.getCompanyId() != deal.getBuyerCompanyId() && 
				user.getCompanyId() != deal.getSellerCompanyId()){
			return new ResultBean(-1, NOT_AUTORISED_ERROR_LABEL).toString();
		} else {
			deal.setProductName(labelService.getLabel(deal.getProductName(), "name", getLanguage()));
			return new ResultBean(1, translateDeal(new DealForUI(deal, user.getCompanyId(), parterMark)), "").toString();
		}
	}
	
	private int getPartnerMark(long companyId, Deal deal) {
		long partnerId = 
				companyId == deal.getBuyerCompanyId() ?
				deal.getSellerCompanyId() : 
				deal.getBuyerCompanyId();
		List<UserComment> comments = userCommentService.getComments(partnerId);
		if (comments.isEmpty()){
			return 5;
		} else {
			return (int)comments.stream().mapToInt(UserComment::getMark).average().getAsDouble();
		}
	}

	private DealForUI translateDeal(DealForUI deal) {
		deal.setStatus(labelService.getLabel("label", deal.getStatus(), getLanguage()));
		return deal;
	}
	
	@RequestMapping(value = "/Picture", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public ResponseEntity<byte[]> picture(@RequestParam("dealId") long dealId, Authentication loggedInUser) {
		StockUser userBean = (StockUser) loggedInUser.getPrincipal();
		String picture = 
				dealService.getById(dealId, userBean.getCompanyId())
				.getSellerFoto();
		byte[] bytes = Base64.getDecoder().decode(picture.substring(picture.indexOf(',') + 1)
				.getBytes());
		
		return new ResponseEntity<>(bytes, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/MyDeals", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String myDealList(Authentication loggedInUser) {
		StockUser userBean = (StockUser) loggedInUser.getPrincipal();
		List<Deal> deals = dealService.getByUserId(userBean.getCompanyId());
		List<DealForUI> dealsForUI = deals.stream()
				.map(d -> {
					d.setProductName(labelService.getLabel(d.getProductName(), "name", getLanguage()));
					return new DealForUI(d, userBean.getCompanyId());
				})
				.map(this::translateDeal)
				.collect(Collectors.toList());
		TableResponse<DealForUI> table = new TableResponse<>(DealForUI.class, dealsForUI);
		return new ResultBean(1, table, "").toString();
	}
	
	@RequestMapping(value = "/TestDeals")
	@ResponseBody
	public String testDealList(Authentication loggedInUser) {
		TableResponse<TestDealForUI> table = new TableResponse<>(TestDealForUI.class, new ArrayList<>());
		return new ResultBean(1, table, null).toString();
	}
	
	@RequestMapping(value = "/TestClean")
	@ResponseBody
	public String testClean(HttpSession session) {
		session.invalidate();
		return new ResultBean(1, null).toString();
	}
	
	@RequestMapping(value = "/TestTrade")
	@ResponseBody
	public String testTrade(Authentication loggedInUser) {
		List<Deal> deals = tradeService.newInstance().testTrade(getSessionId());
		List<TestDealForUI> dealsForUI = deals.stream()
				.map( d -> {
					d.setProductName(labelService.getLabel(d.getProductName(), "name", getLanguage()));
					return new TestDealForUI(d);
				})
				.collect(Collectors.toList());
		TableResponse<TestDealForUI> table = new TableResponse<>(TestDealForUI.class, dealsForUI);
		return new ResultBean(1, table, "").toString();
	}
	
	@RequestMapping(value = "/Approve")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String approve(@RequestParam("json") String json, Authentication loggedInUser) {
		StockUser userBean = (StockUser) loggedInUser.getPrincipal();
		DealForUI deal = GsonUtil.getGson().fromJson(json, DealForUI.class);
		
		deal.setStatus(DealStatus.ON_PARTNER_APPROVE.toString());
		if (dealService.approve(deal.getId(), userBean.getCompanyId())){
			return new ResultBean(2, deal, APPROVE_SUCCESS_LABEL).toString();
		} else {
			return new ResultBean(-1, NOT_AUTORISED_ERROR_LABEL).toString();
		}
	}
	
	@RequestMapping(value = "/Reject")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String reject(@RequestParam("json") String json, Authentication loggedInUser){
		StockUser userBean = (StockUser) loggedInUser.getPrincipal();
		DealForUI deal = GsonUtil.getGson().fromJson(json, DealForUI.class);
		
		deal.setStatus(DealStatus.REJECTED.toString());
		if (dealService.reject(deal.getId(), userBean.getCompanyId())){
			return new ResultBean(2, deal, REJECT_SUCCESS_LABEL).toString();
		} else {
			return new ResultBean(-1, NOT_AUTORISED_ERROR_LABEL).toString();
		}
	}
	
	@RequestMapping(value = "/Transport")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String transport(@RequestParam("json") String json, Authentication loggedInUser) {
		StockUser userBean = (StockUser) loggedInUser.getPrincipal();
		DealForUI deal = GsonUtil.getGson().fromJson(json, DealForUI.class);
		
		deal.setStatus(DealStatus.TRANSPORT.toString());
		if (dealService.transport(deal.getId(), userBean.getCompanyId())){
			return new ResultBean(2, deal, TRANSPORT_SUCCESS_LABEL).toString();
		} else {
			return new ResultBean(-1, NOT_AUTORISED_ERROR_LABEL).toString();
		}
	}
	
	@RequestMapping(value = "/Comments", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String Company(Authentication loggedInUser){
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		List<Comment> list = commentService.getCommentsByCompanyId(user.getCompanyId());
		List<CommentForUI> listForUI = list.stream().map(CommentForUI::new).collect(Collectors.toList());
		TableResponse<CommentForUI> tableResponse = new TableResponse<>(CommentForUI.class, listForUI);
			return new ResultBean(1, tableResponse,"some message").toString();
		
	}
}
