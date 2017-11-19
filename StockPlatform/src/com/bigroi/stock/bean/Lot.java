package com.bigroi.stock.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Id;
import com.bigroi.stock.util.DateUtil;

public class Lot implements Bid{
	
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
	
	@Id
	private long id;
	
	@Column("skock.table.lot.description")
	private String description;
	
	@Column("skock.table.lot.poductId")
	private long productId;
	
	@Column("skock.table.lot.minPrice")
	private double minPrice;
	
	@Column("skock.table.lot.sellerId")
	private long sellerId;
	
	@Column("skock.table.lot.expDate")
	private Date expDate = new Date();
	
	@Column("skock.table.lot.status")
	private Status status;
	
	@Column("skock.table.lot.volumeOfLot")
	private int volume;

	public boolean isExpired() {
		if (DateUtil.beforToday(expDate)) {
			return true;
		}
		return false;
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
	
	@Override
	public String toString() {
		return "Lot [id=" + id + ", description=" + description + ", poductId=" + productId + ", minPrice=" + minPrice
				+ ", sallerId=" + sellerId + ", expDate=" + expDate + ", status=" + status + ", volumeOfLot=" + volume +"]";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Lot && ((Lot)obj).getId() == this.getId();
	}
	
}