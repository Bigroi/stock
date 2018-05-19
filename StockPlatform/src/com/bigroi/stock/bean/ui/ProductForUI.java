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
}
