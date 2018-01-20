package com.bigroi.stock.controller.resource;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Deal;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.Table;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping(value = "/deal/json", produces = "text/plain;charset=UTF-8")
public class DealResourseController extends BaseResourseController {

	@RequestMapping(value = "/Form.spr", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String form(@RequestParam("id") long id, Authentication loggedInUser) 
					throws ServiceException {
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		Deal deal = ServiceFactory.getDealService().getById(id, user.getCompanyId());
		if (user.getCompanyId() != deal.getCustomerId() && user.getCompanyId() != deal.getSellerId()){
			return new ResultBean(-1, "label.deal.not_authorized").toString();
		} else {
			return new ResultBean(1, deal).toString();
		}
	}
	
	@RequestMapping(value = "/MyDeals.spr", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String myDealList(Authentication loggedInUser) 
			throws ServiceException, TableException {
		StockUser userBean = (StockUser) loggedInUser.getPrincipal();
		List<Deal> deals = ServiceFactory.getDealService().getByUserId(userBean.getCompanyId());
		Table<Deal> table = new Table<>(Deal.class, deals);
		return new ResultBean(1, table).toString();
	}
	
	@RequestMapping(value = "/Approve.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String approve(@RequestParam("json") String json, Authentication loggedInUser) 
			throws ServiceException, TableException {
		StockUser userBean = (StockUser) loggedInUser.getPrincipal();
		Deal deal = gson.fromJson(json, Deal.class);
		if (ServiceFactory.getDealService().approve(deal.getId(), userBean.getCompanyId())){
			return new ResultBean(1, "label.deal.approved").toString();
		} else {
			return new ResultBean(-1, "label.deal.not_authorized").toString();
		}
	}
	
	@RequestMapping(value = "/Reject.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String reject(@RequestParam("json") String json, Authentication loggedInUser) 
			throws ServiceException, TableException {
		StockUser userBean = (StockUser) loggedInUser.getPrincipal();
		Deal deal = gson.fromJson(json, Deal.class);
		
		if (ServiceFactory.getDealService().reject(deal.getId(), userBean.getCompanyId())){
			return new ResultBean(1, "label.deal.rejected").toString();
		} else {
			return new ResultBean(-1, "label.deal.not_authorized").toString();
		}
	}
}
