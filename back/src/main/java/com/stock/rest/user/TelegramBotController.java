package com.stock.rest.user;

import com.stock.entity.business.StockTelegramBotRecord;
import com.stock.service.TelegramBotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("api/user/telegramBot/")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://test.yourtrader.com",
        "https://yourtrader.com",
        "https://www.yourtrader.com"
})
public class TelegramBotController {

    private final TelegramBotService telegramBotService;


    public  TelegramBotController(TelegramBotService telegramBotService) { this.telegramBotService = telegramBotService; }

    @PostMapping("/binding")
    public ResponseEntity<?> bindingAccountWithBot(@RequestBody StockTelegramBotRecord stockTelegramBot) {
        if (!stockTelegramBot.getTelegramUserName().equals("")) {
            var result = telegramBotService.bindingAccToTelegramBot(stockTelegramBot);
            if (result) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
