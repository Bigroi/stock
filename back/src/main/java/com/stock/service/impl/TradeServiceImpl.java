package com.stock.service.impl;

import com.stock.client.email.EmailClient;
import com.stock.client.email.EmailType;
import com.stock.dao.*;
import com.stock.entity.BidStatus;
import com.stock.entity.PartnerChoice;
import com.stock.entity.business.LabelRecord;
import com.stock.entity.business.LotRecord;
import com.stock.entity.business.TenderRecord;
import com.stock.entity.business.UserRecord;
import com.stock.service.TradeService;
import com.stock.trading.TradeSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TradeServiceImpl implements TradeService {

    private final UserDao userDao;
    private final LotDao lotDao;
    private final TenderDao tenderDao;
    private final DealDao dealDao;
    private final LabelDao labelDao;
    private final ProductDao productDao;
    private final EmailClient emailClient;
    private final TradeSession tradeSession;

    public TradeServiceImpl(
            UserDao userDao,
            LotDao lotDao,
            TenderDao tenderDao,
            DealDao dealDao,
            LabelDao labelDao,
            ProductDao productDao,
            EmailClient emailClient,
            TradeSession tradeSession
    ) {
        this.userDao = userDao;
        this.lotDao = lotDao;
        this.tenderDao = tenderDao;
        this.dealDao = dealDao;
        this.labelDao = labelDao;
        this.productDao = productDao;
        this.emailClient = emailClient;
        this.tradeSession = tradeSession;
    }

    @Override
    public void expirationCheck() {
        var users = userDao.getAll().stream().collect(Collectors.toMap(UserRecord::getCompanyId, u -> u));
        var expiredLots = new ArrayList<LotRecord>();
        lotDao.getByStatus(BidStatus.ACTIVE).forEach(lot -> {
            if (lot.isExpired()) {
                lot.setStatus(BidStatus.INACTIVE);
                lot.setAlert(true);
                expiredLots.add(lot);

                var user = users.get(lot.getCompanyId());
                var params = Map.<String, Object>of(
                        "price", lot.getPrice(),
                        "description", lot.getDescription(),
                        "volume", lot.getMaxVolume()
                );
                emailClient.sendMessage(user.getLanguage(), user.getUserName(), EmailType.LOT_EXPIRED, params);
            }
        });
        lotDao.updateStatusAndAlert(expiredLots);

        var expiredTenders = new ArrayList<TenderRecord>();
        tenderDao.getByStatus(BidStatus.ACTIVE).forEach(tender -> {
            if (tender.isExpired()) {
                tender.setStatus(BidStatus.INACTIVE);
                tender.setAlert(true);
                expiredTenders.add(tender);

                var user = users.get(tender.getCompanyId());
                var params = Map.<String, Object>of(
                        "price", tender.getPrice(),
                        "description", tender.getDescription(),
                        "volume", tender.getMaxVolume()
                );
                emailClient.sendMessage(user.getLanguage(), user.getUserName(), EmailType.TENDER_EXPIRED, params);
            }
        });
        tenderDao.updateStatusAndAlert(expiredTenders);
    }

    @Override
    public void cleanPreDeals() {
        var users = userDao.getAll().stream().collect(Collectors.toMap(UserRecord::getCompanyId, u -> u));
        var products = productDao.getActiveProductNameByCategory();
        var labels = labelDao.getAll()
                .stream().collect(Collectors.groupingBy(
                        LabelRecord::getName,
                        Collectors.toMap(LabelRecord::getLanguage, LabelRecord::getValue)
                ));

        var tendersToReturnValue = new ArrayList<TenderRecord>();
        var lotsToReturnValue = new ArrayList<LotRecord>();

        dealDao.getOnApprove().forEach(deal -> {
            var emailTypeForSeller = EmailType.DEAL_EXPIRATION_FOR_SELLER;
            var emailTypeForBuyer = EmailType.DEAL_EXPIRATION_FOR_BUYER;
            if (deal.getSellerChoice() != PartnerChoice.ON_APPROVE) {
                emailTypeForSeller = EmailType.DEAL_EXPIRATION_FOR_SELLER_BY_PARTNER;
            } else if (deal.getBuyerChoice() != PartnerChoice.ON_APPROVE) {
                emailTypeForBuyer = EmailType.DEAL_EXPIRATION_FOR_BUYER_BY_PARTNER;
            }

            var seller = users.get(deal.getSellerCompanyId());
            var productNameForSeller = labels
                    .get("label." + products.get(deal.getCategoryId()).getName() + ".name")
                    .get(seller.getLanguage());
            emailClient.sendMessage(
                    seller.getLanguage(),
                    seller.getUserName(),
                    emailTypeForSeller,
                    Map.of("product", productNameForSeller, "price", deal.getPrice())
            );

            var buyer = users.get(deal.getBuyerCompanyId());
            var productNameForBuyer = labels
                    .get("label." + products.get(deal.getCategoryId()).getName() + ".name")
                    .get(buyer.getLanguage());
            emailClient.sendMessage(
                    buyer.getLanguage(),
                    buyer.getUserName(),
                    emailTypeForSeller,
                    Map.of("product", productNameForBuyer, "price", deal.getPrice())
            );

            var lotToReturnValue = new LotRecord();
            lotToReturnValue.setMaxVolume(deal.getVolume());
            lotToReturnValue.setId(deal.getLotId());
            lotsToReturnValue.add(lotToReturnValue);

            var tenderToReturnValue = new TenderRecord();
            tenderToReturnValue.setMaxVolume(deal.getVolume());
            tenderToReturnValue.setId(deal.getLotId());
            tendersToReturnValue.add(tenderToReturnValue);
        });


        dealDao.deleteOnApprove();
        lotDao.returnVolume(lotsToReturnValue);
        tenderDao.returnVolume(tendersToReturnValue);
    }

    @Override
    public void trade() {
        productDao.getActiveProducts().forEach(tradeSession::tradeProduct);
    }
}
