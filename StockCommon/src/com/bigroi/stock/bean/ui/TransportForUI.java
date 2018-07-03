package com.bigroi.stock.bean.ui;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.Proposition;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.Id;

public class TransportForUI {
	
	@Id
	private final long id;
	
	@Column(value = "label.transport.volume", responsivePriority=-4)
	private final int volume;
	
	@Column(value = "label.transport.price", responsivePriority=-3)
	private final double price;
	
	@Column(value = "label.transport.name", responsivePriority=-5)
	private final String name;
	
	@Column(value = "label.transport.addressBuyer", responsivePriority=-2)
	private final String buyerAddress;

	@Column(value = "label.transport.addressSeller", responsivePriority=-1)
	private final String sellerAddress;
	
	@Column(value = "label.button.delete", responsivePriority=-5)
	@Edit(remove="/proposition/json/Delete.spr", edit = "")
	private final String edit = "YYN";
	
	private final double  buyerLatitude;
	private final double buyerLongitude;

	private final double  sellerLatitude;
	private final double sellerLongitude;
	
	private long dealId;

	public TransportForUI(Deal deal){
		this.id = deal.getId();
		this.volume = deal.getVolume();
		this.price = deal.getMaxTransportPrice();
		this.name = deal.getProduct().getName();
		this.buyerAddress = deal.getBuyerAddress().getCountry() +" "+ deal.getBuyerAddress().getCity() +" "+ deal.getBuyerAddress().getAddress();
		this.buyerLatitude =  deal.getBuyerAddress().getLatitude();
		this.buyerLongitude = deal.getBuyerAddress().getLongitude();
		this.sellerAddress = deal.getSellerAddress().getCountry() +" "+ deal.getSellerAddress().getCity() +" "+ deal.getSellerAddress().getAddress();
		this.sellerLatitude = deal.getSellerAddress().getLatitude();
		this.sellerLongitude = deal.getSellerAddress().getLongitude();
	}
	
	public TransportForUI(Proposition prop){
		this.id = prop.getId();
		this.dealId = prop.getDealId();
		this.price = prop.getPrice();
		this.volume = prop.getDeal().getVolume();
		this.name = prop.getProduct().getName();
		this.buyerAddress = prop.getBuyerAddress().getCountry() +" "+ prop.getBuyerAddress().getCity() +" "+ prop.getBuyerAddress().getAddress();
		this.buyerLatitude =  prop.getBuyerAddress().getLatitude();
		this.buyerLongitude = prop.getBuyerAddress().getLongitude();
		this.sellerAddress = prop.getSellerAaddress().getCountry() +" "+ prop.getSellerAaddress().getCity() +" "+ prop.getSellerAaddress().getAddress();
		this.sellerLatitude = prop.getSellerAaddress().getLatitude();
		this.sellerLongitude = prop.getSellerAaddress().getLongitude();
	}

	public double getBuyerLatitude() {
		return buyerLatitude;
	}

	public double getBuyerLongitude() {
		return buyerLongitude;
	}

	public double getSellerLatitude() {
		return sellerLatitude;
	}

	public double getSellerLongitude() {
		return sellerLongitude;
	}
	
	public long getDealId() {
		return dealId;
	}

	public void setDealId(long dealId) {
		this.dealId = dealId;
	}
}