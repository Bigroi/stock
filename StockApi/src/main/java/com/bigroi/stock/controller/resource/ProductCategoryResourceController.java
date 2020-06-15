package com.bigroi.stock.controller.resource;

import com.bigroi.stock.bean.db.ProductCategory;
import com.bigroi.stock.bean.ui.ProductCategoryForUI;
import com.bigroi.stock.controller.BaseResourceController;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.LabelService;
import com.bigroi.stock.service.ProductCategoryService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/category/json")
public class ProductCategoryResourceController extends BaseResourceController {

    private final ProductCategoryService productCategoryService;
    private final LabelService labelService;

    public ProductCategoryResourceController(ProductCategoryService productCategoryService, LabelService labelService) {
        this.productCategoryService = productCategoryService;
        this.labelService = labelService;
    }

    @RequestMapping(value = "/Get")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String getCategory(@RequestParam("id") long id, @RequestParam("productId") long productId) {
        var category = productCategoryService.getById(id, productId);
        return new ResultBean(1, category, null).toString();
    }

    @RequestMapping(value = "/List")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String getCategories(@RequestParam("productId") long productId) {
        var categories = productCategoryService.getByProductId(productId);
        categories.add(0, new ProductCategory(-1, labelService.getLabel("lot", "list", getLanguage())));
        var categoriesForUI = categories.stream()
                .map(ProductCategoryForUI::new)
                .collect(Collectors.toList());
        var table = new TableResponse<>(ProductCategoryForUI.class, categoriesForUI);
        return new ResultBean(1, table, null).toString();
    }

    @RequestMapping(value = "/FormList")
    @ResponseBody
    public String getFormCategories(@RequestParam("productId") long productId) {
        var categories = productCategoryService.getActiveByProductId(productId);
        categories.forEach(category -> category.setCategoryName(labelService.getLabel(category.getCategoryName(), "name", getLanguage())));
        categories.add(0, new ProductCategory(-1, labelService.getLabel("lot", "list", getLanguage())));
        return new ResultBean(1, categories, null).toString();
    }

    @RequestMapping(value = "/Save")
    @ResponseBody
    @Secured(value = "ROLE_ADMIN")
    public String save(@RequestParam("json") String json) {
        var category = GsonUtil.getGson().fromJson(json, ProductCategory.class);
        productCategoryService.merge(category);
        return new ResultBean(1, new ProductCategoryForUI(category), null).toString();
    }

    @RequestMapping(value = "/Delete")
    @ResponseBody
    @Secured(value = "ROLE_ADMIN")
    public String delete(@RequestParam("json") String json) {
        var category = GsonUtil.getGson().fromJson(json, ProductCategory.class);
        productCategoryService.delete(category);
        return new ResultBean(1, new ProductCategoryForUI(category), null).toString();
    }
}
