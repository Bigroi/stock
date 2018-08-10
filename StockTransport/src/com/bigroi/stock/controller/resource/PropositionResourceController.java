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

import com.bigroi.stock.bean.db.Proposition;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.bean.ui.PropositionForUI;
import com.bigroi.stock.bean.ui.TransportForUI;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.PropositionService;

@Controller
@RequestMapping(value = "/proposition/json", produces = "text/plain;charset=UTF-8")
public class PropositionResourceController {
	
	@Autowired
	private PropositionService propService;
	
	@RequestMapping(value = "/MyPropositions.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String myDeallist(){
		List<Proposition> prop =  propService.getListProposition();
		List<TransportForUI> propForUI =  prop.stream().map(TransportForUI::new).collect(Collectors.toList());
		TableResponse<TransportForUI> table = new TableResponse<>(TransportForUI.class, propForUI);
		return new ResultBean(1, table, null).toString();
	}
	
	@RequestMapping(value = "/Delete.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String delete(@RequestParam("dealId") long id, Authentication loggedInUser) {
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		propService.delete(id, user.getCompany().getId());
		return new ResultBean(1, "success").toString();
	}
	
	@RequestMapping(value = "/MyHystory.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String myHystory(Authentication loggedInUser) {
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		List<Proposition> prop =  propService.getListHystoryProposition(user.getCompanyId());
		List<TransportForUI> propForUI =  prop.stream().map(TransportForUI::new).collect(Collectors.toList());
		TableResponse<TransportForUI> table = new TableResponse<>(TransportForUI.class, propForUI);
		return new ResultBean(1, table, null).toString();
	}
	
	@RequestMapping(value = "/Propositions.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String proposions() {
		List<Proposition> prop =  propService.getListPropositionsByTrans();
		List<PropositionForUI> propForUI =  prop.stream().map(PropositionForUI::new).collect(Collectors.toList());
		TableResponse<PropositionForUI> table = new TableResponse<>(PropositionForUI.class, propForUI);
		return new ResultBean(1, table, null).toString();
	}

}
