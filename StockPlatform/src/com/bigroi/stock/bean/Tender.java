package com.bigroi.stock.bean;

import java.text.ParseException;
import java.util.Date;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Id;
import com.bigroi.stock.util.DateUtil;

public class Tender implements Bid{

	@Id
	private long id;
	
	private long productId;
	
	@Column("stock.table.tender.description")
	private String description;
	
	@Column("stock.table.tender.status")
	private BidStatus status;
	
	@Column("stock.table.tender.maxPrice")
	private double maxPrice;
	
	@Column("stock.table.tender.minVolume")
	private int minVolume;
	
	@Column("stock.table.tender.volume")
	private int maxVolume;
	
	private long customerId;
	
	@Column("stock.table.tender.expDate")
	private Date expDate = new Date();
	
	
	
	

	public boolean isExpired() {
		if (DateUtil.beforToday(expDate)) {
			return true;
		}
		return false;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public String getDateStr() {
		return FORMATTER.format(expDate);
	}

	public void setDateStr(String dateStr) throws ParseException {
		expDate = FORMATTER.parse(dateStr);
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

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public BidStatus getStatus() {
		return status;
	}

	public void setStatus(BidStatus status) {
		this.status = status;
	}
	
	@Override
	public int getMaxVolume() {
		return maxVolume;
	}

	@Override
	public void setMaxVolume(int maxVolume) {
		this.maxVolume = maxVolume;
	}

	public int getMinVolume() {
		return minVolume;
	}

	public void setMinVolume(int minVolume) {
		this.minVolume = minVolume;
	}
	
	@Override
	public String toString() {
		return "Tender [id=" + id + ", description=" + description + ", productId=" + productId + ", maxPrice="
				+ maxPrice + ", customerId=" + customerId + ", status=" + status + ", expDate=" + expDate + ", volume="
				+ maxVolume + ", minVolume=" + minVolume + "]";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Tender && ((Tender)obj).getId() == this.getId();
	}

}
