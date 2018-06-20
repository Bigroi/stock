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
	
	//Related objects
	
	private Product product;
	private Address buyerAddress;
	private Address sellerAddress;
	
	public Deal(Lot lot, Tender tender, int volume, double maxTransportPrice) {
		this.lotId = lot.getId();
		this.buyerAddress = tender.getAddress();
		this.buyerAddressId = tender.getAddressId();
		this.buyerChoice = PartnerChoice.ON_APPROVE;
		this.buyerDescription = tender.getDescription();
		this.price = (lot.getMinPrice() + tender.getMaxPrice()) / 2;
		this.productId = lot.getProductId();
		this.sellerAddress = lot.getAddress();
		this.sellerAddressId = lot.getAddressId();
		this.sellerChoice = PartnerChoice.ON_APPROVE;
		this.sellerDescription = lot.getDescription();
		this.sellerFoto = lot.getFoto();
		this.tenderId = tender.getId();
		this.time = new Date();
		this.volume = volume;
		this.maxTransportPrice = maxTransportPrice;
	}
	
	public Deal() {
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

	public Address getBuyerAddress() {
		return buyerAddress;
	}

	public void setBuyerAddress(Address buyerAddress) {
		this.buyerAddress = buyerAddress;
	}

	public Address getSellerAddress() {
		return sellerAddress;
	}

	public void setSellerAddress(Address sellerAddress) {
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
	
}
