package com.stock.client.email;

import com.stock.entity.Language;

import java.util.HashMap;
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
            Language language,
            String emailsTo,
            EmailType emailType,
            Map<String, Object> messageParams,
            String attachmentFileName,
            Map<String, Object> attachmentParams
    ) {
        var newMessageParams = new HashMap<>(messageParams);
        newMessageParams.put("subjectPrefix", "Mail to " + emailsTo + ": ");
        manager.sendMessage(
                language,
                staticTo,
                emailType,
                newMessageParams,
                attachmentFileName,
                attachmentParams
        );
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
