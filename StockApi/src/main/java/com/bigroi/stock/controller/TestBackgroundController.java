package com.bigroi.stock.controller;

import com.bigroi.stock.service.MarketService;
import com.bigroi.stock.service.MessageService;
import com.bigroi.stock.service.TradeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/background")
public class TestBackgroundController extends BaseRenderingController {

    private static final String TEST_PAGE = "testBG";
    private static final String MESSAGE = "message";

    private final TradeService tradeService;
    private final MarketService marketService;
    private final MessageService messageService;

    public TestBackgroundController(
            TradeService tradeService,
            MarketService marketService,
            MessageService messageService
    ) {
        this.tradeService = tradeService;
        this.marketService = marketService;
        this.messageService = messageService;
    }

    @RequestMapping(value = "/Index")
    public ModelAndView showPage() {
        return createModelAndView(TEST_PAGE);
    }

    @RequestMapping(value = "/ClearPreDeals")
    public ModelAndView startClearPredeal() {
        var modelAndView = createModelAndView(TEST_PAGE);

        marketService.clearPreDeal();
        modelAndView.addObject(MESSAGE, "ClearPreDeal is finished");

        return modelAndView;
    }

    @RequestMapping(value = "/Trading")
    public ModelAndView startTrade() {
        ModelAndView modelAndView = createModelAndView(TEST_PAGE);

        tradeService.newInstance().trade();
        modelAndView.addObject(MESSAGE, "Trade is finished");

        return modelAndView;
    }

    @RequestMapping(value = "/SendEmails")
    public ModelAndView startSendMail() {
        ModelAndView modelAndView = createModelAndView(TEST_PAGE);

        messageService.sendAllEmails();
        modelAndView.addObject(MESSAGE, "SendEmails is finished");

        return modelAndView;
    }

    @RequestMapping(value = "/CheckStatus")
    public ModelAndView checkStatus() {
        ModelAndView modelAndView = createModelAndView(TEST_PAGE);

        marketService.checkExpirations();
        modelAndView.addObject(MESSAGE, "CheckStatus is finished");

        return modelAndView;
    }

}
