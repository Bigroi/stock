package com.bigroi.stock.bean;

import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Id;
import com.bigroi.stock.json.TableRow;

@TableRow
public class Product {
	
	@Id
	private long id;
	
	@Column("lable.product.name")
	private String name;
	
	@Column("lable.product.description")
	private String description;
	
	@Column("lable.product.archive")
	private String archive;
	
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
	
	
}
