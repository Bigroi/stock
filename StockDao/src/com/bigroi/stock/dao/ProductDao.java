package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.ui.ProductForUI;

public interface ProductDao {
	
	List<Product> getAllProducts();
	
	List<Product> getAllActiveProducts();
	
	List<ProductForUI> getAllActiveProductsForUI();
	
	void add(Product product);
	
	boolean update(Product product);
	
	Product getById(long id);
	
}
