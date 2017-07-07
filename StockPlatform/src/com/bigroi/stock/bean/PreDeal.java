package com.bigroi.stock.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PreDeal {
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
	
	private long id;
	private String sellerHashCode;
	private String customerHashCode;
	private long tenderId;
	private long lotId;
	private String sallerApprov;
	private String custApprov;
	private Date dealDate = new Date();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSellerHashCode() {
		return sellerHashCode;
	}
	public void setSellerHashCode(String sellerHashCode) {
		this.sellerHashCode = sellerHashCode;
	}
	public String getCustomerHashCode() {
		return customerHashCode;
	}
	public void setCustomerHashCode(String customerHashCode) {
		this.customerHashCode = customerHashCode;
	}
	public long getTenderId() {
		return tenderId;
	}
	public void setTenderId(long tenderId) {
		this.tenderId = tenderId;
	}
	public long getLotId() {
		return lotId;
	}
	public void setLotId(long lotId) {
		this.lotId = lotId;
	}
	public String getSallerApprov() {
		return sallerApprov;
	}
	public void setSallerApprov(String sallerApprov) {
		this.sallerApprov = sallerApprov;
	}
	public String getCustApprov() {
		return custApprov;
	}
	public void setCustApprov(String custApprov) {
		this.custApprov = custApprov;
	}
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	public String getDealDateStr(){
		return FORMATTER.format(dealDate);
	}
	
	public void setDateStr(String dealDateStr) throws ParseException{
		dealDate = FORMATTER.parse(dealDateStr);
	}
	
}

