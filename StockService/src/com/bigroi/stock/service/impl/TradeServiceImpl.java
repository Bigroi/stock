package com.bigroi.stock.service.impl;

import com.bigroi.stock.bean.db.*;
import com.bigroi.stock.dao.*;
import com.bigroi.stock.messager.email.EmailClient;
import com.bigroi.stock.messager.email.EmailType;
import com.bigroi.stock.service.TradeService;
import com.bigroi.stock.util.LabelUtil;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

public class TradeServiceImpl implements TradeService {

    private final ProductDao productDao;
    private final DealDao dealDao;
    private final LotDao lotDao;
    private final TenderDao tenderDao;
    private final AddressDao addressDao;
    private final EmailClient emailClient;
    private final LabelDao labelDao;

    private Set<TradeTender> tendersToUpdate = new HashSet<>();
    private Set<TradeLot> lotsToUpdate = new HashSet<>();
    private List<Deal> deals = new ArrayList<>();

    private boolean canUse = false;

    public TradeServiceImpl(
            ProductDao productDao,
            DealDao dealDao,
            LotDao lotDao,
            TenderDao tenderDao,
            AddressDao addressDao,
            EmailClient emailClient,
            LabelDao labelDao
    ) {
        this.productDao = productDao;
        this.dealDao = dealDao;
        this.lotDao = lotDao;
        this.tenderDao = tenderDao;
        this.addressDao = addressDao;
        this.emailClient = emailClient;
        this.labelDao = labelDao;
    }

    @Override
    @Transactional
    public void trade() {
        if (!canUse) {
            throw new RuntimeException("use newInstance() befor use");
        }
        productDao.getAllActiveProducts().forEach(this::productTrade);
        dealDao.add(deals);
        lotDao.update(lotsToUpdate);
        tenderDao.update(tendersToUpdate);
    }

    private void productTrade(Product product) {
        var tradeLots = new ArrayList<TradeLot>();
        var tradeTenders = new ArrayList<TradeTender>();
        dealDao.getPossibleDeals(tradeLots, tradeTenders, product.getId());
        removeByDistance(tradeLots);

        while (!tradeTenders.isEmpty() && !tradeLots.isEmpty()) {

            var majorBids = getMinVolumeBids(tradeLots, tradeTenders);

            TradeBid majorBid = getBestBid(majorBids);

            createDealsForBid(majorBid, product);

            removeAllZeroBids(tradeTenders, tradeLots);
        }
    }

    private void removeByDistance(List<? extends TradeBid> tradeBids) {
        tradeBids.forEach(bid ->
                bid.getPosiblePartners().forEach(partner -> {
                    var distance = TradeBid.getDistancePrice(bid, partner);
                    if (partner.getDistance() < distance && bid.getDistance() < distance) {
                        partner.getPosiblePartners().remove(bid);
                        bid.getPosiblePartners().remove(partner);
                    }
                })
        );
    }

    private void createDealsForBid(TradeBid bid, Product product) {
        while (bid.getMaxVolume() > 0 && !bid.getPosiblePartners().isEmpty()) {
            var partner = bid.getBestPartner();
            var deal = createDeal(bid, partner);
            deal.setProductId(product.getId());
            deal.setProductName(product.getName());
            sendConfirmationMails(deal);
            deals.add(deal);

            if (partner.getMaxVolume() < partner.getMinVolume()) {
                partner.removeFromPosiblePartners();
            }

            if (bid.getMaxVolume() < bid.getMinVolume()) {
                bid.removeFromPosiblePartners();
            }
        }
    }

    private void sendConfirmationMails(Deal deal) {
        var buyerLocate = LabelUtil.parseString(deal.getBuyerLanguage());
        var buyerParams = new HashMap<String, Object>();
        buyerParams.put("product", labelDao.getLabel(deal.getProductName(), "name", buyerLocate));
        buyerParams.put("price", deal.getPrice() + "");
        emailClient.sendMessage(
                buyerLocate,
                deal.getBuyerEmail(),
                EmailType.DEAL_CONFIRMATION_FOR_BUYER,
                buyerParams
        );

        var sellerLocate = LabelUtil.parseString(deal.getSellerLanguage());
        var sellerParams = new HashMap<String, Object>();
        sellerParams.put("product", labelDao.getLabel(deal.getProductName(), "name", sellerLocate));
        sellerParams.put("price", deal.getPrice() + "");
        emailClient.sendMessage(
                sellerLocate,
                deal.getSellerEmail(),
                EmailType.DEAL_CONFIRMATION_FOR_SELLER,
                sellerParams
        );
    }

