package com.bigroi.stock.controller.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.db.Proposition;
import com.bigroi.stock.bean.ui.PropositionForUI;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.PropositionService;
import com.bigroi.stock.service.ServiceException;

@Controller
@RequestMapping(value = "/proposition/json", produces = "text/plain;charset=UTF-8")
public class PropositionResourceController {
	
	@Autowired
	private PropositionService propService;
	
	@RequestMapping(value = "/MyPropositions.spr")
	@ResponseBody
	public String MyDeallist() throws ServiceException, TableException{
		List<Proposition> prop =  propService.getListProposition();
		List<PropositionForUI> propForUI =  prop.stream().map(PropositionForUI::new).collect(Collectors.toList());
		TableResponse<PropositionForUI> table = new TableResponse<>(PropositionForUI.class, propForUI);
		return new ResultBean(1, table, null).toString();
	}

}
