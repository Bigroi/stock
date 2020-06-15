package com.bigroi.stock.controller.resource;

import com.bigroi.stock.bean.ui.CompanyForUI;
import com.bigroi.stock.controller.BaseResourceController;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.CompanyService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/company/json/admin")
public class CompanyResourceController extends BaseResourceController {

    private final CompanyService companyService;

    public CompanyResourceController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RequestMapping("/List")
    @ResponseBody
    @Secured(value = {"ROLE_ADMIN"})
    public String getListCompanyAll() {
        var list = companyService.getAllCompanies();
        var listForUI = list.stream().map(CompanyForUI::new).collect(Collectors.toList());
        var tableResponse = new TableResponse<>(CompanyForUI.class, listForUI);
        return new ResultBean(1, tableResponse, null).toString();
    }

    @RequestMapping("/ChangeStatus")
    @ResponseBody
    @Secured(value = {"ROLE_ADMIN"})
    public String changeStatus(@RequestParam("id") long id) {
        companyService.changeStatusCompany(id);
        return new ResultBean(1, null).toString();
    }
}
