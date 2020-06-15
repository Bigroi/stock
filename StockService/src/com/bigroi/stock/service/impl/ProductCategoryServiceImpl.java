package com.bigroi.stock.service.impl;

import com.bigroi.stock.bean.db.ProductCategory;
import com.bigroi.stock.dao.ProductCategoryDao;
import com.bigroi.stock.service.ProductCategoryService;

import java.util.List;

public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryDao productCategoryDao;

    public ProductCategoryServiceImpl(ProductCategoryDao productCategoryDao) {
        this.productCategoryDao = productCategoryDao;
    }

    @Override
    public ProductCategory getById(long id, long productId) {
        ProductCategory category;
        if (id == -1) {
            category = new ProductCategory();
            category.setId(id);
            category.setProductId(productId);
        } else {
            category = productCategoryDao.getById(id);
        }
        return category;
    }

    @Override
    public void merge(ProductCategory category) {
        category.setRemoved("N");
        if (category.getId() == -1) {
            productCategoryDao.add(category);
        } else {
            productCategoryDao.update(category);
        }
    }

    @Override
    public void delete(ProductCategory category) {
        category.setRemoved("Y");
        productCategoryDao.delete(category.getId());
    }

    @Override
    public List<ProductCategory> getByProductId(long productId) {
        return productCategoryDao.getByProductId(productId);
    }

    @Override
    public List<ProductCategory> getActiveByProductId(long productId) {
        return productCategoryDao.getActiveByProductId(productId);
    }

    @Override
    public void add(ProductCategory category) {
        productCategoryDao.add(category);
    }

}
