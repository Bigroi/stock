package com.bigroi.stock.bean;

import java.text.ParseException;
import java.util.Date;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.DateTimeAdapter;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.FilterMethod;
import com.bigroi.stock.json.Id;
import com.bigroi.stock.json.Status;
import com.bigroi.stock.util.DateUtil;
import com.google.gson.annotations.JsonAdapter;

public class Tender implements Bid{

	@Id
	private long id;
	
	private long productId;
	
	@Column(value = "label.tender.product", filterMethod = FilterMethod.SELECT)
	private String productName;
	
	private String description;
	
	@Column(value = "label.tender.status", filterMethod = FilterMethod.SELECT)
	@Status(activate="/tender/json/StartTrading.spr", deactivate="/tender/json/StopTrading.spr")
	private BidStatus status;
	
	@Column("label.tender.max_price")
	private double maxPrice;
	
	private int minVolume;
	
	@Column("label.tender.max_volume")
	private int maxVolume;
	
	private long customerId;
	
	@Column(value = "label.tender.exp_date", allowSorting = true)
	private Date expDate = new Date();
	
	@Column(value = "label.tender.creation_date", allowSorting = false)
	@JsonAdapter(DateTimeAdapter.class)
	private Date creationDate = new Date();
	
	private int delivery;
	
	private int packaging;
	
	@Edit(edit="setTenderDialogPlugin", remove="/tender/json/Delete.spr")
	@Column("")
	private String edit = "YY";
	
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
	
	public int getDelivery() {
		return delivery;
	}
	
	public void setDelivery(int delivery) {
		this.delivery = delivery;
	}
	
	public void setPackaging(int packaging) {
		this.packaging = packaging;
	}
	
	public int getPackaging() {
		return packaging;
	}

	@Override
	public String toString() {
		return "Tender [id=" + id + ", productId=" + productId + ", productName=" + productName + ", description="
				+ description + ", status=" + status + ", maxPrice=" + maxPrice + ", minVolume=" + minVolume
				+ ", maxVolume=" + maxVolume + ", customerId=" + customerId + ", expDate=" + expDate + ", creationDate="
				+ creationDate + ", delivery=" + delivery + ", packaging=" + packaging + "]";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Tender && ((Tender)obj).getId() == this.getId();
	}

	public String getEdit() {
		return edit;
	}
	
	public void setEdit(String edit) {
		this.edit = edit;
	}

	@Override
	public double getPrice() {
		return maxPrice;
	}
}
