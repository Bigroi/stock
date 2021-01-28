package com.stock.service;

import com.stock.entity.business.StockTelegramBotRecord;

public interface TelegramBotService {
    boolean bindingAccToTelegramBot(StockTelegramBotRecord stockTelegramBot);
}
