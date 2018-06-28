package com.bigroi.stock.bean.db;

import com.bigroi.stock.bean.common.PropositionStatus;

public class Proposition {

	private long id;
	private long dealId;
	private long companyId;
	private double price;
	private PropositionStatus status;
	
	//Related objects
	private Deal deal;
	private Product product;
	private Address buyerAddress;
	private Address sellerAaddress;
	
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
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
	public Address getBuyerAddress() {
		return buyerAddress;
	}
	public void setBuyerAddress(Address buyerAddress) {
		this.buyerAddress = buyerAddress;
	}
	public Address getSellerAaddress() {
		return sellerAaddress;
	}
	public void setSellerAaddress(Address sellerAaddress) {
		this.sellerAaddress = sellerAaddress;
	}
	@Override
	public String toString() {
		return "Proposition [id=" + id + ", dealId=" + dealId + ", companyId=" + companyId + ", price=" + price
				+ ", status=" + status + ", deal=" + deal + ", product=" + product + ", buyerAddress=" + buyerAddress
				+ ", sellerAaddress=" + sellerAaddress + "]";
	}
}
