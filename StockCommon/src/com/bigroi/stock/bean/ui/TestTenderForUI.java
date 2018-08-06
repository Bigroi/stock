package com.bigroi.stock.bean.ui;

import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.Id;

public class TestTenderForUI {
	
	@Id
	private final long id;
	
	@Column(value = "label.tender.product", responsivePriority=-6)
	private final String productName;
	
	@Column(value = "label.tender.min_price", responsivePriority=-1)
	private final double price;
	
	@Column(value = "label.tender.max_volume", responsivePriority=-2)
	private final int maxVolume;
	
	@Edit(edit="getTestTenderDialogParams")
	private final String edit = "NNN";
	
	public TestTenderForUI(Tender tender) {
		this.productName = tender.getProduct().getName();
		this.price = tender.getPrice();
		this.maxVolume = tender.getMaxVolume();
		this.id = tender.getId();
	}

	public TestTenderForUI(TenderForUI tenderForUI) {
		this.productName = tenderForUI.productName;
		this.price = tenderForUI.maxPrice;
		this.maxVolume = tenderForUI.maxVolume;
		this.id = tenderForUI.id;
	}
}
