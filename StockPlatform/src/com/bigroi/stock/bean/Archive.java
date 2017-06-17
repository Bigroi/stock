package com.bigroi.stock.bean;

import java.util.Date;

public class Archive {

	private long id;
	private long saler;
	private long customer;
	private long product;
	private double price;
	private Date tmsTmp;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSaler() {
		return saler;
	}
	public void setSaler(long saler) {
		this.saler = saler;
	}
	public long getCustomer() {
		return customer;
	}
	public void setCustomer(long customer) {
		this.customer = customer;
	}
	public long getProduct() {
		return product;
	}
	public void setProduct(long product) {
		this.product = product;
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
		return "Archive [id=" + id + ", saler=" + saler + ", customer=" + customer + ", product=" + product + ", price="
				+ price + ", tmsTmp=" + tmsTmp + "]";
	}
	
	
}
