package com.bigroi.stock.bean;

import java.util.Date;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.json.Button;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Id;
import com.bigroi.stock.util.DateUtil;

@Button(url="/lot/json/StartTrading.spr", name="label.button.start_trading")
@Button(url="/lot/json/StopTrading.spr", name="label.button.stop_trading")
@Button(url="/lot/json/Delete.spr", name="label.button.delete")
public class Lot implements Bid{
	
	@Id
	private long id;
	
	@Column("label.lot.product")
	private String productName;
	
	private String description;
	
	@Column("label.lot.status")
	private BidStatus status;
	
	private long productId;	
	
	@Column(value = "label.lot.min_price", floatColumn = true)
	private double minPrice;
	
	private long sellerId;
	
	private int minVolume;
	
	@Column("label.lot.max_volume")
	private int maxVolume;
	
	@Column("label.lot.exp_date")
	private Date expDate = new Date();
	
	@Column("label.lot.creation_date")
	private Date creationDate = new Date();
	
	private String delivery;
	
	private String packaging;
	
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
	
	@Override
	public int getMinVolume() {
		return minVolume;
	}

	public void setMinVolume(int minVolume) {
		this.minVolume = minVolume;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
	
	public String getDelivery() {
		return delivery;
	}

	public String getPackaging() {
		return packaging;
	}
	
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	@Override
	public String toString() {
		return "Lot [id=" + id + ", productName=" + productName + ", description=" + description + ", status=" + status
				+ ", productId=" + productId + ", minPrice=" + minPrice + ", sellerId=" + sellerId + ", minVolume="
				+ minVolume + ", maxVolume=" + maxVolume + ", expDate=" + expDate + ", creationDate=" + creationDate
				+ ", delivery=" + delivery + ", packaging=" + packaging + "]";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Lot && ((Lot)obj).getId() == this.getId();
	}
}