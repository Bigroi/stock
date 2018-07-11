package com.bigroi.stock.bean.ui;

import java.util.Date;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.Id;
import com.bigroi.stock.json.Status;

public class LotForUI{

	@Id
	private final long id;
	
	@Column(value = "label.lot.product", responsivePriority=-6)
	private final String productName;
	
	@Column(value = "label.lot.status", responsivePriority=-5)
	@Status(activate="/lot/json/StartTrading.spr", deactivate="/lot/json/StopTrading.spr")
	private final BidStatus status;
	
	@Column(value = "label.lot.min_price", responsivePriority=-1)
	private final double minPrice;
	
	@Column(value = "label.lot.max_volume", responsivePriority=-2)
	private final int maxVolume;
	
	@Column(value = "label.lot.exp_date", allowSorting = true, responsivePriority=-4)
	private final Date exparationDate;
	
	@Column(value = "label.lot.creation_date", allowSorting = false, responsivePriority=-3)
	private final Date creationDate;
	
	@Column(value = "label.lot.edit", responsivePriority=-7)
	@Edit(edit="getLotDialogParams", remove="/lot/json/Delete.spr")
	private final String edit;
	
	public LotForUI(Lot lot) {
		this.id = lot.getId();
		this.productName = lot.getProduct().getName();
		this.status = lot.getStatus();
		this.minPrice = lot.getPrice();
		this.maxVolume = lot.getMaxVolume();
		this.exparationDate = lot.getExparationDate();
		this.creationDate = lot.getCreationDate();
		this.edit = "YYN";
	}

}
