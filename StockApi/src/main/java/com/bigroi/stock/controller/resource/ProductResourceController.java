package com.bigroi.stock.controller.resource;

import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.ui.ProductForUI;
import com.bigroi.stock.bean.ui.TradeOffer;
import com.bigroi.stock.controller.BaseResourceController;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.LabelService;
import com.bigroi.stock.service.ProductService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/product/json/")
public class ProductResourceController extends BaseResourceController {

    private static final String SAVE_SUCCESS_LABEL = "label.product.save_success";
    private static final String DELETE_SUCCESS_LABEL = "label.product.delete_success";

    private final ProductService productService;
    private final LabelService labelService;

    public ProductResourceController(ProductService productService, LabelService labelService) {
        this.productService = productService;
        this.labelService = labelService;
    }

    @RequestMapping(value = "/List")
    @ResponseBody
    public String list() {
        var productsForUI = productService.getAllActiveProductsForUI();
        productsForUI.forEach(p -> p.setName(labelService.getLabel(p.getName(), "name", getLanguage())));
        return new ResultBean(1, productsForUI, null).toString();
    }

    @RequestMapping("/admin/List")
    @ResponseBody
    @Secured(value = {"ROLE_ADMIN"})
    public String listForAdmin() {
        var products = productService.getAllProducts();
        var productsForUI = products.stream().
                map(ProductForUI::new).collect(Collectors.toList());
        productsForUI.stream().forEach(p -> {
            p.setEdit("YNN");
            p.setName(labelService.getLabel(p.getName(), "name", getLanguage()));
        });
        var table = new TableResponse<>(ProductForUI.class, productsForUI);
        return new ResultBean(1, table, null).toString();
    }

    @RequestMapping("/admin/Form")
    @ResponseBody
    @Secured(value = {"ROLE_ADMIN"})
    public String get(@RequestParam(value = "id", defaultValue = "-1") long id) {
        var product = productService.getProductById(id);
        return new ResultBean(1, product, null).toString();
    }

    @RequestMapping("/admin/Save")
    @ResponseBody
    @Secured(value = {"ROLE_ADMIN"})
    public String save(@RequestParam("json") String json) {
        var product = GsonUtil.getGson().fromJson(json, Product.class);
        product.setRemoved("N");
        productService.merge(product);
        return new ResultBean(1, new ProductForUI(product), SAVE_SUCCESS_LABEL).toString();
    }

    @RequestMapping("/admin/Delete")
    @ResponseBody
    @Secured(value = {"ROLE_ADMIN"})
    public String delete(@RequestParam("json") String json) {
        var product = GsonUtil.getGson().fromJson(json, Product.class);
        productService.delete(product.getId());
        product.setRemoved("Y");
        return new ResultBean(1, new ProductForUI(product), DELETE_SUCCESS_LABEL).toString();
    }

    @RequestMapping("/admin/Categories")
    @ResponseBody
    @Secured(value = {"ROLE_ADMIN"})
    public String categories(@RequestParam("json") String json) {
        var product = GsonUtil.getGson().fromJson(json, Product.class);
        return new ResultBean(0, "/category/List?productId=" + product.getId(), null).toString();
    }

    @RequestMapping("/TradeOffers")
    @ResponseBody
    public String getTradeOffers(@RequestParam int productId) {
        var tradeOffers = productService.getTradeOffers(productId);
        var table = new TableResponse<>(TradeOffer.class, tradeOffers);
        return new ResultBean(1, table, null).toString();
    }

}
