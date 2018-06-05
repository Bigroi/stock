package com.bigroi.transport.json;

public class ColumnDef {

	private String title;
	
	private String data;
	
	private boolean orderable; 
	
	public boolean isOrderable() {
		return orderable;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setOrderable(boolean orderable) {
		this.orderable = orderable;
	}
	
}
