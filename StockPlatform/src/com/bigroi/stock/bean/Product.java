package com.bigroi.stock.bean;

public class Product {

	private long id;
	private String name;
	private String description;
	private int lot—ount;
	private int application—ount;

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

	public int getLot—ount() {
		return lot—ount;
	}

	public void setLot—ount(int lot—ount) {
		this.lot—ount = lot—ount;
	}

	public int getApplication—ount() {
		return application—ount;
	}

	public void setApplication—ount(int application—ount) {
		this.application—ount = application—ount;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
}
