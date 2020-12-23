package com.stock.service.impl;

import com.stock.dao.ProductDao;
import com.stock.entity.ui.Product;
import com.stock.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getActiveProducts() {
        return productDao.getActiveProductWithCategories().entrySet()
                .stream()
                .map(e -> new Product(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

}
