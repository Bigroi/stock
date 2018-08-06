package com.bigroi.stock.controller.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.common.DealStatus;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.bean.ui.DealForUI;
import com.bigroi.stock.bean.ui.TestDealForUI;
import com.bigroi.stock.controller.BaseResourseController;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.DealService;
import com.bigroi.stock.service.ServiceException;

@Controller
@RequestMapping(value = "/deal/json", produces = "text/plain;charset=UTF-8")
public class DealResourseController extends BaseResourseController {

	private static final String NOT_AUTORISED_ERROR_LABEL = "label.deal.not_authorized";
	private static final String TRANSPORT_SUCCESS_LABEL = "label.deal.transport";
	private static final String REJECT_SUCCESS_LABEL = "label.deal.rejected";
	private static final String APPROVE_SUCCESS_LABEL = "label.deal.approved";
	
	@Autowired
	private DealService dealService;
	
	@RequestMapping(value = "/Form.spr", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String form(@RequestParam("id") long id, Authentication loggedInUser) 
					throws ServiceException {
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		Deal deal = dealService.getById(id, user.getCompanyId());
		if (user.getCompanyId() != deal.getBuyerAddress().getCompanyId() && 
				user.getCompanyId() != deal.getSellerAddress().getCompanyId()){
			return new ResultBean(-1, NOT_AUTORISED_ERROR_LABEL).toString();
		} else {
			return new ResultBean(1, translateDeal(new DealForUI(deal, user.getCompanyId())), "").toString();
		}
	}
	
	private DealForUI translateDeal(DealForUI deal){
		deal.setStatus(this.getLabelValue(deal.getStatus()));
		return deal;
	}
	
	@RequestMapping(value = "/MyDeals.spr", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String myDealList(Authentication loggedInUser) 
			throws ServiceException, TableException {
		StockUser userBean = (StockUser) loggedInUser.getPrincipal();
		List<Deal> deals = dealService.getByUserId(userBean.getCompanyId());
		List<DealForUI> dealsForUI = deals.stream()
				.map(d -> new DealForUI(d, userBean.getCompanyId()))
				.map(this::translateDeal)
				.collect(Collectors.toList());
		TableResponse<DealForUI> table = new TableResponse<>(DealForUI.class, dealsForUI);
		return new ResultBean(1, table, "").toString();
	}
	
	@RequestMapping(value = "/TestDeals.spr")
	@ResponseBody
	public String testDealList(Authentication loggedInUser) 
			throws ServiceException, TableException {
		TableResponse<TestDealForUI> table = new TableResponse<>(TestDealForUI.class, new ArrayList<>());
		return new ResultBean(1, table, "").toString();
	}
	
	@RequestMapping(value = "/Approve.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String approve(@RequestParam("json") String json, Authentication loggedInUser) 
			throws ServiceException, TableException {
		StockUser userBean = (StockUser) loggedInUser.getPrincipal();
		DealForUI deal = GsonUtil.getGson().fromJson(json, DealForUI.class);
		
		deal.setStatus(DealStatus.ON_PARTNER_APPROVE.toString());
		if (dealService.approve(deal.getId(), userBean.getCompanyId())){
			return new ResultBean(1, deal, APPROVE_SUCCESS_LABEL).toString();
		} else {
			return new ResultBean(-1, NOT_AUTORISED_ERROR_LABEL).toString();
		}
	}
	
	@RequestMapping(value = "/Reject.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String reject(@RequestParam("json") String json, Authentication loggedInUser) 
			throws ServiceException, TableException {
		StockUser userBean = (StockUser) loggedInUser.getPrincipal();
		DealForUI deal = GsonUtil.getGson().fromJson(json, DealForUI.class);
		
		deal.setStatus(DealStatus.REJECTED.toString());
		if (dealService.reject(deal.getId(), userBean.getCompanyId())){
			return new ResultBean(1, deal, REJECT_SUCCESS_LABEL).toString();
		} else {
			return new ResultBean(-1, NOT_AUTORISED_ERROR_LABEL).toString();
		}
	}
	
	@RequestMapping(value = "/Transport.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String transport(@RequestParam("json") String json, Authentication loggedInUser) 
			throws ServiceException, TableException {
		StockUser userBean = (StockUser) loggedInUser.getPrincipal();
		DealForUI deal = GsonUtil.getGson().fromJson(json, DealForUI.class);
		
		deal.setStatus(DealStatus.TRANSPORT.toString());
		if (dealService.transport(deal.getId(), userBean.getCompanyId())){
			return new ResultBean(1, deal, TRANSPORT_SUCCESS_LABEL).toString();
		} else {
			return new ResultBean(-1, NOT_AUTORISED_ERROR_LABEL).toString();
		}
	}
}
