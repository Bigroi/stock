package com.bigroi.stock.bean;

import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.Id;

public class Product {
	
	@Id
	private long id;
	
	@Column("label.product.name")
	private String name;
	
	@Column("label.product.description")
	private String description;
	
	@Column("label.product.archive")
	private String archive;
	
	
	@Edit(edit="setProductDialogPlugin", remove="/product/json/admin/Delete.spr")
	@Column("label.product.edit")
	private String edit = "YN";
	
	public String getArchive(){
		return archive;
	}
	
	public void setArchive(String archive){
		this.archive = archive;
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
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", archive=" +archive +"] \n";
	}
	
	public String getEdit() {
		return edit;
	}
}
