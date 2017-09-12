package com.bigroi.stock.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.tribes.group.Response;
import org.apache.jasper.tagplugins.jstl.core.Redirect;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

@Controller
public class CompanyResourceController {

	

	@RequestMapping("/CompanyList.spr")
	public ModelAndView getListCompanyAll(HttpSession session) throws DaoException {
		User user = (User) session.getAttribute("user");
		List<Company> list = null;
		if(user != null){
			 list = DaoFactory.getCompanyDao().getAllCompany(); 
		}else{
			return new ModelAndView("login");
		}
		return new ModelAndView("companyList", "listOfCompany", list);
	}
	
	@RequestMapping("/ChangeStatus.spr")
	public ModelAndView changeStatus(@RequestParam("id") long id, HttpSession session) throws DaoException {
		session.getAttribute("user");
		ModelMap model = new ModelMap();
		Company company;
		if (id > 0) {
			company = DaoFactory.getCompanyDao().getById(id);
			model.put("id", company);
			model.put("listOfStatus", DaoFactory.getCompanyDao().getAllCompany());
		}
		return new ModelAndView("companyForm", model);
	}
	
	@RequestMapping("/SaveStatus.spr")
	public Company saveStatus(@RequestParam("id") long id, @RequestParam("status") CompanyStatus status ){
		Company company = new Company();
		company.setStatus(status);
		if(id > 0){
			company.setId(id);
			//DaoFactory.getCompanyDao().updateStatus(company);
		}
	
		return company;
		
		
		
	}
}
