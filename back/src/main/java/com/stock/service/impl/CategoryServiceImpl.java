package com.stock.service.impl;

import com.stock.dao.LotDao;
import com.stock.dao.ProductCategoryDao;
import com.stock.dao.ProductDao;
import com.stock.dao.TenderDao;
import com.stock.entity.business.ProductCategoryRecord;
import com.stock.service.CategoryService;

import java.util.List;
import java.util.UUID;

public class CategoryServiceImpl implements CategoryService {

    private final ProductCategoryDao productCategoryDao;
    private final ProductDao productDao;
    private final LotDao lotDao;
    private final TenderDao tenderDao;

    public CategoryServiceImpl(
            ProductCategoryDao productCategoryDao,
            ProductDao productDao,
            LotDao lotDao,
            TenderDao tenderDao
    ) {
        this.productCategoryDao = productCategoryDao;
        this.lotDao = lotDao;
        this.tenderDao = tenderDao;
        this.productDao = productDao;
    }

    @Override
    public List<ProductCategoryRecord> getCategories(UUID productId) {
        return productCategoryDao.getByProductId(productId);
    }

    @Override
    public UUID create(ProductCategoryRecord record, UUID productId) {
        record.setId(UUID.randomUUID());
        record.setRemoved(false);
        record.setProductId(productId);
        productCategoryDao.create(record);
        return record.getId();
    }

    @Override
    public boolean deactivate(UUID productId, UUID id) {
        if (productCategoryDao.setRemovedByIdAndProductId(id, productId, true)) {
            lotDao.deleteByCategoryId(id);
            tenderDao.deleteByCategoryId(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean activate(UUID productId, UUID id) {
        if (productCategoryDao.setRemovedByIdAndProductId(id, productId, false)){
            productDao.setRemovedById(productId, false);
            return true;
        } else {
            return false;
        }
    }
}
