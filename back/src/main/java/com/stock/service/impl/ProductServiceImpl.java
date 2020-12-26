package com.stock.service.impl;

import com.stock.dao.LotDao;
import com.stock.dao.ProductCategoryDao;
import com.stock.dao.ProductDao;
import com.stock.dao.TenderDao;
import com.stock.entity.BidStatus;
import com.stock.entity.business.LotRecord;
import com.stock.entity.business.ProductRecord;
import com.stock.entity.business.TenderRecord;
import com.stock.entity.ui.Product;
import com.stock.entity.ui.ProductStatistics;
import com.stock.entity.ui.ProductStatisticsDetails;
import com.stock.service.ProductService;
import com.stock.trading.entity.LotTradeRecord;
import com.stock.trading.entity.TenderTradeRecord;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private final LotDao lotDao;
    private final TenderDao tenderDao;
    private final ProductCategoryDao productCategoryDao;

    public ProductServiceImpl(
            ProductDao productDao,
            LotDao lotDao,
            TenderDao tenderDao,
            ProductCategoryDao productCategoryDao
    ) {
        this.productDao = productDao;
        this.lotDao = lotDao;
        this.tenderDao = tenderDao;
        this.productCategoryDao = productCategoryDao;
    }

    public List<Product> getActiveProducts() {
        return productDao.getActiveProductWithCategories().entrySet()
                .stream()
                .map(e -> new Product(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductRecord> getProductsAsAdmin() {
        return productDao.getAll();
    }

    @Override
    public UUID create(ProductRecord record) {
        record.setId(UUID.randomUUID());
        record.setRemoved(false);
        productDao.create(record);
        return record.getId();
    }

    @Override
    public boolean deactivate(UUID id) {
        if (productDao.setRemovedById(id, true)) {
            lotDao.deleteByProductId(id);
            tenderDao.deleteByProductId(id);
            productCategoryDao.setRemovedByProductId(id, true);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean activate(UUID id) {
        return productDao.setRemovedById(id, false);
    }

    @Override
    public List<ProductStatistics> getProductsStatistics() {
        var categoryProductMap = productDao.getActiveProductNameByCategory();
        var lots = lotDao.getByStatus(BidStatus.ACTIVE)
                .stream()
                .collect(Collectors.groupingBy(l -> categoryProductMap.get(l.getCategoryId()).getId()));

        var tenders = tenderDao.getByStatus(BidStatus.ACTIVE)
                .stream()
                .collect(Collectors.groupingBy(TenderRecord::getProductId));
        return new HashSet<>(categoryProductMap.values()).stream()
                .map(p -> new ProductStatistics(
                        p.getId(),
                        p.getName(),
                        p.getPicture(),
                        lots.getOrDefault(p.getId(), Collections.emptyList()).stream()
                                .mapToDouble(LotRecord::getPrice).average().orElse(0),
                        lots.getOrDefault(p.getId(), Collections.emptyList()).stream()
                                .mapToInt(LotRecord::getMaxVolume).sum(),
                        tenders.getOrDefault(p.getId(), Collections.emptyList()).stream()
                                .mapToDouble(TenderRecord::getPrice).average().orElse(0),
                        tenders.getOrDefault(p.getId(), Collections.emptyList()).stream()
                                .mapToInt(TenderRecord::getMaxVolume).sum()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductStatisticsDetails> getProductStatisticsDetails(UUID productId) {
        return productDao.getById(productId)
                .map(product -> {
                    var lots = lotDao.getByProductId(productId);
                    var lotMap = lots.stream()
                            .collect(Collectors.groupingBy(LotRecord::getPrice));
                    var tenders = tenderDao.getByProductId(productId);
                    var tenderMap = tenders.stream()
                            .collect(Collectors.groupingBy(TenderRecord::getPrice));

                    var prices = Stream.concat(
                            lotMap.keySet().stream(),
                            tenderMap.keySet().stream()
                    ).distinct()
                            .sorted(Double::compare)
                            .collect(Collectors.toList());

                    if (prices.size() <= 5) {
                        return getProductStatisticsDetailsForSmallPriceCount(prices, lotMap, tenderMap, product);
                    } else {
                        return getProductStatisticsDetailsForSmallPriceCount(prices, lots, tenders, product);
                    }
                });
    }

    private ProductStatisticsDetails getProductStatisticsDetailsForSmallPriceCount(
            List<Double> prices,
            List<LotTradeRecord> lots,
            List<TenderTradeRecord> tenders,
            ProductRecord product
    ) {
        var minPrice = prices.stream().mapToDouble(d -> d).min().orElse(0);
        var avgPrice = prices.stream().mapToDouble(p -> p).average().orElse(0);

        var rows = DoubleStream.of(0.3, 0.7, 1.3, 1.7, 2.1)
                .mapToObj(c -> {
                    var pricePoint = minPrice + (avgPrice - minPrice) * c;
                    var lotsForPoint = lots.stream()
                            .filter(l -> l.getPrice() <= pricePoint)
                            .collect(Collectors.toList());
                    lots.removeAll(lotsForPoint);

                    var tendersForPoint = tenders.stream()
                            .filter(l -> l.getPrice() <= pricePoint)
                            .collect(Collectors.toList());
                    tenders.removeAll(tendersForPoint);

                    var priceLabel = c == 0.3
                            ? "< " + Math.round(pricePoint * 100.0) / 100.0
                            : c == 2.1
                            ? "> " + Math.round(pricePoint * 100.0) / 100.0
                            : Math.round((minPrice + (avgPrice - minPrice) * (c - 0.5)) * 100.0) / 100.0
                            + " .. "
                            + Math.round(pricePoint * 100.0) / 100.0;

                    return new ProductStatisticsDetails.DetailsRow(
                            priceLabel,
                            lotsForPoint.stream().mapToInt(LotRecord::getMaxVolume).sum(),
                            tendersForPoint.stream().mapToInt(TenderRecord::getMaxVolume).sum()
                    );
                }).collect(Collectors.toList());
        return new ProductStatisticsDetails(
                product.getId(),
                product.getName(),
                product.getPicture(),
                rows
        );
    }

    private ProductStatisticsDetails getProductStatisticsDetailsForSmallPriceCount(
            List<Double> prices,
            Map<Double, List<LotTradeRecord>> lotMap,
            Map<Double, List<TenderTradeRecord>> tenderMap,
            ProductRecord product
            ) {
        var rows = prices.stream()
                .map(p -> new ProductStatisticsDetails.DetailsRow(
                                p + "",
                                lotMap.getOrDefault(p, Collections.emptyList())
                                        .stream()
                                        .mapToInt(LotRecord::getMaxVolume).sum(),
                                tenderMap.getOrDefault(p, Collections.emptyList())
                                        .stream()
                                        .mapToInt(TenderRecord::getMaxVolume).sum()
                        )
                ).collect(Collectors.toList());
        return new ProductStatisticsDetails(
                product.getId(),
                product.getName(),
                product.getPicture(),
                rows
        );
    }
}
