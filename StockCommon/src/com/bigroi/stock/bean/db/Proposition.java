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
	private CompanyAddress buyerAddress;
	private CompanyAddress sellerAaddress;
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
	public CompanyAddress getBuyerAddress() {
		return buyerAddress;
	}
	public void setBuyerAddress(CompanyAddress buyerAddress) {
		this.buyerAddress = buyerAddress;
	}
	public CompanyAddress getSellerAaddress() {
		return sellerAaddress;
	}
	public void setSellerAaddress(CompanyAddress sellerAaddress) {
		this.sellerAaddress = sellerAaddress;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	
	public void setDealPrice(double dealPrice) {
		this.dealPrice = dealPrice;
	}
	
	public void setDealVolume(int dealVolume) {
		this.dealVolume = dealVolume;
	}
	
	public void setCompany(Company company) {
		this.company = company;
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
