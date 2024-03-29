package com.stock.service;

import com.stock.entity.business.ProductCategoryRecord;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<ProductCategoryRecord> getCategories(UUID productId);

    UUID create(ProductCategoryRecord record, UUID productId);

    boolean deactivate(UUID productId, UUID id);

    boolean activate(UUID productId, UUID id);
}
