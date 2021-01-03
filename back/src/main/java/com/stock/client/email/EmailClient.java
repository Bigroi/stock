package com.stock.client.email;

import com.stock.entity.Language;

import java.util.Map;

public interface EmailClient {

    default void sendMessage(
            Language language,
            String emailTo,
            EmailType emailType,
            Map<String, Object> messageParams
    ) {
        sendMessage(language, emailTo, emailType, messageParams, null, null);
    }

    void sendMessage(
            Language language,
            String emailTo,
            EmailType emailType,
            Map<String, Object> messageParams,
            String attachmentFileName,
            Map<String, Object> attachmentParams
    );

    String getDefaultFrom();

    String getSupport();
}
