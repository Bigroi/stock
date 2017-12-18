package com.bigroi.stock.bean;

import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Id;
import com.bigroi.stock.json.TableRow;

@TableRow
public class Product {
	
	private static final String YES = "Y";
	private static final String NO = "N";
	
	@Id
	private long id;
	
	@Column("stock.table.product.name")
	private String name;
	
	@Column("stock.table.product.description")
	private String description;
	
	@Column("stock.table.product.archive")
	private boolean archive;
	
	public String getArchiveData(){
		return archive ? YES : NO;
	}
	
	public void setArchiveData(String archive){
		this.archive = YES.equalsIgnoreCase(archive);
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
	
	public boolean isArchive() {
		return archive;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", archive=" +archive +"] \n";
	}
	
	
}
