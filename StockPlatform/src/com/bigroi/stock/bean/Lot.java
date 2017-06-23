package com.bigroi.stock.bean;

import java.util.Date;

public class Lot {
	
	private long id;
	private String description;
	private long poductId;
	private double min_price;
	private long salerId;
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

	public double getMin_price() {
		return min_price;
	}

	public void setMin_price(double min_price) {
		this.min_price = min_price;
	}

	public Date getExp_date() {
		return exp_date;
	}

	public void setExp_date(Date exp_date) {
		this.exp_date = exp_date;
	}

	public long getPoductId() {
		return poductId;
	}

	public void setPoductId(long poductId) {
		this.poductId = poductId;
	}

	public long getSalerId() {
		return salerId;
	}

	public void setSalerId(long salerId) {
		this.salerId = salerId;
	}

	@Override
	public String toString() {
		return "Lot [id=" + id + ", description=" + description + ", poduct=" + poductId + ", min_price=" + min_price
				+ ", saler=" + salerId + ", exp_date=" + exp_date + "]";
	}

}
