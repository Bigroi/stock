package com.bigroi.stock.controller.resource;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.Product;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.Table;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;
import com.google.gson.Gson;

@Controller
@RequestMapping("/product/json/")
public class ProductResourseController extends BaseResourseController {
	
	@RequestMapping(value = "/List.spr")
	@ResponseBody
	public String list() throws TableException, ServiceException {
		List<Product> products = ServiceFactory.getProductService().getAllActiveProducts();
		Table<Product> table = new Table<>(Product.class, products)
		.exclude(3);
		return new ResultBean(1, table).toString();
	}

	@RequestMapping("/admin/List.spr")
	@ResponseBody
	public String listForAdmin() throws ServiceException, TableException {
		List<Product> allProducts = ServiceFactory.getProductService().getAllProducts();
		Table<Product> table = new Table<>(Product.class, allProducts);
		return new ResultBean(1, table).toString();
	}
	
	@RequestMapping("/admin/Form.spr")
	@ResponseBody
	public String get(@RequestParam(value = "id", defaultValue = "-1") long id) throws ServiceException {
		Product product = ServiceFactory.getProductService().getProductById(id);
		return new ResultBean(1, product).toString();
	}
	
	@RequestMapping("/admin/Save.spr")
	@ResponseBody
	public String save(@RequestParam("json") String json) throws ServiceException {
		Product product = new Gson().fromJson(json, Product.class);
		ServiceFactory.getProductService().merge(product);
		return new ResultBean(1, product).toString();
	}
	
	@RequestMapping("/admin/Delete.spr")
	@ResponseBody
	public String delete(@RequestParam("id") long id) throws ServiceException {
		ServiceFactory.getProductService().delete(id);
		return new ResultBean(1, "success").toString();

	}
	
	@RequestMapping("/lots.spr")
	@ResponseBody
	public String getLots(@RequestParam int productId) throws ServiceException, TableException{
		List<Lot> lots = ServiceFactory.getLotService().getByProduct(productId);
		Table<Lot> table = new Table<>(Lot.class, lots)
		.exclude(1).exclude(1).exclude(2).exclude(2).exclude(2);
		
		return new ResultBean(1, table).toString();
	}
	
	@RequestMapping("/tenders.spr")
	@ResponseBody
	public String getTenders(@RequestParam int productId) throws TableException, ServiceException{
		List<Tender> tenders = ServiceFactory.getTenderService().getByProduct(productId);
		Table<Tender> table = new Table<>(Tender.class, tenders)
		.exclude(1).exclude(1).exclude(2).exclude(2).exclude(2);
		
		return new ResultBean(1, table).toString();
	}
}
