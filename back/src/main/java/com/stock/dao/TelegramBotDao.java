package com.stock.dao;

import com.stock.entity.business.AddressRecord;
import com.stock.entity.business.StockTelegramBotRecord;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@RegisterBeanMapper(StockTelegramBotRecord.class)
public interface TelegramBotDao {
    @SqlUpdate("INSERT INTO telegram_bot (id, telegram_user_name, chat_id) VALUES (:id, :telegramUserName, :chatId);")
    void create(@BindBean StockTelegramBotRecord stockTelegramBotRecord);
}
