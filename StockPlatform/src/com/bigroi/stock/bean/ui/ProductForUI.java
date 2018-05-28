package com.bigroi.stock.bean.ui;

import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.FilterMethod;
import com.bigroi.stock.json.Id;

public class ProductForUI {

	@Id
	private long id;
	
	@Column(value = "label.product.name", allowSorting = true, filterMethod = FilterMethod.SELECT)
	private String name;
	
	@Column(value = "label.product.description", filterMethod = FilterMethod.TEXT)
	private String description;
	
	@Column(value = "label.product.archive", filterMethod = FilterMethod.SELECT)
	private String removed;
	
	@Column(value = "label.product.price")
	private double delivaryPrice;
	
	private double sellPrice;
	
	private double buyPrice;
	
	private int sellVolume;
	
	private int buyVolume;
	
	@Edit(details = "/product/TradeOffers.spr", edit="setProductDialogPlugin", remove="/product/json/admin/Delete.spr")
	@Column("label.product.edit")
	private String edit = "YNN";
	
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
		this.sellPrice = sellPrice;
	}

	public double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
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
	
}
