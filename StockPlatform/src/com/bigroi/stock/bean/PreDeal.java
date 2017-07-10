package com.bigroi.stock.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PreDeal {
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
	public final boolean YES = Boolean.parseBoolean("Y");
	public final static boolean NO = Boolean.parseBoolean("N");;

	private long id;
	private String sellerHashCode;//TODO: fields sellerHashCode,customerHashCode,sellerApprov,custApprov,dealDate in DB ???
	private String customerHashCode;
	private long tenderId;
	private long lotId;
	private boolean sellerApprov;
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
		return sellerApprov;
	}

	public void setSallerApprov(boolean sallerApprov) {
		this.sellerApprov = sallerApprov;
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

	public  boolean checkSallerApprov( boolean yes) {
		if (yes == true ){//TODO: how convert boolean to String and insert into DB????
			return YES;
		} else {
			return  false;
		}
		
	}
	
	/*public boolean checkCustApprov() {
		if (sellerApprov == true){
			return YES;
		} else {
			return NO;
		}
	}*/

}
