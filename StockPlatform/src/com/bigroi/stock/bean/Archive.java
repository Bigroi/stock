package com.bigroi.stock.bean;

import java.util.Date;

public class Archive {

	private long id;
	private long salerId;
	private long customerId;
	private long productId;
	private double price;
	private Date tmsTmp;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSalerId() {
		return salerId;
	}

	public void setSalerId(long salerId) {
		this.salerId = salerId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getTmsTmp() {
		return tmsTmp;
	}

	public void setTmsTmp(Date tmsTmp) {
		this.tmsTmp = tmsTmp;
	}

	@Override
	public String toString() {
		return "Archive [id=" + id + ", salerId=" + salerId + ", customerId=" + customerId + ", productId=" + productId
				+ ", price=" + price + ", tmsTmp=" + tmsTmp + "]";
	}
}
