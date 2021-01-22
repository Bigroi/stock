package com.stock.service.impl;

import com.stock.client.email.EmailClient;
import com.stock.client.email.EmailType;
import com.stock.entity.ui.FeedBackRequest;
import com.stock.service.FeedBackService;

import java.util.Map;

public class FeedBackServiceImpl implements FeedBackService {

    private final EmailClient emailClient;

    public FeedBackServiceImpl(EmailClient emailClient) {
        this.emailClient = emailClient;
    }

    @Override
    public void sendFeedBack(FeedBackRequest feedBackRequest) {
        emailClient.sendMessage(
                feedBackRequest.getLanguage(),
                emailClient.getSupport(),
                EmailType.FEED_BACK,
                Map.of("email", feedBackRequest.getEmail(), "message", feedBackRequest.getMessage())
        );
    }
}
