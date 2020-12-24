package com.stock.service.impl;

import com.stock.dao.LotDao;
import com.stock.dao.ProductCategoryDao;
import com.stock.dao.ProductDao;
import com.stock.dao.TenderDao;
import com.stock.entity.business.ProductRecord;
import com.stock.entity.ui.Product;
import com.stock.service.ProductService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
}
