package com.stock.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.stock.dao.TelegramBotDao;
import com.stock.entity.business.StockTelegramBotRecord;
import com.stock.service.TelegramBotService;
import java.util.UUID;

public class TelegramBotServiceImpl implements TelegramBotService {


    private final TelegramBotDao telegramBotDao;
    private final TelegramBot bot;

    public  TelegramBotServiceImpl(TelegramBotDao telegramBotDao, TelegramBot bot) {
        this.telegramBotDao = telegramBotDao;
        this.bot = bot;
    }

    @Override
    public boolean bindingAccToTelegramBot(StockTelegramBotRecord stockTelegramBot) {
        var getUpdates = new GetUpdates().limit(1000).offset(0).timeout(0);
        var updatesResponse = this.bot.execute(getUpdates);
        var updates = updatesResponse.updates();
        updates.forEach(update -> {
            if (stockTelegramBot.getTelegramUserName().equals(update.message().from().username())) {
                stockTelegramBot.setId(UUID.randomUUID());
                stockTelegramBot.setChatId(update.message().from().id());
                telegramBotDao.create(stockTelegramBot);
                bot.execute(new SendMessage(update.message().from().id(), "Hello " + update.message().from().firstName()));
            }
        });
        return stockTelegramBot.getChatId() != 0 ? true : false;
    }
}
