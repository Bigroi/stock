package com.stock.rest.user;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;


import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import com.stock.entity.business.StockTelegramBotRecord;
import com.stock.service.TelegramBotService;
import com.stock.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


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
        // var bot = (StockTelegramBotRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (stockTelegramBot.getTelegramUserName() != "") {
            var result = telegramBotService.bindingAccToTelegramBot(stockTelegramBot);
            return  result ? ResponseEntity.ok().build() : ResponseEntity.status(404).build();
        }
       return ResponseEntity.status(400).build();
    }

}
