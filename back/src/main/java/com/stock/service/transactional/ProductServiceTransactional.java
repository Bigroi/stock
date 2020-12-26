package com.stock.service.transactional;

import com.stock.dao.Transactional;
import com.stock.entity.business.ProductRecord;
import com.stock.entity.ui.Product;
import com.stock.entity.ui.ProductStatistics;
import com.stock.entity.ui.ProductStatisticsDetails;
import com.stock.service.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductServiceTransactional implements ProductService {

    private final Transactional transactional;
    private final ProductService service;

    public ProductServiceTransactional(Transactional transactional, ProductService service) {
        this.transactional = transactional;
        this.service = service;
    }

    @Override
    public List<Product> getActiveProducts() {
        return service.getActiveProducts();
    }

    @Override
    public List<ProductRecord> getProductsAsAdmin() {
        return service.getProductsAsAdmin();
    }

    @Override
    public UUID create(ProductRecord record) {
        return service.create(record);
    }

    @Override
    public boolean deactivate(UUID id) {
        return transactional.inTransaction(() -> service.deactivate(id));
    }

    @Override
    public boolean activate(UUID id) {
        return service.activate(id);
    }

    @Override
    public List<ProductStatistics> getProductsStatistics() {
        return service.getProductsStatistics();
    }

    @Override
    public Optional<ProductStatisticsDetails> getProductStatisticsDetails(UUID productId) {
        return service.getProductStatisticsDetails(productId);
    }
}
