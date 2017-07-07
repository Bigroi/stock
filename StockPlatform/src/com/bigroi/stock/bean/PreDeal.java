package com.bigroi.stock.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PreDeal {
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
	private static final String YES = "Y";
	private static final String NO = "N";

	private long id;
	private String sellerHashCode;
	private String customerHashCode;
	private long tenderId;
	private long lotId;
	private boolean sallerApprov;
	private boolean custApprov;
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

	public boolean getSallerApprov() {
		return sallerApprov;
	}

	public void setSallerApprov(boolean sallerApprov) {
		this.sallerApprov = sallerApprov;
	}

	public boolean getCustApprov() {
		return custApprov;
	}

	public void setCustApprov(boolean custApprov) {
		this.custApprov = custApprov;
	}

	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	public String getDealDateStr() {
		return FORMATTER.format(dealDate);
	}

	public void setDateStr(String dealDateStr) throws ParseException {
		dealDate = FORMATTER.parse(dealDateStr);
	}

	public boolean checkSallerApprov() {
		if (YES.toUpperCase().equals(sallerApprov)&& (NO.toUpperCase().equals(sallerApprov))){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkCustApprov() {
		if (YES.toUpperCase().equals(custApprov) && (NO.toUpperCase().equals(custApprov))){
			return true;
		} else {
			return false;
		}
	}

}
