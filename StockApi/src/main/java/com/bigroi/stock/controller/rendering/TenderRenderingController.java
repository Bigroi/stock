package com.bigroi.stock.controller.rendering;

import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.controller.BaseRenderingController;
import com.bigroi.stock.service.AddressService;
import com.bigroi.stock.service.ProductService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/tender")
public class TenderRenderingController extends BaseRenderingController {

    private final ProductService productService;
    private final AddressService addressService;

    public TenderRenderingController(ProductService productService, AddressService addressService) {
        this.productService = productService;
        this.addressService = addressService;
    }

    @RequestMapping("/Form")
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public ModelAndView form(
            @RequestParam(value = "id", defaultValue = "-1") long id,
            Authentication loggedInUser
    ) {
        ModelAndView modelAndView = createModelAndView("tenderForm");
        List<Product> products = productService.getAllActiveProducts();
        products.forEach(product -> product.setName(labelService.getLabel(product.getName(), "name", getLanguage())));

        List<CompanyAddress> addressList = addressService.getCompanyAddresses(((StockUser) loggedInUser.getPrincipal()).getCompanyId());

        modelAndView.addObject("newTender", id < 0);
        modelAndView.addObject("listOfProducts", products);
        modelAndView.addObject("listOfAddresses", addressList);
        return modelAndView;
    }

    @RequestMapping("/TestForm")
    public ModelAndView testForm() {
        ModelAndView modelAndView = createModelAndView("testTenderForm");
        List<Product> products = productService.getAllActiveProducts();
        products.forEach(product -> product.setName(labelService.getLabel(product.getName(), "name", getLanguage())));

        List<CompanyAddress> addressList = addressService.getCompanyAddresses(0);

        modelAndView.addObject("listOfProducts", products);
        modelAndView.addObject("listOfAddresses", addressList);
        return modelAndView;
    }

    @RequestMapping("/MyTenders")
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public ModelAndView myList() {
        return createModelAndView("myTenders");
    }
}
