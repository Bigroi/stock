package com.bigroi.stock.controller.resource;

import com.bigroi.stock.controller.BaseResourceController;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.service.LabelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/l10n/json")
public class LocalizationResosurceController extends BaseResourceController {

    private final LabelService labelService;

    public LocalizationResosurceController(LabelService labelService) {
        this.labelService = labelService;
    }

    @RequestMapping(value = "/Labels")
    @ResponseBody
    public String labels() {
        return new ResultBean(1, labelService.getAllLabel(getLanguage()), "").toString();
    }

}
