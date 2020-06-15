package com.bigroi.stock.bean.ui;

import com.bigroi.stock.json.Column;

public class TradeOffer {

    @Column(value = "label.product.price", responsivePriority = -3)
    private final String price;

    @Column(value = "label.product.lot_volume", responsivePriority = -2)
    private long lotVolume;

    @Column(value = "label.product.tender_volume", responsivePriority = -1)
    private long tenderVolume;

    private final Double priceDouble;

    public TradeOffer(Double price) {
        priceDouble = price;
        this.price = ProductForUI.DECIMAL_FORMAT.format(price);
        this.lotVolume = 0;
        this.tenderVolume = 0;
    }

    public TradeOffer(String price) {
        priceDouble = -1.;
        this.price = price;
        this.lotVolume = 0;
        this.tenderVolume = 0;
    }

    public Double getPriceDouble() {
        return priceDouble;
    }

    public String getPrice() {
        return price;
    }

    public long getLotVolume() {
        return lotVolume;
    }

    public void setLotVolume(long lotVolume) {
        this.lotVolume = lotVolume;
    }

    public long getTenderVolume() {
        return tenderVolume;
    }

    public void setTenderVolume(long tenderVolume) {
        this.tenderVolume = tenderVolume;
    }

}
