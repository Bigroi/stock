package com.bigroi.stock.controller.resource;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/company/json/admin")
public class CompanyResourceController extends BaseResourseController {

	@RequestMapping("/List.spr")
	@ResponseBody
	@Secured(value = {"ROLE_ADMIN"})
	public String getListCompanyAll() throws ServiceException, TableException {
		List<Company> list = ServiceFactory.getCompanyService().getAllCompanies();
		TableResponse<Company> tableResponse = new TableResponse<>(Company.class, list);
		return new ResultBean(1, tableResponse, "").toString();
	}

	@RequestMapping("/ChangeStatus.spr")
	@ResponseBody
	@Secured(value = {"ROLE_ADMIN"})
	public String changeStatus(@RequestParam("id") long id) throws ServiceException {
		ServiceFactory.getCompanyService().changeStatusCompany(id);
		return new ResultBean(1, "company.status.change.success").toString();
	}
}
