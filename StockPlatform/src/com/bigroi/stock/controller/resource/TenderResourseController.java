package com.bigroi.stock.controller.resource;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.bean.ui.TenderForUI;
import com.bigroi.stock.controller.BaseResourseController;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.TenderService;

@Controller
@RequestMapping("/tender/json")
public class TenderResourseController extends BaseResourseController {
	
	@Autowired
	private TenderService tenderService;
	
	@RequestMapping(value = "/Form.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String form( @RequestParam(value = "id", defaultValue = "-1") long id) 
			throws ServiceException {
		Tender tender = tenderService.getTender(id, getUserCompanyId());
		if (tender.getId() == -1){
			StockUser user = (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			tender.setAddressId(user.getCompany().getAddress().getId());
		}
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
			Tender oldTender = tenderService.getTender(tender.getId(), getUserCompanyId());
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
		tenderService.merge(tender, getUserCompanyId());
		return new ResultBean(1, new TenderForUI(tender), "label.tender.save_success").toString();
	}

	@RequestMapping("/MyList.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String myList() throws ServiceException, TableException {
		List<Tender> tenders = tenderService.getMyList(getUserCompanyId());
		List<TenderForUI> tendersForUI = tenders.stream().map(TenderForUI::new).collect(Collectors.toList());
		TableResponse<TenderForUI> table = new TableResponse<>(TenderForUI.class, tendersForUI);
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
		tenderService.activate(id, getUserCompanyId());
		return new ResultBean(1, null).toString();
	}
	
	@RequestMapping("/StopTrading.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String stopTrading(@RequestParam("id") long id) throws ServiceException {
		tenderService.deactivate(id, getUserCompanyId());
		return new ResultBean(1, null).toString();
	}

	@RequestMapping("/Delete.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String delete(@RequestParam("id") long id) throws ServiceException {
		tenderService.delete(id, getUserCompanyId());
		return new ResultBean(1, null).toString();
	}

	private long getUserCompanyId(){
		StockUser user = (StockUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getCompanyId();
	}
	
	private List<String> activationCheck(long id) throws ServiceException{
		Tender tender = tenderService.getTender(id, getUserCompanyId());
		return activationCheck(tender);
	}
	
	private List<String> activationCheck(Tender tender){
		List<String> errors = new ArrayList<String>();
		
		if (tender.getProductId() < 0) {
			errors.add("label.tender.product_error");
		}
		if (tender.getMaxPrice() < 0.01) {
			errors.add("label.tender.maxPrice_error");
		}
		if (tender.getMinVolume() < 1) {
			errors.add("label.tender.minVolume_error");
		}
		if (tender.getMaxVolume() < tender.getMinVolume()) {
			errors.add("label.tender.maxVolume_error");
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		if( tender.getExparationDate().getTime() < calendar.getTimeInMillis()){
			errors.add("label.tender.expDate_error");
		}
		return errors;
	}
}
