package com.bigroi.stock.controller.resource;

import java.util.List;
import java.util.stream.Collectors;

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
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.json.TableResponse;
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
		if (user.getCompanyId() != deal.getBuyerAddress().getCompanyId() && 
				user.getCompanyId() != deal.getSellerAddress().getCompanyId()){
			return new ResultBean(-1, "label.deal.not_authorized").toString();
		} else {
			return new ResultBean(1, new DealForUI(deal, user.getCompanyId()), "").toString();
		}
	}
	
	@RequestMapping(value = "/MyDeals.spr", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String myDealList(Authentication loggedInUser) 
			throws ServiceException, TableException {
		StockUser userBean = (StockUser) loggedInUser.getPrincipal();
		List<Deal> deals = ServiceFactory.getDealService().getByUserId(userBean.getCompanyId());
		List<DealForUI> dealsForUI = deals.stream()
				.map(d -> new DealForUI(d, userBean.getCompanyId())).collect(Collectors.toList());
		TableResponse<DealForUI> table = new TableResponse<>(DealForUI.class, dealsForUI);
		return new ResultBean(1, table, "").toString();
	}
	
	@RequestMapping(value = "/Approve.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String approve(@RequestParam("json") String json, Authentication loggedInUser) 
			throws ServiceException, TableException {
		StockUser userBean = (StockUser) loggedInUser.getPrincipal();
		DealForUI deal = GsonUtil.getGson().fromJson(json, DealForUI.class);
		deal.setStatus(DealStatus.ON_PARTNER_APPROVE);
		if (ServiceFactory.getDealService().approve(deal.getId(), userBean.getCompanyId())){
			return new ResultBean(1, deal, "label.deal.approved").toString();
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
		DealForUI deal = GsonUtil.getGson().fromJson(json, DealForUI.class);
		
		deal.setStatus(DealStatus.REJECTED);
		if (ServiceFactory.getDealService().reject(deal.getId(), userBean.getCompanyId())){
			return new ResultBean(1, deal, "label.deal.rejected").toString();
		} else {
			return new ResultBean(-1, "label.deal.not_authorized").toString();
		}
	}
	
	@RequestMapping(value = "/Transport.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String transport(@RequestParam("json") String json, Authentication loggedInUser) 
			throws ServiceException, TableException {
		StockUser userBean = (StockUser) loggedInUser.getPrincipal();
		DealForUI deal = GsonUtil.getGson().fromJson(json, DealForUI.class);
		
		deal.setStatus(DealStatus.ON_PARTNER_APPROVE);
		if (ServiceFactory.getDealService().transport(deal.getId(), userBean.getCompanyId())){
			return new ResultBean(1, deal, "label.deal.rejected").toString();
		} else {
			return new ResultBean(-1, "label.deal.not_authorized").toString();
		}
	}
}
