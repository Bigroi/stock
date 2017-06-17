package com.bigroi.stock.bean;

public class Application {

	private long id;
	private String description;
	private long product;
	private double maxPrice;
	private long customer;

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

	public long getProduct() {
		return product;
	}

	public void setProduct(long product) {
		this.product = product;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public long getCustomer() {
		return customer;
	}

	public void setCustomer(long customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Application [id=" + id + ", description=" + description + ", product=" + product + ", maxPrice="
				+ maxPrice + ", customer=" + customer + "]";
	}

}
