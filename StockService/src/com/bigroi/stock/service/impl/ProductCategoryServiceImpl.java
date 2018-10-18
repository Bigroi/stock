package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.ProductCategory;
import com.bigroi.stock.dao.ProductCategoryDao;
import com.bigroi.stock.service.ProductCategoryService;

@Repository
public class ProductCategoryServiceImpl implements ProductCategoryService{

	@Autowired
	private ProductCategoryDao productCategoryDao;
	
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
		if(category.getId() == -1){
			productCategoryDao.add(category);
		}else{
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
	public void add(ProductCategory category) {
		productCategoryDao.add(category);
	}

}
