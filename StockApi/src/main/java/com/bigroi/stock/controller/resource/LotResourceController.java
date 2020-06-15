package com.bigroi.stock.controller.resource;

import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.ui.LotForUI;
import com.bigroi.stock.bean.ui.TestLotForUI;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.AddressService;
import com.bigroi.stock.service.BidService;
import com.bigroi.stock.service.LabelService;
import com.bigroi.stock.service.LotService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Base64;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/lot/json")
public class LotResourceController extends BidResourceController<Lot, LotForUI> {

    private final LotService lotService;

    public LotResourceController(AddressService addressService, LabelService labelService, LotService lotService) {
        super(addressService, labelService);
        this.lotService = lotService;
    }

    @RequestMapping(value = "/Form")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String form(@RequestParam(value = "id", defaultValue = "-1") long id) {
        return bidForm(id).toString();
    }

    @RequestMapping(value = "/MyList")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String lotList() {
        return bidList().toString();
    }

    @RequestMapping(value = "/TestList")
    @ResponseBody
    public String testLotList() {
        var bids = lotService.getBySessionId(getSessionId());
        var testLotsForUI = bids.stream()
                .map(l -> {
                    l.getProduct().setName(labelService.getLabel(l.getProduct().getName(), "name", getLanguage()));
                    return new TestLotForUI(l);
                })
                .collect(Collectors.toList());
        var table = new TableResponse<>(TestLotForUI.class, testLotsForUI);
        return new ResultBean(1, table, null).toString();
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
            var lotForUI = (LotForUI) resultBean.getData();
            resultBean = new ResultBean(1, new TestLotForUI(lotForUI), null);
        }
        return resultBean.toString();
    }

    @RequestMapping(value = "/SaveAndActivate")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String saveAndActivate(@RequestParam("json") String json) {
        return saveAndActivateBid(json).toString();
    }

    @RequestMapping(value = "/StartTrading")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String startTrading(@RequestParam("id") long id) {
        return startTradingBid(id).toString();
    }

    @RequestMapping(value = "/StopTrading")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String stopTrading(@RequestParam("id") long id) {
        return stopTradingBid(id).toString();
    }

    @RequestMapping(value = "/Delete")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String delete(@RequestParam("id") long id) {
        return deleteBid(id).toString();
    }

    @RequestMapping(value = "/Foto")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public byte[] photo(@RequestParam("id") long id) {
        var photo = lotService.getById(id, getUserCompanyId()).getFoto();

        return Base64.getDecoder().decode(photo.substring(photo.indexOf(',') + 1).getBytes());
    }

    @Override
    protected BidService<Lot> getBidService() {
        return lotService;
    }

    @Override
    protected Function<Lot, LotForUI> getObjectForUIConstructor() {
        return LotForUI::new;
    }

    @Override
    protected Class<Lot> getBidClass() {
        return Lot.class;
    }

    @Override
    protected String getPropertyFileName() {
        return "lot";
    }

    protected Class<LotForUI> getForUIClass() {
        return LotForUI.class;
    }

    @Override
    protected boolean isEmptyCategoryAllowed() {
        return false;
    }
}
