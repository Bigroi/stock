package com.bigroi.stock.controller.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.db.ProductCategory;
import com.bigroi.stock.bean.ui.ProductCategoryForUI;
import com.bigroi.stock.controller.BaseResourseController;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.LabelService;
import com.bigroi.stock.service.ProductCategoryService;

@Controller
@RequestMapping(value = "/category/json")
public class ProductCategoryResourceController extends BaseResourseController {
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Autowired
	private LabelService labelService;
	
	@RequestMapping(value = "/Get.spr")
	@ResponseBody
	@Secured("ROLE_ADMIN")
	public String getCategory(@RequestParam("id") long id, @RequestParam("productId") long productId){
		ProductCategory category = productCategoryService.getById(id, productId);
		return new ResultBean(1, category, null).toString();
	}
	
	@RequestMapping(value = "/List.spr")
	@ResponseBody
	@Secured("ROLE_ADMIN")
	public String getCategories(@RequestParam("productId") long productId){
		List<ProductCategory> categoris = productCategoryService.getByProductId(productId);
		categoris.add(0, new ProductCategory(-1, labelService.getLabel("lot", "list", getLanguage())));
		List<ProductCategoryForUI> categorisForUI = categoris.stream()
				.map(ProductCategoryForUI::new)
				.collect(Collectors.toList());
		TableResponse<ProductCategoryForUI> table = new TableResponse<>(ProductCategoryForUI.class, categorisForUI);
		return new ResultBean(1, table, null).toString();
	}
	
	@RequestMapping(value = "/FormList.spr")
	@ResponseBody
	@Secured("ROLE_USER")
	public String getFormCategories(@RequestParam("productId") long productId){
		List<ProductCategory> categoris = productCategoryService.getActiveByProductId(productId);
		categoris.add(0, new ProductCategory(-1, labelService.getLabel("lot", "list", getLanguage())));
		return new ResultBean(1, categoris, null).toString();
	}
	
	@RequestMapping(value = "/Save.spr")
	@ResponseBody
	@Secured(value = "ROLE_ADMIN")
	public String save(@RequestParam("json") String json){
		ProductCategory category = GsonUtil.getGson().fromJson(json, ProductCategory.class);
		
		productCategoryService.merge(category);
		return new ResultBean(1, new ProductCategoryForUI(category), null).toString();
	}
	
	@RequestMapping(value = "/Delete.spr")
	@ResponseBody
	@Secured(value = "ROLE_ADMIN")
	public String delete(@RequestParam("json") String json){
		ProductCategory category = GsonUtil.getGson().fromJson(json, ProductCategory.class);
		
		productCategoryService.delete(category);
		return new ResultBean(1, new ProductCategoryForUI(category), null).toString();
	}
}
