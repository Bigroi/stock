package com.bigroi.stock.controller.resource;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.bean.ui.TenderForUI;
import com.bigroi.stock.bean.ui.TestTenderForUI;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.BidService;
import com.bigroi.stock.service.TenderService;

@Controller
@RequestMapping("/tender/json")
public class TenderResourseController extends BidResourceController<Tender, TenderForUI> {
	
	@Autowired
	private TenderService tenderService;
	
	@RequestMapping(value = "/Form.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String form( @RequestParam(value = "id", defaultValue = "-1") long id) {
		return bidForm(id).toString();
	}

	@RequestMapping(value = "/Save.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String save(@RequestParam("json") String json) {
		return saveBid(json).toString();
	}
	
	@RequestMapping(value = "/TestSave.spr")
	@ResponseBody
	public String testSave(@RequestParam("json") String json) {
		ResultBean resultBean = testSaveBid(json);
		if (resultBean.getResult() > 0){
			TenderForUI tenderForUI = (TenderForUI) resultBean.getData();
			resultBean = new ResultBean(1, new TestTenderForUI(tenderForUI), null);
		}
		return resultBean.toString();
	}
	
	@RequestMapping(value = "/SaveAndActivate.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String saveAndActivate(@RequestParam("json") String json) {
		return saveAndActivateBid(json).toString();
	}
	
	@RequestMapping("/MyList.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String myList() {
		return bidList().toString();
	}
	
	@RequestMapping(value = "/TestList.spr")
	@ResponseBody
	public String testLotList() {
		List<Tender> bids = tenderService.getBySessionId(getSessionId());
		List<TestTenderForUI> testTendersForUI = bids.stream().map(TestTenderForUI::new).collect(Collectors.toList());
		TableResponse<TestTenderForUI> table = new TableResponse<>(TestTenderForUI.class, testTendersForUI);
		return new ResultBean(1, table, null).toString();
	}

	@RequestMapping("/StartTrading.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String startTrading(@RequestParam("id") long id) {
		return startTradingBid(id).toString();
	}
	
	@RequestMapping("/StopTrading.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String stopTrading(@RequestParam("id") long id) {
		return stopTradingBid(id).toString();
	}

	@RequestMapping("/Delete.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String delete(@RequestParam("id") long id) {
		return deleteBid(id).toString();
	}

	@Override
	protected BidService<Tender> getBidService() {
		return tenderService;
	}

	@Override
	protected Function<Tender, TenderForUI> getObjectForUIConstructor() {
		return TenderForUI::new;
	}

	@Override
	protected Class<Tender> getBidClass() {
		return Tender.class;
	}

	@Override
	protected String getPropertyFileName() {
		return "tender";
	}
	
	protected Class<TenderForUI> getForUIClass(){
		return TenderForUI.class;
	}
}
