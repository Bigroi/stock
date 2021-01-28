package com.stock.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.stock.dao.TelegramBotDao;
import com.stock.entity.business.StockTelegramBotRecord;
import com.stock.service.TelegramBotService;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.UUID;

public class TelegramBotServiceImpl implements TelegramBotService {

    private final String TELEGRAM_BOT_TOKEN = "1592037476:AAEvpJg59UCc3VFo8g29H771Mk88Jw_hSTc"; // TODO: move to resource cfg
    private final TelegramBot bot = new TelegramBot(TELEGRAM_BOT_TOKEN);
    private final TelegramBotDao telegramBotDao;

    public  TelegramBotServiceImpl(TelegramBotDao telegramBotDao) {
        this.telegramBotDao = telegramBotDao;
    }

    @Override
    public boolean bindingAccToTelegramBot(StockTelegramBotRecord stockTelegramBot) {
        GetUpdates getUpdates = new GetUpdates().limit(1000).offset(0).timeout(0);
        GetUpdatesResponse updatesResponse = bot.execute(getUpdates);
        List<Update> updates = updatesResponse.updates();
        for (Update update : updates) {
            if (stockTelegramBot.getTelegramUserName().equals(update.message().from().username())) {
                stockTelegramBot.setId(UUID.randomUUID());
                stockTelegramBot.setChatId(update.message().from().id());
                telegramBotDao.create(stockTelegramBot);
                bot.execute(new SendMessage(update.message().from().id(), "Hello " + update.message().from().firstName()));
                return true;
            }
        }
        return false;
    }
}
