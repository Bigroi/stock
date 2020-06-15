package com.bigroi.stock.bean.ui;

import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.Id;

public class TestTenderForUI {

    @Id
    private final long id;

    @Column(value = "label.tender.product", responsivePriority = -1)
    private final String productName;

    @Column(value = "label.tender.max_price", responsivePriority = -2)
    private final double maxPrice;

    @Column(value = "label.tender.max_volume", responsivePriority = -3)
    private final int maxVolume;

    @Edit(edit = "getTestTenderDialogParams")
    private final String edit;

    public TestTenderForUI(Tender tender) {
        this.productName = tender.getProduct().getName();
        this.maxPrice = tender.getPrice();
        this.maxVolume = tender.getMaxVolume();
        this.id = tender.getId();
        this.edit = "NNN";
    }

    public TestTenderForUI(TenderForUI tenderForUI) {
        this.productName = tenderForUI.productName;
        this.maxPrice = tenderForUI.maxPrice;
        this.maxVolume = tenderForUI.maxVolume;
        this.id = tenderForUI.id;
        this.edit = "NNN";
    }
}
