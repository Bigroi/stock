package com.stock.entity.business;

import java.io.Serializable;
import java.util.UUID;

public class StockTelegramBotRecord implements Serializable {
    private UUID id;
    private String telegramUserName;
    private  long chatId;

    public StockTelegramBotRecord() {

    }
    public StockTelegramBotRecord(UUID id, String telegramUserName, long chatId ) {
        this.id =id;
        this.telegramUserName = telegramUserName;
        this.chatId = chatId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTelegramUserName() {
        return telegramUserName;
    }

    public void setTelegramUserName(String telegramUserName) {
        this.telegramUserName = telegramUserName;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
}
