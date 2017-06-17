package com.bigroi.stock.bean;

import java.util.Date;

public class Lot {
	
	private long id;
	private String description;
	private long poduct;
	private double min_price;
	private long saler;
	private Date exp_date;

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

	public long getPoduct() {
		return poduct;
	}

	public void setPoduct(long poduct) {
		this.poduct = poduct;
	}

	public double getMin_price() {
		return min_price;
	}

	public void setMin_price(double min_price) {
		this.min_price = min_price;
	}

	public long getSaler() {
		return saler;
	}

	public void setSaler(long saler) {
		this.saler = saler;
	}

	public Date getExp_date() {
		return exp_date;
	}

	public void setExp_date(Date exp_date) {
		this.exp_date = exp_date;
	}

	@Override
	public String toString() {
		return "Lot [id=" + id + ", description=" + description + ", poduct=" + poduct + ", min_price=" + min_price
				+ ", saler=" + saler + ", exp_date=" + exp_date + "]";
	}

}
