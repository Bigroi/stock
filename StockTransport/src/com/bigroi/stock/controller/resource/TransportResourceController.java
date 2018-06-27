package com.bigroi.stock.controller.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.ui.TransportForUI;
import com.bigroi.stock.controller.BaseResourseController;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.DealService;
import com.bigroi.stock.service.ServiceException;

@Controller
@RequestMapping(value = "/deal/json", produces = "text/plain;charset=UTF-8")
public class TransportResourceController extends BaseResourseController {
	
	@Autowired
	private  DealService dealService;
	
	@RequestMapping(value = "/MyDeals.spr")
	@ResponseBody
	public String MyDeallist() throws ServiceException, TableException{
		List<Deal> deals =  dealService.getListBySellerAndBuyerApproved();
		List<TransportForUI> transportForUI =  deals.stream().map(TransportForUI::new).collect(Collectors.toList());
		TableResponse<TransportForUI> table = new TableResponse<>(TransportForUI.class, transportForUI);
		return new ResultBean(1, table, null).toString();
	}
}
