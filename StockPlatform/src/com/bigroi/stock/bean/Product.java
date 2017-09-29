package com.bigroi.stock.bean;

public class Product {
	
	private static final String YES = "Y";
	private static final String NO = "N";

	private long id;
	private String name;
	private String description;
	private  boolean archive;
	
	public String getArchiveData(){
		
		if(this.archive == false){
			return NO;
		}else{
			return YES;
		}
	}
	
	public void setArchiveData(String archive){
		if("N".equals(archive.toUpperCase())){
			this.archive = true;
		}else{
			this.archive = false;
		}
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
	
	public boolean getArchive() {
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
