package com.bigroi.stock.bean.db;

import java.util.Date;

import com.bigroi.stock.bean.common.PartnerChoice;

public class Deal {
	
	// DB fields
	private long id;
	private long productId;
	private long categoryId;
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
	private String productName;
	private String categoryName;
	
	private String buyerCountry;
	private String buyerCity;
	private String buyerAddress;
	private double buyerLongitude;
	private double buyerLatitude;
	
	private long buyerCompanyId;
	private String buyerCompanyName;
	private String buyerPhone;
	private String buyerRegNumber;
	
	private String sellerCountry;
	private String sellerCity;
	private String sellerAddress;
	private double sellerLongitude;
	private double sellerLatitude;
	
	private long sellerCompanyId;
	private String sellerCompanyName;
	private String sellerPhone;
	private String sellerRegNumber;
	
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
	
	public long getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBuyerCountry() {
		return buyerCountry;
	}

	public void setBuyerCountry(String buyerCountry) {
		this.buyerCountry = buyerCountry;
	}

	public String getBuyerCity() {
		return buyerCity;
	}

	public void setBuyerCity(String buyerCity) {
		this.buyerCity = buyerCity;
	}

	public String getBuyerAddress() {
		return buyerAddress;
	}

	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}

	public String getSellerCountry() {
		return sellerCountry;
	}

	public void setSellerCountry(String sellerCountry) {
		this.sellerCountry = sellerCountry;
	}

	public String getSellerCity() {
		return sellerCity;
	}

	public void setSellerCity(String sellerCity) {
		this.sellerCity = sellerCity;
	}

	public String getSellerAddress() {
		return sellerAddress;
	}

	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	public double getBuyerLongitude() {
		return buyerLongitude;
	}

	public void setBuyerLongitude(double buyerLongitude) {
		this.buyerLongitude = buyerLongitude;
	}

	public double getBuyerLatitude() {
		return buyerLatitude;
	}

	public void setBuyerLatitude(double buyerLatitude) {
		this.buyerLatitude = buyerLatitude;
	}

	public double getSellerLongitude() {
		return sellerLongitude;
	}

	public void setSellerLongitude(double sellerLongitude) {
		this.sellerLongitude = sellerLongitude;
	}

	public double getSellerLatitude() {
		return sellerLatitude;
	}

	public void setSellerLatitude(double sellerLatitude) {
		this.sellerLatitude = sellerLatitude;
	}

	public String getBuyerCompanyName() {
		return buyerCompanyName;
	}

	public void setBuyerCompanyName(String buyerCompanyName) {
		this.buyerCompanyName = buyerCompanyName;
	}

	public String getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}

	public String getBuyerRegNumber() {
		return buyerRegNumber;
	}

	public void setBuyerRegNumber(String buyerRegNumber) {
		this.buyerRegNumber = buyerRegNumber;
	}

	public String getSellerCompanyName() {
		return sellerCompanyName;
	}

	public void setSellerCompanyName(String sellerCompanyName) {
		this.sellerCompanyName = sellerCompanyName;
	}

	public String getSellerPhone() {
		return sellerPhone;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	public String getSellerRegNumber() {
		return sellerRegNumber;
	}

	public void setSellerRegNumber(String sellerRegNumber) {
		this.sellerRegNumber = sellerRegNumber;
	}

	public long getBuyerCompanyId() {
		return buyerCompanyId;
	}

	public void setBuyerCompanyId(long buyerCompanyId) {
		this.buyerCompanyId = buyerCompanyId;
	}

	public long getSellerCompanyId() {
		return sellerCompanyId;
	}

	public void setSellerCompanyId(long sellerCompanyId) {
		this.sellerCompanyId = sellerCompanyId;
	}
	
}
