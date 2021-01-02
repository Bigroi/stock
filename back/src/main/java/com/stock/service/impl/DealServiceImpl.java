package com.stock.service.impl;

import com.stock.dao.*;
import com.stock.entity.DealStatus;
import com.stock.entity.PartnerChoice;
import com.stock.entity.business.ProductCategoryRecord;
import com.stock.entity.business.ProductRecord;
import com.stock.entity.business.UserCommentRecord;
import com.stock.entity.ui.Deal;
import com.stock.entity.ui.DealDetails;
import com.stock.entity.ui.UserComment;
import com.stock.mapper.DealMapper;
import com.stock.service.DealService;

import java.util.*;
import java.util.stream.Collectors;

public class DealServiceImpl implements DealService {

    private final DealDao dealDao;
    private final ProductDao productDao;
    private final ProductCategoryDao categoryDao;
    private final CompanyDao companyDao;
    private final UserCommentDao userCommentDao;
    private final BidBlackListDao bidBlackListDao;
    private final DealMapper dealMapper;

    public DealServiceImpl(
            DealDao dealDao,
            ProductDao productDao,
            ProductCategoryDao categoryDao,
            CompanyDao companyDao,
            UserCommentDao userCommentDao,
            BidBlackListDao bidBlackListDao,
            DealMapper dealMapper
    ) {
        this.dealDao = dealDao;
        this.productDao = productDao;
        this.categoryDao = categoryDao;
        this.companyDao = companyDao;
        this.userCommentDao = userCommentDao;
        this.bidBlackListDao = bidBlackListDao;
        this.dealMapper = dealMapper;
    }

    @Override
    public List<Deal> getByCompanyId(UUID companyId) {
        var products = productDao.getAll().stream().collect(Collectors.toMap(ProductRecord::getId, p -> p));
        var categories = categoryDao.getAll().stream().collect(Collectors.toMap(ProductCategoryRecord::getId, c -> c));
        return dealDao.getByCompanyId(companyId)
                .stream()
                .map(record -> dealMapper.mapDeal(
                        record,
                        companyId,
                        categories.get(record.getCategoryId()).getCategoryName(),
                        products.get(categories.get(record.getCategoryId()).getProductId()).getName()
                )).collect(Collectors.toList());
    }

    @Override
    public Optional<DealDetails> getById(UUID id, UUID companyId) {
        return dealDao.getByIdAndCompanyId(id, companyId)
                .map(record -> {
                    var partnerCompanyId = record.getSellerCompanyId().equals(companyId)
                            ? record.getBuyerCompanyId()
                            : record.getSellerCompanyId();

                    var partner = companyDao.getByID(partnerCompanyId);
                    var products = productDao.getAll().stream()
                            .collect(Collectors.toMap(ProductRecord::getId, p -> p));
                    var categories = categoryDao.getAll().stream()
                            .collect(Collectors.toMap(ProductCategoryRecord::getId, c -> c));
                    var partnerMark = userCommentDao.getByCompanyId(partnerCompanyId).stream()
                            .mapToDouble(UserCommentRecord::getMark)
                            .average()
                            .orElse(0);
                    return dealMapper.mapDealDetails(
                            record,
                            companyId,
                            products.get(categories.get(record.getCategoryId()).getProductId()).getName(),
                            categories.get(record.getCategoryId()).getCategoryName(),
                            partner,
                            partnerMark
                    );
                });

    }

    @Override
    public Optional<DealStatus> setState(UUID id, UUID companyId, PartnerChoice choice) {
        return dealDao.getByIdAndCompanyId(id, companyId)
                .map(record -> {
                    ChangeChoice changeChoice;
                    PartnerChoice partnerChoice;
                    if (record.getSellerCompanyId().equals(companyId)) {
                        partnerChoice = record.getBuyerChoice();
                        changeChoice = dealDao::changeSellerChoice;
                    } else {
                        record.setBuyerChoice(choice);
                        changeChoice = dealDao::changeBuyerChoice;
                        partnerChoice = record.getSellerChoice();
                    }

                    if (partnerChoice != PartnerChoice.REJECTED && changeChoice.execute(id, companyId, choice)) {
                        if (choice == PartnerChoice.REJECTED) {
                            bidBlackListDao.createForDeal(id);
                        }
                        return DealStatus.calculateStatus(choice, partnerChoice);
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull);
    }

    @Override
    public boolean createComment(UUID dealId, UUID companyId, UserComment comment) {
        return dealDao.getByIdAndCompanyId(dealId, companyId)
                .map(deal -> userCommentDao.getByDealIdAndReporterId(dealId, companyId)
                        .map(c -> false)
                        .orElseGet(() -> {
                            var newComment = new UserCommentRecord();
                            newComment.setId(UUID.randomUUID());
                            newComment.setComment(comment.getComment());
                            newComment.setCommentDate(new Date());
                            newComment.setCompanyId(
                                    deal.getBuyerCompanyId().equals(companyId)
                                            ? deal.getSellerCompanyId()
                                            : deal.getBuyerCompanyId()
                            );
                            newComment.setDealId(dealId);
                            newComment.setMark(comment.getMark());
                            newComment.setReporterId(companyId);
                            return userCommentDao.create(newComment);
                        })
                )
                .orElse(false);
    }

    @Override
    public boolean updateComment(UUID dealId, UUID companyId, UserComment comment) {
        return userCommentDao.updateByDealIdAndReporterId(dealId, companyId, comment.getComment(), comment.getMark());
    }

    @Override
    public Optional<UserComment> getComment(UUID dealId, UUID companyId) {
        return userCommentDao.getByDealIdAndReporterId(dealId, companyId)
                .map(record -> {
                    var comment = new UserComment();
                    comment.setComment(record.getComment());
                    comment.setMark(record.getMark());
                    return comment;
                });
    }

    @FunctionalInterface
    private interface ChangeChoice {
        boolean execute(UUID id, UUID companyId, PartnerChoice choice);
    }
}