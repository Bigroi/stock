package com.bigroi.stock.controller.resource;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.ChartTrace;
import com.bigroi.stock.bean.Product;
import com.bigroi.stock.bean.TradeOffer;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/product/json/")
public class ProductResourseController extends BaseResourseController {
	
	@RequestMapping(value = "/List.spr")
	@ResponseBody
	public String list() throws TableException, ServiceException {
		List<Product> products = ServiceFactory.getProductService().getAllActiveProducts();
		TableResponse<Product> table = new TableResponse<>(Product.class, products).removeColumn("archive");
		products.stream().forEach(p -> p.setEdit("NNY"));
		return new ResultBean(1, table, null).toString();
	}

	@RequestMapping("/admin/List.spr")
	@ResponseBody
	@Secured(value = {"ROLE_ADMIN"})
	public String listForAdmin() throws ServiceException, TableException {
		List<Product> allProducts = ServiceFactory.getProductService().getAllProducts();
		TableResponse<Product> table = new TableResponse<>(Product.class, allProducts);
		return new ResultBean(1, table, null).toString();
	}
	
	@RequestMapping("/admin/Form.spr")
	@ResponseBody
	@Secured(value = {"ROLE_ADMIN"})
	public String get(@RequestParam(value = "id", defaultValue = "-1") long id) throws ServiceException {
		Product product = ServiceFactory.getProductService().getProductById(id);
		return new ResultBean(1, product, null).toString();
	}
	
	@RequestMapping("/admin/Save.spr")
	@ResponseBody
	@Secured(value = {"ROLE_ADMIN"})
	public String save(@RequestParam("json") String json) throws ServiceException {
		Product product = GsonUtil.getGson().fromJson(json, Product.class);
		if (product.getId() < 0){
			product.setArchive("N");
		}
		ServiceFactory.getProductService().merge(product);
		return new ResultBean(1, product, "label.product.save_success").toString();
	}
	
	@RequestMapping("/admin/Delete.spr")
	@ResponseBody
	@Secured(value = {"ROLE_ADMIN"})
	public String delete(@RequestParam("json") String json) throws ServiceException {
		Product product = GsonUtil.getGson().fromJson(json, Product.class);
		ServiceFactory.getProductService().delete(product.getId());
		return new ResultBean(1, null).toString();
	}
	
	@RequestMapping("/TradeOffers.spr")
	@ResponseBody
	public String getTradeOffers(@RequestParam int productId) throws ServiceException, TableException{
		List<TradeOffer> tradeOffers = ServiceFactory.getProductService().getTradeOffers(productId);
		TableResponse<TradeOffer> table = new TableResponse<>(TradeOffer.class, tradeOffers);
		return new ResultBean(1, table, null).toString();
	}
	
	@RequestMapping("/TradeOfferChart.spr")
	@ResponseBody
	public String getTradeOfferChart(@RequestParam long productId) throws ServiceException, TableException{
		List<ChartTrace> chartTraces = ServiceFactory.getProductService().getChartTraces(productId);
		return new ResultBean(1, chartTraces, null).toString();
	}
	
}
