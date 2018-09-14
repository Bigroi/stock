package com.bigroi.stock.bean.db;

public class Product {
	
	private long id;
	
	private String name;
	
	private String removed;
	
	private double delivaryPrice;
	
	private String picture;
	
	public String getPicture() {
		return picture;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
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
		return "Product [id=" + id + ", name=" + name + ", removed=" + removed + ", delivaryPrice=" + delivaryPrice
				+ ", picture=" + picture + "]";
	}
}
