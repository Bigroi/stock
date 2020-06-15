package com.bigroi.stock.controller.rendering;

import com.bigroi.stock.controller.BaseRenderingController;
import com.bigroi.stock.service.ProductService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/product")
public class ProductRenderingController extends BaseRenderingController {

    private final ProductService productService;

    public ProductRenderingController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/List")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ModelAndView activeProductList() {
        return createModelAndView("products");
    }

    @RequestMapping("/TradeOffers")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ModelAndView tradeOffers(@RequestParam("id") long id) {
        var modelAndView = createModelAndView("tradeOffers");
        var product = productService.getProductById(id);
        product.setName(labelService.getLabel(product.getName(), "name", getLanguage()));
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @RequestMapping("/admin/List")
    @Secured("ROLE_ADMIN")
    public ModelAndView allProducts() {
        return createModelAndView("productsForAdmin");
    }

    @RequestMapping("/admin/Form")
    @Secured("ROLE_ADMIN")
    public ModelAndView form(@RequestParam(value = "id", defaultValue = "-1") long id) {
        return createModelAndView("productForm");
    }

}
