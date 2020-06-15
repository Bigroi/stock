package com.bigroi.stock.controller.rendering;

import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.controller.BaseRenderingController;
import com.bigroi.stock.service.AddressService;
import com.bigroi.stock.service.LotService;
import com.bigroi.stock.service.ProductService;
import com.bigroi.stock.util.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/lot")
public class LotRenderingController extends BaseRenderingController {

    private final ProductService productService;
    private final AddressService addressService;
    private final LotService lotService;

    public LotRenderingController(ProductService productService, AddressService addressService, LotService lotService) {
        this.productService = productService;
        this.addressService = addressService;
        this.lotService = lotService;
    }

    @RequestMapping("/Form")
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public ModelAndView form(
            @RequestParam(value = "id", defaultValue = "-1") long id,
            Authentication loggedInUser
	) {
        var user = (StockUser) loggedInUser.getPrincipal();

        var modelAndView = createModelAndView("lotForm");
        var productList = productService.getAllActiveProducts();
        productList.forEach(product -> product.setName(labelService.getLabel(product.getName(), "name", getLanguage())));

        var addressList = addressService.getCompanyAddresses(user.getCompanyId());

        modelAndView.addObject("listOfProducts", productList);
        modelAndView.addObject("listOfAddresses", addressList);
        modelAndView.addObject("newLot", id < 1);
        modelAndView.addObject("id", id);
        modelAndView.addObject("foto", !StringUtils.isEmpty(lotService.getById(id, user.getCompanyId()).getFoto()));
        return modelAndView;
    }

    @RequestMapping("/TestForm")
    public ModelAndView testForm() {
        var modelAndView = createModelAndView("testLotForm");
        var productList = productService.getAllActiveProducts();
        productList.forEach(product -> product.setName(labelService.getLabel(product.getName(), "name", getLanguage())));

        var addressList = addressService.getCompanyAddresses(0);

        modelAndView.addObject("listOfProducts", productList);
        modelAndView.addObject("listOfAddresses", addressList);
        return modelAndView;
    }

    @RequestMapping("/MyLots")
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public ModelAndView myList() {
        return createModelAndView("myLots");
    }

}
