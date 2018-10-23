package com.bigroi.stock.bean.db;

import java.util.Date;

import com.bigroi.stock.bean.common.PartnerChoice;

public class Deal {
	
	// DB fields
	private long id;
	private long productId;
	private Date time;
	
	private Long lotId;
	private Long tenderId;
	
	private double price;
	private int volume;
	private String sellerFoto;
	private double maxTransportPrice;
	
	private PartnerChoice buyerChoice;
	private PartnerChoice sellerChoice;
	
	private long buyerAddressId;
	private long sellerAddressId;
	
	private String buyerDescription;
	private String sellerDescription;
	
	private String buyerPackaging;
	private String buyerProcessing;
	
	private String buyerLanguage;
	private String sellerLanguage;
	
	private String buyerEmail;
	private String sellerEmail;
	
	//Related objects
	
	private Product product;
	private CompanyAddress buyerAddress;
	private CompanyAddress sellerAddress;
	
	public Deal(TradeLot lot, TradeTender tender, int volume, double maxTransportPrice) {
		this.lotId = lot.getId();
		this.buyerChoice = PartnerChoice.ON_APPROVE;
		this.price = (lot.getPrice() + tender.getPrice()) / 2;
		this.sellerChoice = PartnerChoice.ON_APPROVE;
		this.tenderId = tender.getId();
		this.time = new Date();
		this.volume = volume;
		this.maxTransportPrice = maxTransportPrice;
		this.buyerLanguage = tender.getLanguage();
		this.sellerLanguage = lot.getLanguage();
		this.buyerEmail = tender.getEmail();
		this.sellerEmail = lot.getEmail();
		this.buyerAddressId = tender.getAddressId();
		this.sellerAddressId = lot.getAddressId();
	}
	
	public Deal() {
	}
	
	public String getBuyerEmail() {
		return buyerEmail;
	}
	
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	
	public String getSellerEmail() {
		return sellerEmail;
	}
	
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}
	
	public String getBuyerLanguage() {
		return buyerLanguage;
	}
	
	public void setBuyerLanguage(String buyerLanguage) {
		this.buyerLanguage = buyerLanguage;
	}
	
	public String getSellerLanguage() {
		return sellerLanguage;
	}
	
	public void setSellerLanguage(String sellerLanguage) {
		this.sellerLanguage = sellerLanguage;
	}
	
	public String getBuyerPackaging() {
		return buyerPackaging;
	}
	
	public void setBuyerPackaging(String buyerPackaging) {
		this.buyerPackaging = buyerPackaging;
	}
	
	public String getBuyerProcessing() {
		return buyerProcessing;
	}
	
	public void setBuyerProcessing(String buyerProcessing) {
		this.buyerProcessing = buyerProcessing;
	}
	
	public double getMaxTransportPrice() {
		return maxTransportPrice;
	}
	
	public void setMaxTransportPrice(double maxTransportPrice) {
		this.maxTransportPrice = maxTransportPrice;
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

	public CompanyAddress getSellerAddress() {
		return sellerAddress;
	}

	public void setSellerAddress(CompanyAddress sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Long getLotId() {
		return lotId;
	}

	public void setLotId(Long lotId) {
		this.lotId = lotId;
	}

	public Long getTenderId() {
		return tenderId;
	}

	public void setTenderId(Long tenderId) {
		this.tenderId = tenderId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public String getSellerFoto() {
		return sellerFoto;
	}

	public void setSellerFoto(String sellerFoto) {
		this.sellerFoto = sellerFoto;
	}

	public PartnerChoice getBuyerChoice() {
		return buyerChoice;
	}
	
	public void setBuyerChoice(PartnerChoice buyerChoice) {
		this.buyerChoice = buyerChoice;
	}
	
	public PartnerChoice getSellerChoice() {
		return sellerChoice;
	}
	
	public void setSellerChoice(PartnerChoice sellerChoice) {
		this.sellerChoice = sellerChoice;
	}

	public long getBuyerAddressId() {
		return buyerAddressId;
	}

	public void setBuyerAddressId(long buyerAddressId) {
		this.buyerAddressId = buyerAddressId;
	}

	public long getSellerAddressId() {
		return sellerAddressId;
	}

	public void setSellerAddressId(long sellerAddressId) {
		this.sellerAddressId = sellerAddressId;
	}

	public String getBuyerDescription() {
		return buyerDescription;
	}

	public void setBuyerDescription(String buyerDescription) {
		this.buyerDescription = buyerDescription;
	}

	public String getSellerDescription() {
		return sellerDescription;
	}

	public void setSellerDescription(String sellerDescription) {
		this.sellerDescription = sellerDescription;
	}

	@Override
	public String toString() {
		return "Deal [id=" + id + ", productId=" + productId + ", time=" + time + ", lotId=" + lotId + ", tenderId="
				+ tenderId + ", price=" + price + ", volume=" + volume + ", sellerFoto=" + sellerFoto
				+ ", maxTransportPrice=" + maxTransportPrice + ", buyerChoice=" + buyerChoice + ", sellerChoice="
				+ sellerChoice + ", buyerAddressId=" + buyerAddressId + ", sellerAddressId=" + sellerAddressId
				+ ", buyerDescription=" + buyerDescription + ", sellerDescription=" + sellerDescription + ", product="
				+ product + ",  buyerAddress=" + buyerAddress + ", sellerAddress=" + sellerAddress
				+ "]";
	}

	
}
