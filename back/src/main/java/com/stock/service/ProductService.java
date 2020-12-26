package com.stock.service;

import com.stock.entity.business.ProductRecord;
import com.stock.entity.ui.Product;
import com.stock.entity.ui.ProductStatistics;
import com.stock.entity.ui.ProductStatisticsDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    List<Product> getActiveProducts();

    List<ProductRecord> getProductsAsAdmin();

    UUID create(ProductRecord record);

    boolean deactivate(UUID id);

    boolean activate(UUID id);

    List<ProductStatistics> getProductsStatistics();

    Optional<ProductStatisticsDetails> getProductStatisticsDetails(UUID productId);
}
