package com.bigroi.stock.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PreDeal {
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
	private static final String YES = "Y";
	private static final String NO = "N";

	private long id;
	private String sellerHashCode;// TODO: fields
									// sellerHashCode,customerHashCode,sellerApprov,custApprov,dealDate
									// in DB ???
	private String customerHashCode;
	private long tenderId;
	private long lotId;
	private boolean sellerApprov;
	private boolean custApprov;
	private Date dealDate = new Date();

	public String getSellerApprov() {
		if (this.sellerApprov) {
			return YES;
		}
		return NO;
	}

	public void setSellerApprov(String sellerApprov) {
		if ("Y".equals(sellerApprov.toUpperCase())) {
			this.sellerApprov = true;
		} else {
			this.sellerApprov = false;
		}
	}

	public String getCustApprov() {
		if (this.custApprov) {
			return YES;
		}
		return NO;
	}

	public void setCustApprov(String custApprov) {
		if ("Y".equals(custApprov.toUpperCase())) {
			this.custApprov = true;
		} else {
			this.custApprov = false;
		}
	}

	public boolean getSellerApprovBool() {
		return sellerApprov;
	}

	public void setSellerApprovBool(boolean sellerApprov) {
		this.sellerApprov = sellerApprov;
	}

	public boolean getCustApprovBool() {
		return custApprov;
	}

	public void setCustApprovBool(boolean custApprov) {
		this.custApprov = custApprov;
	}

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

}
