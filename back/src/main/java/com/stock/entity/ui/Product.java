package com.stock.entity.ui;

import com.stock.entity.business.ProductCategoryRecord;
import com.stock.entity.business.ProductRecord;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Product {

    private final UUID id;
    private final String name;
    private final List<ProductCategory> categories;

    public Product(ProductRecord record, List<ProductCategoryRecord> categories) {
        this.id = record.getId();
        this.name = record.getName();
        this.categories = categories.stream()
                .map(ProductCategory::new)
                .collect(Collectors.toList());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ProductCategory> getCategories() {
        return categories;
    }
}
