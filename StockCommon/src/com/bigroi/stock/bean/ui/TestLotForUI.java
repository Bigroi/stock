package com.bigroi.stock.bean.ui;

import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.Id;

public class TestLotForUI{
	
	@Id
	private final long id;
	
	@Column(value = "label.lot.product", responsivePriority=-6)
	private final String productName;
	
	@Column(value = "label.lot.min_price", responsivePriority=-1)
	private final double price;
	
	@Column(value = "label.lot.max_volume", responsivePriority=-2)
	private final int maxVolume;
	
	@Edit(edit="getTestLotDialogParams")
	private final String edit = "NNN";
	
	public TestLotForUI(Lot lot) {
		this.productName = lot.getProduct().getName();
		this.price = lot.getPrice();
		this.maxVolume = lot.getMaxVolume();
		this.id = lot.getId();
	}

	public TestLotForUI(LotForUI lotForUI) {
		this.productName = lotForUI.productName;
		this.price = lotForUI.minPrice;
		this.maxVolume = lotForUI.maxVolume;
		this.id = lotForUI.id;
	}
}
