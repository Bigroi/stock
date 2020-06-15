package com.bigroi.stock.controller.resource;

import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.service.AlertService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/alert/json", produces = "text/plain;charset=UTF-8")
public class AlertResourceController {

    private final AlertService alertService;

    public AlertResourceController(AlertService alertService) {
        this.alertService = alertService;
    }

    @RequestMapping(value = "/Get")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String getAlerts(Authentication loggedInUser) {
        var user = (StockUser) loggedInUser.getPrincipal();
        var alerts = alertService.getAlerts(user.getCompanyId());
        return new ResultBean(1, alerts, null).toString();
    }

    @RequestMapping(value = "/reset/lots")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String resetLotsAlerts(Authentication loggedInUser) {
        var user = (StockUser) loggedInUser.getPrincipal();
        alertService.resetLotsAlerts(user.getCompanyId());
        return new ResultBean(1, null).toString();
    }

    @RequestMapping(value = "/reset/tenders")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String resetTenderAlerts(Authentication loggedInUser) {
        var user = (StockUser) loggedInUser.getPrincipal();
        alertService.resetTenderAlerts(user.getCompanyId());
        return new ResultBean(1, null).toString();
    }
    @RequestMapping(value = "/reset/deals")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String resetDealsAlerts(Authentication loggedInUser) {
        var user = (StockUser) loggedInUser.getPrincipal();
        alertService.resetDealAlerts(user.getCompanyId());
        return new ResultBean(1, null).toString();
    }
}
