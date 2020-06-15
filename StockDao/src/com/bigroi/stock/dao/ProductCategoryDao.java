package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.db.ProductCategory;

public interface ProductCategoryDao {

    void add(ProductCategory category);

    boolean update(ProductCategory category);

    boolean delete(long id);

    ProductCategory getById(long id);

    List<ProductCategory> getByProductId(long productId);

    List<ProductCategory> getActiveByProductId(long productId);
}
