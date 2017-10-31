package com.bigroi.stock.controller.resource;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/company/json/admin")
public class CompanyResourceController extends BaseResourseController {

	@RequestMapping("/List.spr")
	public @ResponseBody String getListCompanyAll() throws ServiceException {
		List<Company> list = ServiceFactory.getCompanyService().getAllCompanies();
		return new ResultBean(1, list).toString();
	}

	@RequestMapping("/ChangeStatus.spr")
	public @ResponseBody String changeStatus(@RequestParam("id") long id) throws ServiceException {
		ServiceFactory.getCompanyService().changeStatusCompany(id);
		return new ResultBean(1, "company.status.change.success").toString();
	}
}
