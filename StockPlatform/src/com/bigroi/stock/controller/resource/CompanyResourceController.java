package com.bigroi.stock.controller.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.db.Company;
import com.bigroi.stock.bean.ui.CompanyForUI;
import com.bigroi.stock.controller.BaseResourseController;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.CompanyService;

@Controller
@RequestMapping("/company/json/admin")
public class CompanyResourceController extends BaseResourseController {

	@Autowired
	private CompanyService companyService;
	
	@RequestMapping("/List.spr")
	@ResponseBody
	@Secured(value = {"ROLE_ADMIN"})
	public String getListCompanyAll() {
		List<Company> list = companyService.getAllCompanies();
		List<CompanyForUI> listForUI = list.stream().map(CompanyForUI::new).collect(Collectors.toList());
		TableResponse<CompanyForUI> tableResponse = new TableResponse<>(CompanyForUI.class, listForUI);
		return new ResultBean(1, tableResponse, null).toString();
	}

	@RequestMapping("/ChangeStatus.spr")
	@ResponseBody
	@Secured(value = {"ROLE_ADMIN"})
	public String changeStatus(@RequestParam("id") long id) {
		companyService.changeStatusCompany(id);
		return new ResultBean(1, null).toString();
	}
}
