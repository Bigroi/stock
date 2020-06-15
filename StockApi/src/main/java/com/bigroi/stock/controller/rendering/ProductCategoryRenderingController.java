package com.bigroi.stock.controller.rendering;

import com.bigroi.stock.controller.BaseRenderingController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/category")
public class ProductCategoryRenderingController extends BaseRenderingController {

    @RequestMapping("/List")
    @Secured(value = "ROLE_ADMIN")
    public ModelAndView list(@RequestParam(value = "productId", defaultValue = "-1") long productId) {
        var modelAndView = createModelAndView("categories");
        modelAndView.addObject("productId", productId);
        return modelAndView;
    }

    @RequestMapping("/Form")
    @Secured(value = "ROLE_ADMIN")
    public ModelAndView form() {
        return createModelAndView("categoryForm");
    }
}