    private Deal createDeal(TradeBid bid, TradeBid partner) {
        var volume = Math.min(bid.getMaxVolume(), partner.getMaxVolume());
        bid.setMaxVolume(bid.getMaxVolume() - volume);
        partner.setMaxVolume(partner.getMaxVolume() - volume);

        var maxTransportPrice = TradeBid.getDistancePrice(bid, partner);

        TradeLot lot;
        TradeTender tender;
        if (bid instanceof TradeLot) {
            lot = (TradeLot) bid;
            tender = (TradeTender) partner;
        } else {
            lot = (TradeLot) partner;
            tender = (TradeTender) bid;
        }
        Deal deal = new Deal(lot, tender, volume, maxTransportPrice);

        lotsToUpdate.add(lot);
        tendersToUpdate.add(tender);
        return deal;
    }

    private TradeBid getBestBid(List<? extends TradeBid> bids) {
        var bid = Collections.min(bids, Comparator.comparingInt(o -> o.getPosiblePartners().size()));
        bids = bids.stream()
                .filter(b -> b.getPosiblePartners().size() == bid.getPosiblePartners().size())
                .collect(Collectors.toList());

        return Collections.min(bids, Comparator.comparingInt(TradeBid::getMaxVolume));
    }

    private List<? extends TradeBid> getMinVolumeBids(List<TradeLot> tradeLots, List<TradeTender> tradeTenders) {
        if (getTotalVolume(tradeLots) < getTotalVolume(tradeTenders)) {
            return tradeLots;
        } else {
            return tradeTenders;
        }
    }

    private int getTotalVolume(List<? extends TradeBid> tradeBids) {
        var result = 0;
        for (TradeBid bid : tradeBids) {
            result += bid.getMaxVolume();
        }
        return result;
    }

    private void removeAllZeroBids(List<TradeTender> tradeTenders, List<TradeLot> tradeLots) {
        tradeTenders.removeIf(tender -> tender.getPosiblePartners().isEmpty());
        tradeLots.removeIf(lot -> lot.getPosiblePartners().isEmpty());
    }

    @Override
    public List<Deal> testTrade(String sessionId) {
        if (!canUse) {
            throw new RuntimeException("use newInstance() before use");
        }

        var dealDao = Mockito.mock(DealDao.class);
        Mockito.doAnswer(x -> testDealDaoAnswer(x, this.dealDao, sessionId))
                .when(dealDao).getPossibleDeals(Mockito.any(), Mockito.any(), Mockito.anyLong());

        var instance = new TradeServiceImpl(
                Mockito.mock(ProductDao.class),
                dealDao,
                Mockito.mock(LotDao.class),
                Mockito.mock(TenderDao.class),
                Mockito.mock(AddressDao.class),
                Mockito.mock(EmailClient.class),
                labelDao
        );
        instance.trade();

        deals.forEach(this::enrichAddress);

        return deals;
    }

    private void enrichAddress(Deal deal) {
        var buyerAddress = addressDao.getAddressById(deal.getBuyerAddressId(), 0);
        deal.setBuyerAddress(buyerAddress.getAddress());
        deal.setBuyerCity(buyerAddress.getCity());
        deal.setBuyerCountry(buyerAddress.getCity());

        var sellerAddress = addressDao.getAddressById(deal.getSellerAddressId(), 0);
        deal.setSellerAddress(sellerAddress.getAddress());
        deal.setSellerCity(sellerAddress.getCity());
        deal.setSellerCountry(sellerAddress.getCity());
    }

    @SuppressWarnings("unchecked")
    private Void testDealDaoAnswer(InvocationOnMock invocation, DealDao realDealDao, String sessionId) {
        var lots = (List<TradeLot>) invocation.getArguments()[0];
        var tenders = (List<TradeTender>) invocation.getArguments()[1];
        var productId = (long) invocation.getArguments()[2];
        realDealDao.getTestPossibleDeals(lots, tenders, productId, sessionId);
        return null;
    }

    @Override
    public TradeService newInstance() {
        var instance = new TradeServiceImpl(
                productDao,
                dealDao,
                lotDao,
                tenderDao,
                addressDao,
                emailClient,
                labelDao
        );
        instance.canUse = true;
        return instance;
    }
}
