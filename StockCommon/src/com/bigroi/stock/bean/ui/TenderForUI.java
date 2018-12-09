package com.bigroi.stock.bean.ui;

import java.util.Date;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.Id;
import com.bigroi.stock.json.Status;

public class TenderForUI {

	@Id
	long id;
	
	@Column(value = "label.tender.product", responsivePriority=-6)
	final String productName;
	
	@Column(value = "label.tender.status", responsivePriority=-5)
	@Status(activate="/tender/json/StartTrading", deactivate="/tender/json/StopTrading")
	final BidStatus status;
	
	@Column(value = "label.tender.max_price", responsivePriority=-1)
	final double maxPrice;
	
	@Column(value = "label.tender.max_volume", responsivePriority=-2)
	final int maxVolume;
	
	@Column(value = "label.tender.exp_date", allowSorting = true, responsivePriority=-4)
	final Date exparationDate;
	
	@Column(value = "label.tender.creation_date", allowSorting = false, responsivePriority=-3)
	final Date creationDate;
	
	@Edit(edit="getTenderDialogParams", remove="/tender/json/Delete")
	@Column(value = "label.tender.edit", responsivePriority=-7)
	final String edit;
	
	public TenderForUI(Tender tender) {
		this.id = tender.getId();
		this.productName = tender.getProduct().getName();
		this.status = tender.getStatus();
		this.maxPrice = tender.getPrice();
		this.maxVolume = tender.getMaxVolume();
		this.exparationDate = tender.getExparationDate();
		this.creationDate = tender.getCreationDate();
		this.edit = "YYN";
	}
}
