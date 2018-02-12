package com.bigroi.stock.json;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class Table<T> {

	private List<T> data;
	
	private List<ColumnDef> columns = new ArrayList<>();
	
	public Table(Class<T> clazz, List<T> objects) throws TableException {
		try{
			for (Field field : clazz.getDeclaredFields()){
				Column column = field.getAnnotation(Column.class);
				if (column != null){
					ColumnDef columnDef = new ColumnDef();
					columnDef.setData(field.getName());
					columnDef.setTitle(column.value());
					columns.add(columnDef);
				}
			}
			data = objects;
		} catch(IllegalArgumentException e){
			throw new TableException(e);
		}
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public List<ColumnDef> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnDef> columns) {
		this.columns = columns;
	}
	
	public Table<T> removeColumn(String fieldName){
		for (ColumnDef columnDef : new ArrayList<>(columns)){
			if (columnDef.getData().equals(fieldName)){
				columns.remove(columnDef);
				return this;
			}
		}
		return this;
	}
	
}
