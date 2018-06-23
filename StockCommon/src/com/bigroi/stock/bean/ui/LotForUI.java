package com.bigroi.stock.bean.ui;

import java.util.Date;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.DateTimeAdapter;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.FilterMethod;
import com.bigroi.stock.json.Id;
import com.bigroi.stock.json.Status;
import com.google.gson.annotations.JsonAdapter;

public class LotForUI{

	@Id
	private long id;
	
	@Column(value = "label.lot.product", filterMethod = FilterMethod.SELECT)
	private String productName;
	
	@Column(value = "label.lot.status", filterMethod = FilterMethod.SELECT)
	@Status(activate="/lot/json/StartTrading.spr", deactivate="/lot/json/StopTrading.spr")
	private BidStatus status;
	
	@Column("label.lot.min_price")
	private double minPrice;
	
	@Column("label.lot.max_volume")
	private int maxVolume;
	
	@Column(value = "label.lot.exp_date", allowSorting = true)
	private Date exparationDate = new Date();
	
	@Column(value = "label.lot.creation_date", allowSorting = false)
	@JsonAdapter(DateTimeAdapter.class)
	private Date creationDate;
	
	@Column("label.lot.edit")
	@Edit(edit="getLotDialogParams", remove="/lot/json/Delete.spr")
	private String edit = "YY";
	
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
