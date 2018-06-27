package com.bigroi.stock.bean.ui;

import com.bigroi.stock.bean.db.Proposition;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Id;

public class PropositionForUI {
	
	@Id
	private long id;
	
	@Column(value = "label.transport.price")
	private int price;
	
	@Column(value = "label.transport.volume")
	private int volume;
	
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
	
	public PropositionForUI(Proposition prop){
		this.id = prop.getId();
		this.price = prop.getPrice();
		this.name = prop.getProduct().getName();
		this.buyerAddress = prop.getAddress().getCountry() +" "+ prop.getAddress().getCity() +" "+ prop.getAddress().getAddress();
		this.buyerlatitude =  prop.getAddress().getLatitude();
		this.buyerlongitude = prop.getAddress().getLongitude();
		this.sellerAddress = prop.getAddress().getCountry() +" "+ prop.getAddress().getCity() +" "+ prop.getAddress().getAddress();
		this.sellerlatitude = prop.getAddress().getLatitude();
		this.sellerLongitude = prop.getAddress().getLongitude();
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
