package com.bigroi.stock.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Id;
import com.bigroi.stock.util.DateUtil;

public class Tender {

	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");

	@Id
	private long id;
	
	@Column("skock.table.tender.description")
	private String description;
	
	@Column("skock.table.tender.productId")
	private long productId;
	
	@Column("skock.table.tender.maxPrice")
	private double maxPrice;
	
	@Column("skock.table.tender.customerId")
	private long customerId;
	
	@Column("skock.table.tender.status")
	private Status status;
	
	@Column("skock.table.tender.expDate")
	private Date expDate = new Date();
	
	@Column("skock.table.tender.volumeOfTender")
	private int volumeOfTender;

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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public int getVolumeOfTender() {
		return volumeOfTender;
	}

	public void setVolumeOfTender(int volumeOfTender) {
		this.volumeOfTender = volumeOfTender;
	}


	@Override
	public String toString() {
		return "Tender [id=" + id + ", description=" + description + ", productId=" + productId + ", maxPrice="
				+ maxPrice + ", customerId=" + customerId + ", status=" + status + ", expDate=" + expDate + ", volumeOfTender=" + volumeOfTender + "]";
	}
}
