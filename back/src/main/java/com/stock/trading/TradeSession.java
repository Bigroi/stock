package com.stock.trading;

import com.stock.client.email.EmailClient;
import com.stock.client.email.EmailType;
import com.stock.dao.*;
import com.stock.entity.Language;
import com.stock.entity.PartnerChoice;
import com.stock.entity.business.*;
import com.stock.trading.entity.LotTradeRecord;
import com.stock.trading.entity.TenderTradeRecord;
import com.stock.trading.graph.Graph;
import com.stock.trading.graph.Line;
import com.stock.trading.graph.Node;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class TradeSession {

    private static Predicate<Line> SAME_USER = line ->
            line.getLotElement().getElement().getCompanyId().equals(line.getTenderElement().getElement().getCompanyId());

    private static Predicate<Line> DISTANCE_RULE = line ->
            line.getLength() > line.getLotElement().getElement().getDistance()
                    && line.getLength() > line.getTenderElement().getElement().getDistance();

    private static BiPredicate<List<BidBlackListRecord>, Line> BLACKLIST_RULE = (bl, line) ->
            bl.stream().anyMatch(e ->
                    line.getTenderElement().getElement().getId().equals(e.getTenderId())
                            && line.getLotElement().getElement().getId().equals(e.getLotId()));

    private static Predicate<Line> CATEGORY_RULE = line -> {
        var r = line.getTenderElement().getElement().getCategoryId() != null
                && !line.getLotElement().getElement().getCategoryId().equals(line.getTenderElement().getElement().getCategoryId());
        if (r) System.out.println("CATEGORY_RULE");
        return r;
    };

    private static Predicate<Line> MAX_MIN_RULE = line ->
            line.getTenderElement().getElement().getMaxVolume() < line.getLotElement().getElement().getMinVolume()
                    || line.getLotElement().getElement().getMaxVolume() < line.getTenderElement().getElement().getMinVolume();

    private static Predicate<Line> PRICE_RULE = line ->
            line.getTenderElement().getElement().getPrice() < line.getLotElement().getElement().getPrice();


    private final DealDao dealDao;
    private final LotDao lotDao;
    private final TenderDao tenderDao;
    private final LabelDao labelDao;
    private final BidBlackListDao bidBlackListDao;
    private final UserDao userDao;
    private final EmailClient emailClient;

    public TradeSession(
            DealDao dealDao,
            LotDao lotDao,
            TenderDao tenderDao,
            LabelDao labelDao,
            BidBlackListDao bidBlackListDao,
            UserDao userDao,
            EmailClient emailClient
    ) {
        this.dealDao = dealDao;
        this.lotDao = lotDao;
        this.tenderDao = tenderDao;
        this.labelDao = labelDao;
        this.bidBlackListDao = bidBlackListDao;
        this.userDao = userDao;
        this.emailClient = emailClient;
    }

    public void tradeProduct(ProductRecord product) {
        var tendersToUpdate = new HashSet<TenderRecord>();
        var lotsToUpdate = new HashSet<LotRecord>();
        var deals = new ArrayList<DealRecord>();

        var productNames = labelDao.getByName("label." + product.getName() + ".name");
        var users = userDao.getAll().stream().collect(Collectors.toMap(UserRecord::getCompanyId, u -> u));
        var blackList = bidBlackListDao.getAll();
        var lots = lotDao.getByProductId(product.getId());
        var tenders = tenderDao.getByProductId(product.getId());

        var graph = Graph.GraphBuilder.createGraph(lots, tenders);
        graph.acceptLineRules(
                CATEGORY_RULE
                        .or(l -> {
                            var r = SAME_USER.test(l);
                            if (r) System.out.println("SAME_USER");
                            return r;
                        })
                        .or(l -> {
                            var r = BLACKLIST_RULE.test(blackList, l);
                            if (r) System.out.println("BLACKLIST_RULE");
                            return r;
                        })
                        .or(l -> {
                            var r = DISTANCE_RULE.test(l);
                            if (r) System.out.println("DISTANCE_RULE");
                            return r;
                        })
                        .or(l -> {
                            var r = MAX_MIN_RULE.test(l);
                            if (r) System.out.println("MAX_MIN_RULE");
                            return r;
                        })
                        .or(l -> {
                            var r = PRICE_RULE.test(l);
                            if (r) System.out.println("PRICE_RULE");
                            return r;
                        })
        );

        while (!graph.getLotNodes().isEmpty() && !graph.getTenderNodes().isEmpty()) {
            if (graph.getTotalLotVolume() < graph.getTotalTenderVolume()) {
                var lotNodes = graph.getLotNodes();
                var lotNode = getBestNode(lotNodes, n -> n.getElement().getMaxVolume());

                while (!lotNode.getLines().isEmpty()) {
                    var partner = getBestPartner(
                            lotNode,
                            n -> n.getElement().getMaxVolume(),
                            l -> l.getTenderElement().getElement().getMaxVolume(),
                            l -> l.getTenderElement().getElement().getPrice(),
                            l -> l.getTenderElement().getElement().getCreationDate()
                    ).getTenderElement();

                    var lot = lotNode.getElement();
                    var tender = partner.getElement();
                    var deal = createDeal(lot, tender);
                    lot.setMaxVolume(lot.getMinVolume() - deal.getVolume());
                    tender.setMaxVolume(tender.getMaxVolume() - deal.getVolume());
                    lotNode.getLines().removeIf(l -> l.getTenderElement().getElement().getId().equals(tender.getId()));
                    deals.add(deal);
                    lotsToUpdate.add(lot);
                    tendersToUpdate.add(tender);
                }
            } else {
                var tenderNodes = graph.getTenderNodes();
                var tenderNode = getBestNode(tenderNodes, n -> n.getElement().getMaxVolume());

                while (!tenderNode.getLines().isEmpty()) {
                    var partner = getBestPartner(
                            tenderNode,
                            n -> n.getElement().getMaxVolume(),
                            l -> l.getLotElement().getElement().getMaxVolume(),
                            l -> l.getLotElement().getElement().getPrice(),
                            l -> l.getLotElement().getElement().getCreationDate()
                    ).getLotElement();

                    var tender = tenderNode.getElement();
                    var lot = partner.getElement();
                    var deal = createDeal(lot, tender);
                    lot.setMaxVolume(lot.getMinVolume() - deal.getVolume());
                    tender.setMaxVolume(tender.getMaxVolume() - deal.getVolume());
                    tenderNode.getLines().removeIf(l -> l.getLotElement().getElement().getId().equals(lot.getId()));
                    deals.add(deal);
                    lotsToUpdate.add(lot);
                    tendersToUpdate.add(tender);
                }
            }

            graph.removeEmptyNotes();
        }

        if (!deals.isEmpty()) {
            dealDao.create(deals);
            lotDao.updateStatusAndAlert(lotsToUpdate);
            tenderDao.updateStatusAndAlert(tendersToUpdate);
            sendConfirmationMails(deals, users, productNames);
        }
    }

    private <BID> Line getBestPartner(
            Node<BID> bidNode,
            ToIntFunction<Node<BID>> bidMaxVolume,
            ToIntFunction<Line> partnerMaxVolume,
            ToDoubleFunction<Line> partnerPrice,
            Function<Line, Date> partnerDate
    ) {
        var bidMaxVolumeValue = bidMaxVolume.applyAsInt(bidNode);
        return bidNode.getLines().stream()
                .max((l1, l2) -> {
                    var o2Volume = Math.min(partnerMaxVolume.applyAsInt(l2), bidMaxVolumeValue);
                    var o2Price = (partnerPrice.applyAsDouble(l2) * o2Volume + l2.getLength()) / o2Volume;

                    var o1Volume = Math.min(partnerMaxVolume.applyAsInt(l1), bidMaxVolumeValue);
                    var o1Price = (partnerPrice.applyAsDouble(l1) * o1Volume + l1.getLength()) / o1Volume;

                    int result = (int) ((o1Price - o2Price) * 10000);
                    if (result == 0) {
                        return (int) (partnerDate.apply(l1).getTime() - partnerDate.apply(l2).getTime());
                    } else {
                        return result;
                    }
                }).orElseThrow();
    }

    private void sendConfirmationMails(
            List<DealRecord> deals,
            Map<UUID, UserRecord> users,
            Map<Language, String> productName
    ) {
        deals.forEach(deal -> {
            var buyer = users.get(deal.getBuyerCompanyId());
            emailClient.sendMessage(
                    buyer.getLanguage(),
                    buyer.getUserName(),
                    EmailType.DEAL_CONFIRMATION_FOR_BUYER,
                    Map.of(
                            "product", productName.get(buyer.getLanguage()),
                            "price", deal.getPrice()
                    )
            );

            var seller = users.get(deal.getSellerCompanyId());
            emailClient.sendMessage(
                    seller.getLanguage(),
                    seller.getUserName(),
                    EmailType.DEAL_CONFIRMATION_FOR_SELLER,
                    Map.of(
                            "product", productName.get(seller.getLanguage()),
                            "price", deal.getPrice()
                    )
            );
        });
    }

    private <T> Node<T> getBestNode(List<Node<T>> nodes, ToIntFunction<Node<T>> maxVolume) {
        Comparator<Node<T>> linesComparator = Comparator.comparingInt(n -> n.getLines().size());
        return nodes.stream().min(linesComparator.thenComparingInt(maxVolume)).orElseThrow();
    }

    private DealRecord createDeal(LotTradeRecord lot, TenderTradeRecord tender) {
        var dealRecord = new DealRecord();
        dealRecord.setId(UUID.randomUUID());
        dealRecord.setLotId(lot.getId());
        dealRecord.setTenderId(tender.getId());
        dealRecord.setSellerCompanyId(lot.getCompanyId());
        dealRecord.setBuyerCompanyId(tender.getCompanyId());
        dealRecord.setSellerAddress(new DealRecord.DealAddress(
                lot.getAddressLine(),
                lot.getLatitude(),
                lot.getLongitude()
        ));
        dealRecord.setBuyerAddress(new DealRecord.DealAddress(
                tender.getAddressLine(),
                tender.getLatitude(),
                tender.getLongitude()
        ));
        dealRecord.setCreationDate(new Date());
        dealRecord.setSellerChoice(PartnerChoice.ON_APPROVE);
        dealRecord.setBuyerChoice(PartnerChoice.ON_APPROVE);
        dealRecord.setPrice((lot.getPrice() + tender.getPrice()) / 2);
        dealRecord.setVolume(Math.min(lot.getMaxVolume(), tender.getMaxVolume()));
        dealRecord.setCategoryId(lot.getCategoryId());
        dealRecord.setPhoto(lot.getPhoto());
        dealRecord.setSellerDescription(lot.getDescription());
        dealRecord.setBuyerDescription(tender.getDescription());
        dealRecord.setProcessing(tender.getPackaging());
        dealRecord.setPackaging(tender.getPackaging());
        dealRecord.setSellerAlert(false);
        dealRecord.setBuyerAlert(false);
        return dealRecord;
    }

}
