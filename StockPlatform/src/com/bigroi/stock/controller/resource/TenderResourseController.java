package com.bigroi.stock.controller.resource;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/tender/json")
public class TenderResourseController extends BaseResourseController {
	
	@RequestMapping(value = "/Form.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String form( @RequestParam(value = "id", defaultValue = "-1") long id) 
			throws ServiceException {
		Tender tender = ServiceFactory.getTenderService().getTender(id, getUserCompanyId());
		return new ResultBean(1, tender, null).toString();
	}

	@RequestMapping(value = "/Save.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String save(@RequestParam("json") String json) 
					throws ServiceException, ParseException {
		Tender tender = GsonUtil.getGson().fromJson(json, Tender.class);
		if (tender.getId() < 0) {
			tender.setStatus(BidStatus.INACTIVE);
		} else {
			Tender oldTender = ServiceFactory.getTenderService().getTender(tender.getId(), getUserCompanyId());
			tender.setStatus(oldTender.getStatus());
		}
		return save(tender);
	}
	
	@RequestMapping(value = "/SaveAndActivate.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String saveAndActivate(@RequestParam("json") String json) 
			throws ServiceException, ParseException {
		Tender tender = GsonUtil.getGson().fromJson(json, Tender.class);
		tender.setStatus(BidStatus.ACTIVE);
		
		return save(tender);
	}
	
	private String save(Tender tender) throws ServiceException{
		List<String> errors = activationCheck(tender);
		if (errors.size() > 0) {
			String str = errors.toString().substring(1, errors.toString().length() - 2);
			return new ResultBean(-1, str).toString();
		}
		ServiceFactory.getTenderService().merge(tender, getUserCompanyId());
		return new ResultBean(1, tender, "label.tender.save_success").toString();
	}

	@RequestMapping("/MyList.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String myList() throws ServiceException, TableException {
		List<Tender> tenders = ServiceFactory.getTenderService().getMyList(getUserCompanyId());
		TableResponse<Tender> table = new TableResponse<>(Tender.class, tenders);
		return new ResultBean(1, table, null).toString();
	}

	@RequestMapping("/StartTrading.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String startTrading(@RequestParam("id") long id) throws ServiceException {
		List<String> errors = activationCheck(id);
		if (errors.size() > 0) {
			String str = errors.toString().substring(1, errors.toString().length() - 2);
			return new ResultBean(-1, str).toString();
		}
		ServiceFactory.getTenderService().activate(id, getUserCompanyId());
		return new ResultBean(1, null).toString();
	}
	
	@RequestMapping("/StopTrading.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String stopTrading(@RequestParam("id") long id) throws ServiceException {
		ServiceFactory.getTenderService().deactivate(id, getUserCompanyId());
		return new ResultBean(1, null).toString();
	}

	@RequestMapping("/Delete.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String delete(@RequestParam("id") long id) throws ServiceException {
		ServiceFactory.getTenderService().delete(id, getUserCompanyId());
		return new ResultBean(1, null).toString();
	}

	private long getUserCompanyId(){
		StockUser user = (StockUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getCompanyId();
	}
	
	private List<String> activationCheck(long id) throws ServiceException{
		Tender tender = ServiceFactory.getTenderService().getTender(id, getUserCompanyId());
		return activationCheck(tender);
	}
	
	private List<String> activationCheck(Tender tender){
		List<String> errors = new ArrayList<String>();
		
		if (tender.getProductId() < 0) {
			errors.add("tender.product.error");
		}
		if (tender.getMaxPrice() < 0.1) {
			errors.add("tender.maxPrice.error");
		}
		if (tender.getMinVolume() < 1) {
			errors.add("tender.minVolume.error");
		}
		if (tender.getMaxVolume() < tender.getMinVolume()) {
			errors.add("tender.maxVolume.error");
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		if( tender.getExpDate().getTime() < calendar.getTimeInMillis()){
			errors.add("lot.expDate.error");
		}
		return errors;
	}
}
