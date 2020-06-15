package com.bigroi.stock.messager.email;

import com.bigroi.stock.bean.db.Email;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class StaticEmailClient implements EmailClient {

    private final EmailClient manager;
    private final String staticTo;

    public StaticEmailClient(EmailClient manager, String staticTo) {
        this.manager = manager;
        this.staticTo = staticTo;
    }

    @Override
    public void sendMessage(
            Locale locale,
            String emailTo,
            EmailType emailType,
            Map<String, Object> messageParams,
            String attachmentFileName,
            Map<String, Object> attachmentParams) {
        var newMessageParams = new HashMap<>(messageParams);
        newMessageParams.put("subjectPrefix", "Mail to " + emailTo + ": ");
        manager.sendMessage(locale, staticTo, emailType, newMessageParams, attachmentFileName, attachmentParams);
    }

    @Override
    public void sendMessageNow(Email email) {
        manager.sendMessageNow(email);
    }

    @Override
    public String getDefaultFrom() {
        return manager.getDefaultFrom();
    }

    @Override
    public String getSupport() {
        return manager.getSupport();
    }
}
