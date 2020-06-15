package com.bigroi.stock.messager.email;

import com.bigroi.stock.bean.db.Email;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface EmailClient {

    default void sendMessage(
            Locale locale,
            String emailTo,
            EmailType emailType,
            Map<String, Object> messageParams
    ){
        sendMessage(locale, emailTo, emailType, messageParams, null, null);
    }

    void sendMessage(
            Locale locale,
            String emailTo,
            EmailType emailType,
            Map<String, Object> messageParams,
            String attachmentFileName,
            Map<String, Object> attachmentParams
    );

    void sendMessageNow(Email email);

    String getDefaultFrom();

    String getSupport();
}
