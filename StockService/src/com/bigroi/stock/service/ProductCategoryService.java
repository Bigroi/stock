package com.bigroi.stock.service;

import com.bigroi.stock.bean.db.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    void add(ProductCategory category);

    void merge(ProductCategory category);

    void delete(ProductCategory category);

    List<ProductCategory> getByProductId(long productId);

    ProductCategory getById(long id, long productId);

    List<ProductCategory> getActiveByProductId(long productId);

}
