package com.bigroi.stock.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bigroi.stock.bean.common.Status;

public class Lot {
	
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
	
	private long id;
	private String description;
	private long poductId;
	private double minPrice;
	private long salerId;
	private Date expDate = new Date();
	private Status status;
	
	public String getDateStr(){
		return FORMATTER.format(expDate);
	}
	
	public void setDateStr(String dateStr) throws ParseException{
		expDate = FORMATTER.parse(dateStr);
	}

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

	public long getPoductId() {
		return poductId;
	}

	public void setPoductId(long poductId) {
		this.poductId = poductId;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public long getSalerId() {
		return salerId;
	}

	public void setSalerId(long salerId) {
		this.salerId = salerId;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Lot [id=" + id + ", description=" + description + ", poductId=" + poductId + ", minPrice=" + minPrice
				+ ", salerId=" + salerId + ", expDate=" + expDate + ", status=" + status + "]";
	}

	
	
}
