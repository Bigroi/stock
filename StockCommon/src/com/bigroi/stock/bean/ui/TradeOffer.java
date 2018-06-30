package com.bigroi.stock.bean.ui;

import com.bigroi.stock.json.Column;

public class TradeOffer {

	@Column(value = "label.product.price", responsivePriority=-3)
	private String price;
	
	@Column(value = "label.product.lot_volume", responsivePriority=-2)
	private long lotVolume;
	
	@Column(value = "label.product.tender_volume", responsivePriority=-1)
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
