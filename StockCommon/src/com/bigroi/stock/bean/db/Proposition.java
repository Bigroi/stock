package com.bigroi.stock.bean.db;

import com.bigroi.stock.bean.common.PropositionStatus;

public class Proposition {

	private long id;
	private long dealId;
	private long companyId;
	private int price;
	private PropositionStatus status;
	
	//Related objects
	private Deal deal;
	private Product product;
	private Address address;
	
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
	public PropositionStatus getStatus() {
		return status;
	}
	public void setStatus(PropositionStatus status) {
		this.status = status;
	}
	
	public Deal getDeal() {
		return deal;
	}
	public void setDeal(Deal deal) {
		this.deal = deal;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Proposition [id=" + id + ", dealId=" + dealId + ", companyId=" + companyId + ", price=" + price
				+ ", status=" + status + ", deal=" + deal + ", product=" + product + ", address=" + address + "]";
	}
	
}
