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
	
	@Column("lable.tender.product")
	private String productName;
	
	private String description;
	
	@Column("lable.tender.status")
	private BidStatus status;
	
	@Column("lable.tender.max_price")
	private double maxPrice;
	
	private int minVolume;
	
	@Column("lable.tender.max_volume")
	private int maxVolume;
	
	private long customerId;
	
	@Column("lable.tender.exp_date")
	private Date expDate = new Date();
	
	@Column("lable.tender.creationDate")
	private Date creationDate = new Date();
	
	
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
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	@Override
	public String toString() {
		return "Tender [id=" + id + ", productId=" + productId + ", productName=" + productName + ", description="
				+ description + ", status=" + status + ", maxPrice=" + maxPrice + ", minVolume=" + minVolume
				+ ", maxVolume=" + maxVolume + ", customerId=" + customerId + ", expDate=" + expDate + ", creationDate="
				+ creationDate + "]";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Tender && ((Tender)obj).getId() == this.getId();
	}

}
