package com.bigroi.stock.controller.resource;

import com.bigroi.stock.bean.db.Label;
import com.bigroi.stock.bean.ui.LabelForUI;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.LabelService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/label/json/admin")
public class LabelResourseController {

    private final LabelService labelService;

    public LabelResourseController(LabelService labelService) {
        this.labelService = labelService;
    }

    @RequestMapping("/List")
    @ResponseBody
    @Secured(value = {"ROLE_ADMIN"})
    public String getAllLabels() {
        var list = labelService.getAllLabel();
        var listForUI = list.stream().map(LabelForUI::new).collect(Collectors.toList());
        var tableResponse = new TableResponse<>(LabelForUI.class, listForUI);
        return new ResultBean(1, tableResponse, null).toString();
    }

    @RequestMapping(value = "/Form", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    @Secured(value = {"ROLE_ADMIN"})
    public String form(@RequestParam(value = "id", defaultValue = "-1") long id, Authentication loggedInUser) {
        if (loggedInUser.isAuthenticated()) {
            var label = labelService.getLabelById(id);
            return new ResultBean(1, new LabelForUI(label), null).toString();
        }
        return null;
    }

    @RequestMapping("/Save")
    @ResponseBody
    @Secured(value = {"ROLE_ADMIN"})
    public String save(@RequestParam("json") String json) {
        var label = GsonUtil.getGson().fromJson(json, Label.class);
        labelService.merge(label);
        return new ResultBean(1, new LabelForUI(label), null).toString();
    }

    @RequestMapping("/Delete")
    @ResponseBody
    @Secured(value = {"ROLE_ADMIN"})
    public String delete(@RequestParam("id") long id) {
        labelService.delete(id);
        return new ResultBean(1, null).toString();
    }
}
