package com.stock.dao;

import com.stock.entity.business.StockTelegramBotRecord;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterBeanMapper(StockTelegramBotRecord.class)
public interface TelegramBotDao {
    @SqlUpdate("INSERT INTO TELEGRAM_BOT (id, telegram_user_name, chat_id) VALUES (:id, :telegramUserName, :chatId);")
    void create(@BindBean StockTelegramBotRecord stockTelegramBotRecord);
}
