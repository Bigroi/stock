package com.stock.service.transactional;

import com.stock.dao.Transactional;
import com.stock.entity.business.ProductCategoryRecord;
import com.stock.entity.business.ProductRecord;
import com.stock.entity.ui.Product;
import com.stock.service.CategoryService;
import com.stock.service.ProductService;

import java.util.List;
import java.util.UUID;

public class CategoryServiceTransactional implements CategoryService {

    private final Transactional transactional;
    private final CategoryService service;

    public CategoryServiceTransactional(Transactional transactional, CategoryService service) {
        this.transactional = transactional;
        this.service = service;
    }

    @Override
    public List<ProductCategoryRecord> getCategories(UUID productId) {
        return service.getCategories(productId);
    }

    @Override
    public UUID create(ProductCategoryRecord record, UUID productId) {
        return service.create(record, productId);
    }

    @Override
    public boolean deactivate(UUID id, UUID productId) {
        return transactional.inTransaction(() -> service.deactivate(id, productId));
    }

    @Override
    public boolean activate(UUID id, UUID productId) {
        return transactional.inTransaction(() -> service.activate(id, productId));
    }
}
