package com.bigroi.stock.bean.ui;

import java.text.DecimalFormat;

import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.Id;

public class ProductForUI {

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("# ###.##");

    @Id
    private long id;

    @Column(value = "label.product.name", allowSorting = true, responsivePriority = -4)
    private String name;

    @Column(value = "label.product.archive", responsivePriority = -3)
    private String removed;

    @Column(value = "label.product.price", responsivePriority = -2)
    private double delivaryPrice;

    @Edit(details = "/product/TradeOffers", edit = "getProductDialogParams", remove = "/product/json/admin/Delete")
    @Column(value = "label.product.edit", responsivePriority = -4)
    private String edit = "YNN";

    private String sellPrice;

    private String buyPrice;

    private int sellVolume;

    private int buyVolume;

    private String picture;

    public ProductForUI() {

    }

    public ProductForUI(Product product) {
        this.delivaryPrice = product.getDelivaryPrice();
        this.id = product.getId();
        this.name = product.getName();
        this.removed = product.getRemoved();
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemoved() {
        return removed;
    }

    public void setRemoved(String removed) {
        this.removed = removed;
    }

    public double getDelivaryPrice() {
        return delivaryPrice;
    }

    public void setDelivaryPrice(double delivaryPrice) {
        this.delivaryPrice = delivaryPrice;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = DECIMAL_FORMAT.format(sellPrice);
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = DECIMAL_FORMAT.format(buyPrice);
    }

    public int getSellVolume() {
        return sellVolume;
    }

    public void setSellVolume(int sellVolume) {
        this.sellVolume = sellVolume;
    }

    public int getBuyVolume() {
        return buyVolume;
    }

    public void setBuyVolume(int buyVolume) {
        this.buyVolume = buyVolume;
    }

    public String getEdit() {
        return edit;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
