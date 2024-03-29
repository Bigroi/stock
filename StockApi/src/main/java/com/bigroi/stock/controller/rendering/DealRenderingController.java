package com.bigroi.stock.controller.rendering;

import com.bigroi.stock.controller.BaseRenderingController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/deal")
public class DealRenderingController extends BaseRenderingController {

    @RequestMapping("/MyDeals")
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public ModelAndView myList() {
        return createModelAndView("myDeals");
    }

    @RequestMapping("/Form")
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public ModelAndView deal(@RequestParam("id") long id) {
        var modelAndView = createModelAndView("deal");
        modelAndView.addObject("id", id);
        return modelAndView;
    }

}
