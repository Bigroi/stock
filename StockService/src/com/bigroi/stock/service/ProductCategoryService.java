package com.bigroi.stock.service;

import java.util.List;

import com.bigroi.stock.bean.db.ProductCategory;

public interface ProductCategoryService {

void add(ProductCategory category);
	
	void merge(ProductCategory category);
	
	void delete(ProductCategory category);
	
	List<ProductCategory> getByProductId(long productId);

	ProductCategory getById(long id, long productId);
	
}
