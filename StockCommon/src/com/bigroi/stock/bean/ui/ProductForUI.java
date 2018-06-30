package com.bigroi.stock.bean.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.Id;

public class ProductForUI {

	@Id
	private long id;
	
	@Column(value = "label.product.name", allowSorting = true, responsivePriority = -4)
	private String name;
	
	@Column(value = "label.product.description", responsivePriority = -1)
	private String description;
	
	@Column(value = "label.product.archive", responsivePriority = -3)
	private String removed;
	
	@Column(value = "label.product.price", responsivePriority = -2)
	private double delivaryPrice;
	
	@Edit(details = "/product/TradeOffers.spr", edit="getProductDialogParams", remove="/product/json/admin/Delete.spr")
	@Column(value = "label.product.edit", responsivePriority = -4)
	private String edit = "YNN";
	
	private double sellPrice;
	
	private double buyPrice;
	
	private int sellVolume;
	
	private int buyVolume;
	
	private String picture;
	
	public ProductForUI(){
		
	}
	
	public ProductForUI(Product product) {
		this.delivaryPrice = product.getDelivaryPrice();
		this.description = product.getDescription();
		this.id = product.getId();
		this.name = product.getName();
		this.removed = product.getRemoved();
	}
	
	public void setEdit(String edit) {
		this.edit = edit;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(double sellPrice) {
		this.sellPrice = new BigDecimal(sellPrice).setScale(2, RoundingMode.UP).doubleValue();
	}

	public double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(double buyPrice) {
		this.buyPrice = new BigDecimal(buyPrice).setScale(2, RoundingMode.UP).doubleValue();
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
	
}
