package com.bigroi.stock.bean.db;

public class ProductCategory {

	private long id;
	
	private long productId;
	
	private String categoryName;
	
	private String removed;

	public ProductCategory() {
	}
	
	public ProductCategory(long id, String categoryName) {
		this.id = id;
		this.categoryName = categoryName;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getRemoved() {
		return removed;
	}

	public void setRemoved(String removed) {
		this.removed = removed;
	}
	
}
