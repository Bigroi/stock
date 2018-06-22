package com.bigroi.stock.bean.db;

public class Product {
	
	private long id;
	
	private String name;
	
	private String description;
	
	private String removed;
	
	private double delivaryPrice;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getDelivaryPrice() {
		return delivaryPrice;
	}
	
	public void setDelivaryPrice(double delivaryPrice) {
		this.delivaryPrice = delivaryPrice;
	}
	
	public String getRemoved() {
		return removed;
	}
	
	public void setRemoved(String removed) {
		this.removed = removed;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", removed=" + removed
				+ ", delivaryPrice=" + delivaryPrice + "]";
	}
	
}
