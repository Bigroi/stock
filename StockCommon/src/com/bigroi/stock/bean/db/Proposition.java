package com.bigroi.stock.bean.db;

public class Proposition {

	private long id;
	private long dealId;
	private long companyId;
	private int price;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDealId() {
		return dealId;
	}
	public void setDealId(long dealId) {
		this.dealId = dealId;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "TransportProposition [id=" + id + ", dealId=" + dealId + ", companyId=" + companyId + ", price=" + price
				+ "]";
	}
}
