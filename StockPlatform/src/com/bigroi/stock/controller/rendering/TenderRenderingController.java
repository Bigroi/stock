package com.bigroi.stock.controller.rendering;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/tender")
public class TenderRenderingController {
	
	@RequestMapping("/Form.spr")
	public ModelAndView form(
			@RequestParam(value = "id", defaultValue = "-1") long id, 
			HttpSession session) throws ServiceException {
		User user = (User)session.getAttribute("user");
		Tender tender = ServiceFactory.getTenderService().getTender(id, user.getCompanyId());
		ModelAndView modelAndView = new ModelAndView("tenderForm", "tender", tender);
		modelAndView.addObject("listOfProducts", 
				ServiceFactory.getProductService().getAllActiveProducts());
		return modelAndView;
		
	}

	@RequestMapping("/Save.spr")
	public ModelAndView save(@RequestParam("id") long id, 
			@RequestParam("description") String description,			
			@RequestParam("productId") long productId,
			@RequestParam("maxPrice") double maxPrice,
			@RequestParam("customerId") long customerId,
			@RequestParam("expDate") String expDateStr,
			@RequestParam("volumeOfTender") int volumeOfTender,
			@RequestParam("status") Status status,
			HttpSession session) throws ParseException, ServiceException {
		
		Tender tender = new Tender();
		tender.setId(id);
		tender.setDescription(description);
		tender.setProductId(productId);
		tender.setMaxPrice(maxPrice);
		tender.setCustomerId(customerId);
		tender.setDateStr(expDateStr);
		tender.setVolumeOfTender(volumeOfTender);
		tender.setStatus(status);
		
		ServiceFactory.getTenderService().merge(tender);

		return myList(session);
	}
	
	@RequestMapping("/MyList.spr")
	public ModelAndView myList(HttpSession session) throws  ServiceException {
		User user = (User)session.getAttribute("user");
		List<Tender> tenders = ServiceFactory.getTenderService().getMyList(user.getCompanyId());
		return new ModelAndView("myTenderList", "listOfTenders", tenders);
	}
	
	@RequestMapping("/StartTrading.spr")
	public ModelAndView startTrading(@RequestParam("id") long id, HttpSession session) throws  ServiceException {
		ServiceFactory.getTenderService().startTrading(id);
		return myList(session);
	}
	
	@RequestMapping("/Cancel.spr")
	public ModelAndView cancel(@RequestParam("id") long id, HttpSession session) throws  ServiceException {
		ServiceFactory.getTenderService().cancel(id);
		return myList(session);
	}
}
