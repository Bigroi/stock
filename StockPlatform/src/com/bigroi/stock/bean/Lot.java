package com.bigroi.stock.bean;

import java.util.Date;

import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Id;
import com.bigroi.stock.util.DateUtil;

public class Lot implements Bid{
	
	@Id
	private long id;
	
	@Column("stock.table.lot.poductName")
	private String productName;
	
	@Column("stock.table.lot.description")
	private String description;
	
	@Column("stock.table.lot.status")
	private Status status;
	
	private long productId;	
	
	@Column("stock.table.lot.minPrice")
	private double minPrice;
	
	private long sellerId;
	
	@Column("stock.table.lot.minVolume")
	private int minVolume;
	
	@Column("stock.table.lot.volume")
	private int volume;
	
	@Column("stock.table.lot.expDate")
	private Date expDate = new Date();
	
	public boolean isExpired() {
		if (DateUtil.beforToday(expDate)) {
			return true;
		}
		return false;
	}

	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long salerId) {
		this.sellerId = salerId;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	
	
	public int getMinVolume() {
		return minVolume;
	}

	public void setMinVolume(int minVolume) {
		this.minVolume = minVolume;
	}

	@Override
	public String toString() {
		return "Lot [id=" + id + ", description=" + description + ", productId=" + productId + ", minPrice=" + minPrice
				+ ", sellerId=" + sellerId + ", expDate=" + expDate + ", status=" + status + ", volume=" + volume
				+ ", minVolume=" + minVolume + "]";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Lot && ((Lot)obj).getId() == this.getId();
	}
	
}