package com.bigroi.stock.controller.resource;

import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.bean.ui.TenderForUI;
import com.bigroi.stock.bean.ui.TestTenderForUI;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.AddressService;
import com.bigroi.stock.service.BidService;
import com.bigroi.stock.service.LabelService;
import com.bigroi.stock.service.TenderService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tender/json")
public class TenderResourceController extends BidResourceController<Tender, TenderForUI> {

    private final TenderService tenderService;

    public TenderResourceController(
            AddressService addressService,
            LabelService labelService,
            TenderService tenderService
    ) {
        super(addressService, labelService);
        this.tenderService = tenderService;
    }

    @RequestMapping(value = "/Form")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String form(@RequestParam(value = "id", defaultValue = "-1") long id) {
        return bidForm(id).toString();
    }

    @RequestMapping(value = "/Save")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String save(@RequestParam("json") String json) {
        return saveBid(json).toString();
    }

    @RequestMapping(value = "/TestSave")
    @ResponseBody
    public String testSave(@RequestParam("json") String json) {
        var resultBean = testSaveBid(json);
        if (resultBean.getResult() > 0) {
            var tenderForUI = (TenderForUI) resultBean.getData();
            resultBean = new ResultBean(1, new TestTenderForUI(tenderForUI), null);
        }
        return resultBean.toString();
    }

    @RequestMapping(value = "/SaveAndActivate")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String saveAndActivate(@RequestParam("json") String json) {
        return saveAndActivateBid(json).toString();
    }

    @RequestMapping("/MyList")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String myList() {
        return bidList().toString();
    }

    @RequestMapping(value = "/TestList")
    @ResponseBody
    public String testLotList() {
        var bids = tenderService.getBySessionId(getSessionId());
        var testTendersForUI = bids.stream()
                .map(t -> {
                    t.getProduct().setName(labelService.getLabel(t.getProduct().getName(), "name", getLanguage()));
                    return new TestTenderForUI(t);
                })
                .collect(Collectors.toList());
        var table = new TableResponse<>(TestTenderForUI.class, testTendersForUI);
        return new ResultBean(1, table, null).toString();
    }

    @RequestMapping("/StartTrading")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String startTrading(@RequestParam("id") long id) {
        return startTradingBid(id).toString();
    }

    @RequestMapping("/StopTrading")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String stopTrading(@RequestParam("id") long id) {
        return stopTradingBid(id).toString();
    }

    @RequestMapping("/Delete")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
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

    protected Class<TenderForUI> getForUIClass() {
        return TenderForUI.class;
    }

    @Override
    protected boolean isEmptyCategoryAllowed() {
        return true;
    }
}
