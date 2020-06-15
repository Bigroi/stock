package com.bigroi.stock.service.impl;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.common.PartnerChoice;
import com.bigroi.stock.bean.db.Bid;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.dao.*;
import com.bigroi.stock.messager.email.EmailClient;
import com.bigroi.stock.messager.email.EmailType;
import com.bigroi.stock.service.MarketService;
import com.bigroi.stock.util.LabelUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class MarketServiceImpl implements MarketService {

    private final LotDao lotDao;
    private final TenderDao tenderDao;
    private final DealDao dealDao;
    private final CompanyDao companyDao;
    private final LabelDao labelDao;
    private final EmailClient emailClient;

    public MarketServiceImpl(
            LotDao lotDao,
            TenderDao tenderDao,
            DealDao dealDao,
            CompanyDao companyDao,
            LabelDao labelDao,
            EmailClient emailClient
    ) {
        this.lotDao = lotDao;
        this.tenderDao = tenderDao;
        this.dealDao = dealDao;
        this.emailClient = emailClient;
        this.labelDao = labelDao;
        this.companyDao = companyDao;
    }

    @Override
    @Transactional
    public void checkExpirations() {
        var companyEmails = new HashMap<Long, String>();
        var lots = lotDao.getActive();
        for (Lot lot : lots) {
            if (lot.isExpired()) {
                lot.setStatus(BidStatus.INACTIVE);
                lot.setAlertBool(true);
                sendExpirationEmail(lot, companyEmails, EmailType.LOT_EXPIRED);
            }
        }
        lotDao.updateStatus(lots);

        List<Tender> tenders = tenderDao.getActive();
        for (Tender tender : tenders) {
            if (tender.isExpired()) {
                tender.setStatus(BidStatus.INACTIVE);
                tender.setAlertBool(true);
                sendExpirationEmail(tender, companyEmails, EmailType.TENDER_EXPIRED);
            }
        }
        tenderDao.updateStatus(tenders);
    }

    private void sendExpirationEmail(Bid bid, Map<Long, String> companyEmails, EmailType emailType) {
        var params = new HashMap<String, Object>();
        params.put("price", bid.getPrice());
        params.put("description", bid.getDescription());
        params.put("volume", bid.getMaxVolume());
        emailClient.sendMessage(
                LabelUtil.parseString(bid.getCompanyAddress().getCompany().getLanguage()),
                companyEmails.computeIfAbsent(bid.getCompanyId(), id -> companyDao.getById(id).getEmail()),
                emailType,
                params
        );
    }

    @Override
    @Transactional
    public void clearPreDeal() {
        var localProduct = new HashMap<String, String>();
        var deals = dealDao.getOnApprove();
        for (Deal deal : deals) {
            if (deal.getLotId() == null) {
                continue;
            }
            var emailTypeForSeller = EmailType.DEAL_EXPIRATION_FOR_SELLER;
            var emailTypeForBuyer = EmailType.DEAL_EXPIRATION_FOR_BUYER;
            if (deal.getSellerChoice() != PartnerChoice.ON_APPROVE) {
                emailTypeForSeller = EmailType.DEAL_EXPIRATION_FOR_SELLER_BY_PARTNER;
            } else if (deal.getBuyerChoice() != PartnerChoice.ON_APPROVE) {
                emailTypeForBuyer = EmailType.DEAL_EXPIRATION_FOR_BUYER_BY_PARTNER;
            }
            sendConfirmationEmail(
                    deal,
                    LabelUtil.parseString(deal.getSellerLanguage()),
                    deal::getSellerEmail,
                    emailTypeForSeller,
                    localProduct
            );

            sendConfirmationEmail(
                    deal,
                    LabelUtil.parseString(deal.getBuyerLanguage()),
                    deal::getBuyerEmail,
                    emailTypeForBuyer,
                    localProduct
            );
            returnVolumeToBids(deal);
        }
        dealDao.deleteOnApprove();
        lotDao.close();
        tenderDao.close();
    }

    private void sendDealExpirationEmail(
            Deal deal,
            Locale locale,
            Supplier<String> to,
            EmailType emailType,
            Map<String, String> localProduct
    ) {
        var params = new HashMap<String, Object>();
        params.put("product", localProduct.computeIfAbsent(
                deal.getProductName(),
                productName -> labelDao.getLabel(productName, "name", locale))
        );
        emailClient.sendMessage(locale, to.get(), emailType, params);
    }

    private void returnVolumeToBids(Deal deal) {
        long buyerId = deal.getBuyerCompanyId();
        long sellerId = deal.getSellerCompanyId();

        Lot lot = lotDao.getById(deal.getLotId(), sellerId);
        lot.setMaxVolume(lot.getMaxVolume() + deal.getVolume());
        lotDao.update(lot, lot.getCompanyId());

        Tender tender = tenderDao.getById(deal.getTenderId(), buyerId);
        tender.setMaxVolume(tender.getMaxVolume() + deal.getVolume());
        tenderDao.update(tender, tender.getCompanyId());
    }

    @Override
    @Transactional
    public void sendConfirmationMessages() {
        var localProduct = new HashMap<String, String>();
        dealDao.getOnApprove().forEach(deal -> {
            sendConfirmationEmail(
                    deal,
                    LabelUtil.parseString(deal.getBuyerLanguage()),
                    deal::getBuyerEmail,
                    EmailType.DEAL_CONFIRMATION_FOR_BUYER,
                    localProduct
            );

            sendConfirmationEmail(
                    deal,
                    LabelUtil.parseString(deal.getSellerLanguage()),
                    deal::getSellerEmail,
                    EmailType.DEAL_CONFIRMATION_FOR_SELLER,
                    localProduct
            );
        });
    }

    private void sendConfirmationEmail(
            Deal deal,
            Locale locale,
            Supplier<String> to,
            EmailType emailType,
            Map<String, String> localProduct
    ) {
        var params = new HashMap<String, Object>();
        params.put("product", localProduct.computeIfAbsent(
                deal.getProductName(),
                productName -> labelDao.getLabel(productName, "name", locale))
        );
        params.put("price", deal.getPrice());

        emailClient.sendMessage(locale, to.get(), emailType, params);
    }
}
