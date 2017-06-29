package com.bigroi.stock.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Deals {
	
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");

	private long id;
	private long salerId;
	private long customerId;
	private long productId;
	private double price;
	private Date tmsTmp = new Date();
	private String sellerApprov;
	private String custApprov;
	
	public String getDateStr(){
		return FORMATTER.format(tmsTmp);
	}
	
	public void setDateStr(String dateStr) throws ParseException{
		tmsTmp = FORMATTER.parse(dateStr);
	}
	
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

	public String getSellerApprov() {
		return sellerApprov;
	}

	public void setSellerApprov(String sellerApprov) {
		this.sellerApprov = sellerApprov;
	}

	public String getCustApprov() {
		return custApprov;
	}

	public void setCustApprov(String custApprov) {
		this.custApprov = custApprov;
	}

	@Override
	public String toString() {
		return "Deals [id=" + id + ", salerId=" + salerId + ", customerId=" + customerId + ", productId=" + productId
				+ ", price=" + price + ", tmsTmp=" + tmsTmp + ", sellerApprov=" + sellerApprov + ", custApprov="
				+ custApprov + "]";
	}

	
}