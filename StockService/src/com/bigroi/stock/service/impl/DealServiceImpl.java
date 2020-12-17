package com.bigroi.stock.service.impl;

import com.bigroi.stock.bean.common.DealStatus;
import com.bigroi.stock.bean.common.PartnerChoice;
import com.bigroi.stock.bean.db.Blacklist;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.dao.*;
import com.bigroi.stock.messager.email.EmailClient;
import com.bigroi.stock.messager.email.EmailType;
import com.bigroi.stock.service.DealService;
import com.bigroi.stock.util.LabelUtil;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DealServiceImpl implements DealService {

    private final BlacklistDao blacklistDao;
    private final DealDao dealDao;
    private final LotDao lotDao;
    private final TenderDao tenderDao;
    private final EmailClient emailClient;
    private final LabelDao labelDao;


    public DealServiceImpl(
            BlacklistDao blacklistDao,
            DealDao dealDao,
            LotDao lotDao,
            TenderDao tenderDao,
            EmailClient emailClient,
            LabelDao labelDao
    ) {
        this.blacklistDao = blacklistDao;
        this.dealDao = dealDao;
        this.lotDao = lotDao;
        this.tenderDao = tenderDao;
        this.emailClient = emailClient;
        this.labelDao = labelDao;
    }

    @Override
    public Deal getById(long id, long companyId) {
        return dealDao.getById(id);
    }

    @Override
    public List<Deal> getByUserId(long companyId) {
        return dealDao.getByCompanyId(companyId);
    }

    @Override
    @Transactional
    public boolean reject(long id, long companyId) {
        var deal = dealDao.getById(id);
        var buyerId = deal.getBuyerCompanyId();
        var sellerId = deal.getSellerCompanyId();

        var messageParams = new HashMap<String, Object>();
        if (buyerId == companyId) {
            deal.setBuyerChoice(PartnerChoice.REJECTED);
            deal.setSellerAlertBool(true);
            dealDao.setBuyerStatus(deal);

            //send email to seller
            var locale = LabelUtil.parseString(deal.getSellerLanguage());
            messageParams.put("product", labelDao.getLabel(deal.getProductName(), "name", locale));
            emailClient.sendMessage(
                    locale,
                    deal.getSellerEmail(),
                    EmailType.BUYER_CANCELED,
                    messageParams
            );
        } else if (sellerId == companyId) {
            deal.setSellerChoice(PartnerChoice.REJECTED);
            deal.setBuyerAlertBool(true);
            dealDao.setSellerStatus(deal);

            //email to buyer
            var locale = LabelUtil.parseString(deal.getBuyerLanguage());
            messageParams.put("product", labelDao.getLabel(deal.getProductName(), "name", locale));
            emailClient.sendMessage(
                    locale,
                    deal.getBuyerEmail(),
                    EmailType.SELLER_CANCELED,
                    messageParams
            );
        } else {
            return false;
        }
        addBlackList(deal.getLotId(), deal.getTenderId());
        setVolumeBack(deal);
        return true;
    }

    private void setVolumeBack(Deal deal) {
        var buyerId = deal.getBuyerCompanyId();
        var sellerId = deal.getSellerCompanyId();

        var lot = lotDao.getById(deal.getLotId(), sellerId);
        lot.setMaxVolume(lot.getMaxVolume() + deal.getVolume());
        lotDao.update(lot, lot.getCompanyId());

        var tender = tenderDao.getById(deal.getTenderId(), buyerId);
        tender.setMaxVolume(tender.getMaxVolume() + deal.getVolume());
        tenderDao.update(tender, tender.getCompanyId());
    }

    @Override
    public DealStatus approve(long id, long companyId) {
        var deal = dealDao.getById(id);
        var buyerId = deal.getBuyerCompanyId();
        var sellerId = deal.getSellerCompanyId();

        if (buyerId == companyId) {
            deal.setBuyerChoice(PartnerChoice.APPROVED);
            dealDao.setBuyerStatus(deal);
        } else if (sellerId == companyId) {
            deal.setSellerChoice(PartnerChoice.APPROVED);
            dealDao.setSellerStatus(deal);
        } else {
            return null;
        }
        if (DealStatus.calculateStatus(deal.getBuyerChoice(), deal.getSellerChoice()) == DealStatus.APPROVED) {
            var attachmentParams = new HashMap<String, Object>();
            attachmentParams.put("{deal_date}", new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
            attachmentParams.put("{seller_city}", deal.getSellerCity());
            attachmentParams.put("{seller_company_name}", deal.getSellerCompanyName());
            attachmentParams.put("{seller_company_reg_number}", deal.getSellerRegNumber());
            attachmentParams.put("{seller_address}", deal.getSellerAddress());
            attachmentParams.put("{buyer_company_name}", deal.getBuyerCompanyName());
            attachmentParams.put("{buyer_company_reg_number}", deal.getBuyerRegNumber());
            attachmentParams.put("{buyer_city}", deal.getBuyerCity());
            attachmentParams.put("{buyer_address}", deal.getBuyerAddress());
            attachmentParams.put("{deal_volume}", deal.getVolume());
            attachmentParams.put("{seller_phone}", deal.getSellerPhone());

            //message to buyer
            var buyerMessageParams = new HashMap<String, Object>();
            var buyerLocale = LabelUtil.parseString(deal.getBuyerLanguage());

            var buyerProductName = labelDao.getLabel(deal.getProductName(), "name", buyerLocale);
            attachmentParams.put("{product_name}", buyerProductName);
            buyerMessageParams.put("product", buyerProductName);

            emailClient.sendMessage(
                    buyerLocale,
                    deal.getBuyerEmail(),
                    EmailType.SUCCESS_DEAL_FOR_BUYER,
                    buyerMessageParams,
                    "Deal",
                    attachmentParams
            );

            //message to seller
            var sellerMessageParams = new HashMap<String, Object>();
            var sellerLocale = LabelUtil.parseString(deal.getSellerLanguage());

            var sellerProductName = labelDao.getLabel(deal.getProductName(), "name", sellerLocale);
            attachmentParams.put("{product_name}", sellerProductName);
            sellerMessageParams.put("product", sellerProductName);

            emailClient.sendMessage(
                    sellerLocale,
                    deal.getSellerEmail(),
                    EmailType.SUCCESS_DEAL_FOR_SELLER,
                    sellerMessageParams,
                    "Deal",
                    attachmentParams
            );
            return DealStatus.APPROVED;
        }

        return DealStatus.ON_PARTNER_APPROVE;
    }

    private void addBlackList(long lotId, long tenderId) {
        var blackList = new Blacklist();
        blackList.setLotId(lotId);
        blackList.setTenderId(tenderId);
        blacklistDao.add(blackList);
    }

    @Override
    public boolean transport(long id, long companyId) {
        var deal = dealDao.getById(id);
        var buyerId = deal.getBuyerCompanyId();
        var sellerId = deal.getSellerCompanyId();

        if (buyerId == companyId) {
            deal.setBuyerChoice(PartnerChoice.TRANSPORT);
            dealDao.setBuyerStatus(deal);
        } else if (sellerId == companyId) {
            deal.setSellerChoice(PartnerChoice.TRANSPORT);
            dealDao.setSellerStatus(deal);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public List<Deal> getListBySellerAndBuyerApproved() {
        return dealDao.getListBySellerAndBuyerApproved();
    }
}
