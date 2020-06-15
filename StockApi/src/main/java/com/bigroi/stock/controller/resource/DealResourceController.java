package com.bigroi.stock.controller.resource;

import com.bigroi.stock.bean.common.DealStatus;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.bean.db.UserComment;
import com.bigroi.stock.bean.ui.DealForUI;
import com.bigroi.stock.bean.ui.TestDealForUI;
import com.bigroi.stock.controller.BaseResourceController;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.DealService;
import com.bigroi.stock.service.LabelService;
import com.bigroi.stock.service.TradeService;
import com.bigroi.stock.service.UserCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Base64;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/deal/json", produces = "text/plain;charset=UTF-8")
public class DealResourceController extends BaseResourceController {

    private static final String NOT_AUTORISED_ERROR_LABEL = "label.deal.not_authorized";
    private static final String TRANSPORT_SUCCESS_LABEL = "label.deal.transport";
    private static final String REJECT_SUCCESS_LABEL = "label.deal.rejected";
    private static final String APPROVE_SUCCESS_LABEL = "label.deal.approved";

    private final TradeService tradeService;
    private final DealService dealService;
    private final LabelService labelService;
    private final UserCommentService userCommentService;

    public DealResourceController(
            TradeService tradeService,
            DealService dealService,
            LabelService labelService,
            UserCommentService userCommentService
    ) {
        this.tradeService = tradeService;
        this.dealService = dealService;
        this.labelService = labelService;
        this.userCommentService = userCommentService;
    }

    @RequestMapping(value = "/Form", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String form(@RequestParam("id") long id, Authentication loggedInUser) {
        var user = (StockUser) loggedInUser.getPrincipal();
        var deal = dealService.getById(id, user.getCompanyId());
        var partnerMark = getPartnerMark(user.getCompanyId(), deal);
        if (user.getCompanyId() != deal.getBuyerCompanyId() &&
                user.getCompanyId() != deal.getSellerCompanyId()) {
            return new ResultBean(-1, NOT_AUTORISED_ERROR_LABEL).toString();
        } else {
            deal.setProductName(labelService.getLabel(deal.getProductName(), "name", getLanguage()));
            return new ResultBean(1, translateDeal(new DealForUI(deal, user.getCompanyId(), partnerMark)), "").toString();
        }
    }

    private int getPartnerMark(long companyId, Deal deal) {
        var partnerId = companyId == deal.getBuyerCompanyId()
				? deal.getSellerCompanyId()
				: deal.getBuyerCompanyId();
        var comments = userCommentService.getComments(partnerId);
        if (comments.isEmpty()) {
            return 5;
        } else {
            return (int) comments.stream().mapToInt(UserComment::getMark).average().getAsDouble();
        }
    }

    private DealForUI translateDeal(DealForUI deal) {
        deal.setStatus(labelService.getLabel("deal", deal.getStatus(), getLanguage()));
        return deal;
    }

    @RequestMapping(value = "/Picture", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<byte[]> picture(@RequestParam("dealId") long dealId, Authentication loggedInUser) {
        var userBean = (StockUser) loggedInUser.getPrincipal();
        var picture = dealService.getById(dealId, userBean.getCompanyId()).getSellerFoto();
        byte[] bytes = Base64.getDecoder().decode(picture.substring(picture.indexOf(',') + 1).getBytes());

        return new ResponseEntity<>(bytes, HttpStatus.OK);
    }

    @RequestMapping(value = "/MyDeals", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String myDealList(Authentication loggedInUser) {
        var userBean = (StockUser) loggedInUser.getPrincipal();
        var deals = dealService.getByUserId(userBean.getCompanyId());
        var dealsForUI = deals.stream()
                .map(d -> {
                    d.setProductName(labelService.getLabel(d.getProductName(), "name", getLanguage()));
                    return new DealForUI(d, userBean.getCompanyId());
                })
                .map(this::translateDeal)
                .collect(Collectors.toList());
        var table = new TableResponse<>(DealForUI.class, dealsForUI);
        return new ResultBean(1, table, "").toString();
    }

    @RequestMapping(value = "/TestDeals")
    @ResponseBody
    public String testDealList(Authentication loggedInUser) {
        var table = new TableResponse<>(TestDealForUI.class, new ArrayList<>());
        return new ResultBean(1, table, null).toString();
    }

    @RequestMapping(value = "/TestClean")
    @ResponseBody
    public String testClean(HttpSession session) {
        session.invalidate();
        return new ResultBean(1, null).toString();
    }

    @RequestMapping(value = "/TestTrade")
    @ResponseBody
    public String testTrade(Authentication loggedInUser) {
        var deals = tradeService.newInstance().testTrade(getSessionId());
        var dealsForUI = deals.stream()
                .map(d -> {
                    d.setProductName(labelService.getLabel(d.getProductName(), "name", getLanguage()));
                    return new TestDealForUI(d);
                })
                .collect(Collectors.toList());
        var table = new TableResponse<>(TestDealForUI.class, dealsForUI);
        return new ResultBean(1, table, "").toString();
    }

    @RequestMapping(value = "/Approve")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String approve(@RequestParam("json") String json, Authentication loggedInUser) {
        var userBean = (StockUser) loggedInUser.getPrincipal();
        var deal = GsonUtil.getGson().fromJson(json, DealForUI.class);

        var newStatus = dealService.approve(deal.getId(), userBean.getCompanyId());
        if (newStatus != null) {
            deal.setStatus(newStatus.toString());
            return new ResultBean(2, translateDeal(deal), APPROVE_SUCCESS_LABEL).toString();
        } else {
            return new ResultBean(-1, NOT_AUTORISED_ERROR_LABEL).toString();
        }
    }

    @RequestMapping(value = "/Reject")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String reject(@RequestParam("json") String json, Authentication loggedInUser) {
        var userBean = (StockUser) loggedInUser.getPrincipal();
        var deal = GsonUtil.getGson().fromJson(json, DealForUI.class);

        deal.setStatus(DealStatus.REJECTED.toString());
        if (dealService.reject(deal.getId(), userBean.getCompanyId())) {
            return new ResultBean(2, translateDeal(deal), REJECT_SUCCESS_LABEL).toString();
        } else {
            return new ResultBean(-1, NOT_AUTORISED_ERROR_LABEL).toString();
        }
    }

    @RequestMapping(value = "/Transport")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String transport(@RequestParam("json") String json, Authentication loggedInUser) {
        var userBean = (StockUser) loggedInUser.getPrincipal();
        var deal = GsonUtil.getGson().fromJson(json, DealForUI.class);

        deal.setStatus(DealStatus.TRANSPORT.toString());
        if (dealService.transport(deal.getId(), userBean.getCompanyId())) {
            return new ResultBean(2, translateDeal(deal), TRANSPORT_SUCCESS_LABEL).toString();
        } else {
            return new ResultBean(-1, NOT_AUTORISED_ERROR_LABEL).toString();
        }
    }
}
