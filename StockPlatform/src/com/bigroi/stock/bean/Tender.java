package com.bigroi.stock.bean;

import java.util.Date;

public class Tender {

	private long id;
	private String description;
	private long productId;
	private double maxPrice;
	private long customerId;
	private byte status;
	private Date expDate;
	
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
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public double getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public Date getExpDate() {
		return expDate;
	}
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
	@Override
	public String toString() {
		return "Tender [id=" + id + ", description=" + description + ", productId=" + productId + ", maxPrice="
				+ maxPrice + ", customerId=" + customerId + ", status=" + status + ", expDate=" + expDate + "]";
	}

	

}
