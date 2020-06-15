package com.bigroi.stock.bean.ui;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.json.Column;

public class TestDealForUI {

    @Column(value = "label.deal.productName", responsivePriority = -3)
    private final String productName;
    @Column(value = "label.deal.sellerAddress", responsivePriority = -3)
    private final String sellerAddrress;
    @Column(value = "label.deal.buyerAddrress", responsivePriority = -3)
    private final String buyerAddrress;
    @Column(value = "label.deal.price", responsivePriority = -3)
    private final double price;
    @Column(value = "label.deal.volume", responsivePriority = -3)
    private final int volume;

    public TestDealForUI(Deal deal) {
        productName = deal.getProductName();
        this.sellerAddrress = deal.getSellerCity();
        this.buyerAddrress = deal.getBuyerCity();
        this.price = deal.getPrice();
        this.volume = deal.getVolume();
    }
}
