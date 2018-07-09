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
	private Company company;
	private Product product;
	private Address buyerAddress;
	private Address sellerAaddress;
	private String companyName;
	private String companyPhone;
	private int dealVolume;
	private double dealPrice;
	
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
	public Company getCompany() {
		return company;
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
	public void setDealPriceAndVolume(Deal deal) {
		this.dealVolume = deal.getVolume();
		this.dealPrice = deal.getPrice();
	}
	public void setCompanyNameAndPhone(Company company) {
		this.companyName = company.getName();
		this.companyPhone = company.getPhone();
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getCompanyPhone() {
		return companyPhone;
	}
	public int getDealVolume() {
		return dealVolume;
	}
	public double getDealPrice() {
		return dealPrice;
	}
	@Override
	public String toString() {
		return "Proposition [id=" + id + ", dealId=" + dealId + ", companyId=" + companyId + ", price=" + price
				+ ", status=" + status + ", deal=" + deal + ", company=" + company + ", product=" + product
				+ ", buyerAddress=" + buyerAddress + ", sellerAaddress=" + sellerAaddress + ", companyName="
				+ companyName + ", companyPhone=" + companyPhone + ", dealVolume=" + dealVolume + ", dealPrice="
				+ dealPrice + "]";
	}
}
