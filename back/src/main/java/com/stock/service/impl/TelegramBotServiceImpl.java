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

    public TelegramBotServiceImpl(TelegramBotDao telegramBotDao, TelegramBot bot) {
        this.telegramBotDao = telegramBotDao;
        this.bot = bot;
    }

    public boolean bindingAccToTelegramBot(StockTelegramBotRecord stockTelegramBot) {
        return bot.execute(new GetUpdates().limit(1000).offset(0).timeout(0))
                .updates().stream().filter(update -> stockTelegramBot.getTelegramUserName().equals(update.message().from().username()))
                .findFirst().map(update -> {
                    stockTelegramBot.setId(UUID.randomUUID());
                    stockTelegramBot.setChatId(update.message().from().id());
                    telegramBotDao.create(stockTelegramBot);
                    bot.execute(new SendMessage(update.message().from().id(), "Hello " + update.message().from().firstName()));
                    return true;
                }).orElse(false);
    }
}
