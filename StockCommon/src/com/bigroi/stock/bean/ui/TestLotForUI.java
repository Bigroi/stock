package com.bigroi.stock.bean.ui;

import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.Id;

public class TestLotForUI{
	
	@Id
	private final long id;
	
	@Column(value = "label.lot.product", responsivePriority=-1)
	private final String productName;
	
	@Column(value = "label.lot.max_volume", responsivePriority=-3)
	private final int maxVolume;
	
	@Column(value = "label.lot.min_price", responsivePriority=-2)
	private final double minPrice;
	
	@Edit(edit="getTestLotDialogParams")
	private final String edit;
	
	public TestLotForUI(Lot lot) {
		this.productName = lot.getProduct().getName();
		this.minPrice = lot.getPrice();
		this.maxVolume = lot.getMaxVolume();
		this.id = lot.getId();
		this.edit = "NNN";
	}

	public TestLotForUI(LotForUI lotForUI) {
		this.productName = lotForUI.productName;
		this.minPrice = lotForUI.minPrice;
		this.maxVolume = lotForUI.maxVolume;
		this.id = lotForUI.id;
		this.edit = "NNN";
	}
}
