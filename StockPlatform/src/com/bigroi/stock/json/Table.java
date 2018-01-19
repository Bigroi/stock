package com.bigroi.stock.json;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Table<T> {

	private Model model = new Model();
	
	private List<String> headers = new ArrayList<>();
	
	private List<List<Object>> rows = new ArrayList<>();
	
	public Table(Class<T> clazz, List<T> objects) throws TableException {
		try{
			Buttons buttons = clazz.getAnnotation(Buttons.class);
			if (buttons != null){
				model.setButtons(buttons.value());
			}
			int i = 0;
			for (Field field : clazz.getDeclaredFields()){
				Column column = field.getAnnotation(Column.class);
				if (column != null){
					model.addAllowSorting(i, column.allowSorting());
					model.addCustSortFn(i, column.customSortFunction());
					if (column.floatColumn()){
						model.addFloatColumn(i);
					}
					headers.add(column.value());
					i++;
				} else if (field.getAnnotation(Id.class) != null){
					model.setIdColumn(i);
					headers.add("id");
					i++;
				}
			}
			
			for (Object object : objects){
				List<Object> row = new ArrayList<>();
				for (Field field : clazz.getDeclaredFields()){
					if (field.getAnnotation(Column.class) != null ||
							field.getAnnotation(Id.class) != null){
						field.setAccessible(true);
						Object value = field.get(object);
						row.add(value == null ? "" : value);
						field.setAccessible(false);
					}
				}
				rows.add(row);
			}
		} catch(IllegalArgumentException | IllegalAccessException e){
			throw new TableException(e);
		}
	}
	
	public List<List<Object>> getRows() {
		return rows;
	}
	
	public List<String> getHeaders() {
		return headers;
	}
	
	public Model getModel() {
		return model;
	}

	public Table<T> exclude(int index) {
		model.exclude(index);
		headers.remove(index);
		for (List<Object> row : rows){
			row.remove(index);
		}
		return this;
	}
}
