package com.bigroi.stock.json;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Table {

	private Model model = new Model();
	
	private List<String> headers = new ArrayList<>();
	
	private List<List<String>> rows = new ArrayList<>();
	
	public Table(Class<?> clazz, List<?> objects) throws TableException {
		try{
			int i = 0;
			for (Field field : clazz.getDeclaredFields()){
				Column column = field.getAnnotation(Column.class);
				if (column != null){
					model.addAllowSorting(i, column.allowSorting());
					model.addCustSortFn(i, column.customSortFunction());
					headers.add(column.value());
					i++;
				}
			}
			
			for (Object object : objects){
				List<String> row = new ArrayList<>();
				for (Field field : clazz.getDeclaredFields()){
					if (field.getAnnotation(Column.class) != null){
						field.setAccessible(true);
						Object value = field.get(object);
						row.add(value == null ? "" : value.toString());
						field.setAccessible(false);
					}
				}
				rows.add(row);
			}
		} catch(IllegalArgumentException | IllegalAccessException e){
			throw new TableException(e);
		}
	}
	
	public List<List<String>> getRows() {
		return rows;
	}
	
	public List<String> getHeaders() {
		return headers;
	}
	
	public Model getModel() {
		return model;
	}

	public void exclude(int index) {
		model.exclude(index);
		headers.remove(index);
		for (List<String> row : rows){
			row.remove(index);
		}
	}
}
