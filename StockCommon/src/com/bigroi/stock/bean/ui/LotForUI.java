package com.bigroi.stock.bean.ui;

import java.util.Date;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.DateTimeAdapter;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.Id;
import com.bigroi.stock.json.Status;
import com.google.gson.annotations.JsonAdapter;

public class LotForUI{

	@Id
	private long id;
	
	@Column(value = "label.lot.product", responsivePriority=-6)
	private String productName;
	
	@Column(value = "label.lot.status", responsivePriority=-5)
	@Status(activate="/lot/json/StartTrading.spr", deactivate="/lot/json/StopTrading.spr")
	private BidStatus status;
	
	@Column(value = "label.lot.min_price", responsivePriority=-1)
	private double minPrice;
	
	@Column(value = "label.lot.max_volume", responsivePriority=-2)
	private int maxVolume;
	
	@Column(value = "label.lot.exp_date", allowSorting = true, responsivePriority=-4)
	private Date exparationDate = new Date();
	
	@Column(value = "label.lot.creation_date", allowSorting = false, responsivePriority=-3)
	@JsonAdapter(DateTimeAdapter.class)
	private Date creationDate;
	
	@Column(value = "label.lot.edit", responsivePriority=-7)
	@Edit(edit="getLotDialogParams", remove="/lot/json/Delete.spr")
	private String edit = "YYN";
	
	public LotForUI(Lot lot) {
		this.id = lot.getId();
		this.productName = lot.getProduct().getName();
		this.status = lot.getStatus();
		this.minPrice = lot.getMinPrice();
		this.maxVolume = lot.getMaxVolume();
		this.exparationDate = lot.getExparationDate();
		this.creationDate = lot.getCreationDate();
	}

}
