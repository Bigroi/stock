package com.stock.entity.ui;

import com.stock.entity.business.ProductCategoryRecord;

import java.util.UUID;

public class ProductCategory {

    private final UUID id;
    private final String name;

    public ProductCategory(ProductCategoryRecord record) {
        this.id = record.getId();
        this.name = record.getCategoryName();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
