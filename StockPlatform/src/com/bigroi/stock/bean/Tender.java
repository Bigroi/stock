package com.bigroi.stock.bean;

public class Tender {

	private long id;
	private String description;
	private long productId;
	private double maxPrice;
	private long customerId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "Tender [id=" + id + ", description=" + description + ", product=" + productId + ", maxPrice="
				+ maxPrice + ", customer=" + customerId + "]";
	}

}
