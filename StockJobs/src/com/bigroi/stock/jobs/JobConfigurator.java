package com.bigroi.stock.jobs;

import com.bigroi.stock.service.MarketService;
import com.bigroi.stock.service.MessageService;
import com.bigroi.stock.service.TradeService;
import com.bigroi.stock.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class JobConfigurator {

    private static final Logger logger = Logger.getLogger(JobConfigurator.class);

    private final TradeService tradeService;
    private final MessageService messageService;
    private final MarketService marketService;
    private final UserService userService;

    public JobConfigurator(
            TradeService tradeService,
            MessageService messageService,
            MarketService marketService,
            UserService userService
    ) {
        this.tradeService = tradeService;
        this.messageService = messageService;
        this.marketService = marketService;
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 0 */2 * * ")
    public void deleteGenerateKeys() {
        try {
            userService.deleteGenerateKeys();
        } catch (Exception e) {
            logger.warn("unsucsessful deleting keys", e);
        }
    }

    @Scheduled(cron = "0 0 9,21 * * *")
    public void trade() {
        try {
            marketService.clearPreDeal();
            marketService.checkExpirations();
            tradeService.newInstance().trade();
            marketService.sendConfirmationMessages();
        } catch (Exception e) {
            logger.warn("unsucsessful trading", e);
        }
    }

    @Scheduled(cron = "0 * * * * *")
    public void sendAllEmails() {
        try {
            messageService.sendAllEmails();
        } catch (Exception e) {
            logger.warn("unseccessfull mailing", e);
        }

    }
}
