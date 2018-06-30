package com.bigroi.stock.bean.ui;

import java.util.Date;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.DateTimeAdapter;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.Id;
import com.bigroi.stock.json.Status;
import com.google.gson.annotations.JsonAdapter;

public class TenderForUI {

	@Id
	private long id;
	
	@Column(value = "label.tender.product", responsivePriority=-6)
	private String productName;
	
	@Column(value = "label.tender.status", responsivePriority=-5)
	@Status(activate="/tender/json/StartTrading.spr", deactivate="/tender/json/StopTrading.spr")
	private BidStatus status;
	
	@Column(value = "label.tender.max_price", responsivePriority=-1)
	private double maxPrice;
	
	@Column(value = "label.tender.max_volume", responsivePriority=-2)
	private int maxVolume;
	
	@Column(value = "label.tender.exp_date", allowSorting = true, responsivePriority=-4)
	private Date exparationDate = new Date();
	
	@Column(value = "label.tender.creation_date", allowSorting = false, responsivePriority=-3)
	@JsonAdapter(DateTimeAdapter.class)
	private Date creationDate = new Date();
	
	@Edit(edit="getTenderDialogParams", remove="/tender/json/Delete.spr")
	@Column(value = "label.tender.edit", responsivePriority=-7)
	private String edit = "YYN";
	
	public TenderForUI(Tender tender) {
		this.id = tender.getId();
		this.productName = tender.getProduct().getName();
		this.status = tender.getStatus();
		this.maxPrice = tender.getMaxPrice();
		this.maxVolume = tender.getMaxVolume();
		this.exparationDate = tender.getExparationDate();
		this.creationDate = tender.getCreationDate();
	}
}
