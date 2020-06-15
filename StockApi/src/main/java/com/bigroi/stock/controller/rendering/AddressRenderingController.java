package com.bigroi.stock.controller.rendering;

import com.bigroi.stock.controller.BaseRenderingController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/address")
public class AddressRenderingController extends BaseRenderingController {

    @RequestMapping("/List")
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public ModelAndView toAddress() {
        return createModelAndView("myAddresses");
    }

    @RequestMapping("/Form")
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public ModelAndView deal(@RequestParam(value = "id", defaultValue = "-1") long id) {
        var modelAndView = createModelAndView("addressForm");
        modelAndView.addObject("id", id);
        return modelAndView;
    }
}
