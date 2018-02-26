package com.bigroi.stock.bean;

import com.bigroi.stock.json.Column;

public class TradeOffer {

	@Column("label.product.price")
	private String price;
	
	@Column("label.product.lot_volume")
	private long lotVolume;
	
	@Column("label.product.tender_volume")
	private long tenderVolume;

	public TradeOffer(String price) {
		this.price = price;
		this.lotVolume = 0;
		this.tenderVolume = 0;
	}
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public long getLotVolume() {
		return lotVolume;
	}

	public void setLotVolume(long lotVolume) {
		this.lotVolume = lotVolume;
	}

	public long getTenderVolume() {
		return tenderVolume;
	}

	public void setTenderVolume(long tenderVolume) {
		this.tenderVolume = tenderVolume;
	}
	
}
