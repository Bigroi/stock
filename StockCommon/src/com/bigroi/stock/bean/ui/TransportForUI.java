package com.bigroi.stock.bean.ui;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Id;

public class TransportForUI {
	
	@Id
	private long id;
	
	@Column(value = "label.transport.volume")
	private int volume;
	
	@Column(value = "label.transport.maxTransportPrice")
	private double maxTransportPrice;
	
	@Column(value = "label.transport.name")
	private String name;
	
	@Column(value = "label.transport.addressBuyer")
	private String buyerAddress;
	
	private double  buyerlatitude;
	private double buyerlongitude;
	
	@Column(value = "label.transport.addressSeller")
	private String sellerAddress;
	
	private double  sellerlatitude;
	private double sellerLongitude;
	
	public TransportForUI(Deal deal){
		this.id = deal.getId();
		this.volume = deal.getVolume();
		this.maxTransportPrice = deal.getMaxTransportPrice();
		this.name = deal.getProduct().getName();
		this.buyerAddress = deal.getBuyerAddress().getCountry() +" "+ deal.getBuyerAddress().getCity() +" "+ deal.getBuyerAddress().getAddress();
		this.buyerlatitude =  deal.getBuyerAddress().getLatitude();
		this.buyerlongitude = deal.getBuyerAddress().getLongitude();
		this.sellerAddress = deal.getSellerAddress().getCountry() +" "+ deal.getSellerAddress().getCity() +" "+ deal.getSellerAddress().getAddress();
		this.sellerlatitude = deal.getSellerAddress().getLatitude();
		this.sellerLongitude = deal.getSellerAddress().getLongitude();
	}

	public double getBuyerlatitude() {
		return buyerlatitude;
	}

	public void setBuyerlatitude(double buyerlatitude) {
		this.buyerlatitude = buyerlatitude;
	}

	public double getBuyerlongitude() {
		return buyerlongitude;
	}

	public void setBuyerlongitude(double buyerlongitude) {
		this.buyerlongitude = buyerlongitude;
	}

	public double getSellerlatitude() {
		return sellerlatitude;
	}

	public void setSellerlatitude(double sellerlatitude) {
		this.sellerlatitude = sellerlatitude;
	}

	public double getSellerLongitude() {
		return sellerLongitude;
	}

	public void setSellerLongitude(double sellerLongitude) {
		this.sellerLongitude = sellerLongitude;
	}
}