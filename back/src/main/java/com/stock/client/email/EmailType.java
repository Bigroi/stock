package com.stock.client.email;

public enum EmailType {

    //DEAL
    BUYER_CANCELED( "dealCancelled"),
    SELLER_CANCELED("dealCancelled"),
    DEAL_CONFIRMATION_FOR_BUYER("confirmationForBuyer"),
    DEAL_CONFIRMATION_FOR_SELLER("confirmationForSeller"),
    DEAL_EXPIRATION_FOR_BUYER("expiredDeal"),
    DEAL_EXPIRATION_FOR_SELLER("expiredDeal"),
    DEAL_EXPIRATION_FOR_BUYER_BY_PARTNER("expiredDealOpponent"),
    DEAL_EXPIRATION_FOR_SELLER_BY_PARTNER("expiredDealOpponent"),
    SUCCESS_DEAL_FOR_BUYER("successDeal"),
    SUCCESS_DEAL_FOR_SELLER("successDeal"),

    //OTHER
    LOT_EXPIRED("lotExpired"),
    TENDER_EXPIRED("tenderExpired"),
    PASSWORD_RESET("passwordReset"),
    LINK_FOR_PASSWORD_RESET("linkForResetPassw"),
    FEED_BACK("feedBack");

    private final String emailTemplateName;

    EmailType(String emailTemplateName) {
        this.emailTemplateName = emailTemplateName;
    }

    public String getEmailTemplateName() {
        return "emails/" + emailTemplateName;
    }
}
