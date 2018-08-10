package com.bigroi.stock.json;

import java.util.List;

public class TableResponse<T> {

	private Table<T> table;
	
	private Model<T> model;
	
	public TableResponse(Class<T> clazz, List<T> objects) {
		table = new Table<>(clazz, objects);
		model = new Model<>(clazz);
	}

	public Table<T> getTable() {
		return table;
	}

	public Model<T> getModel() {
		return model;
	}
	
	public TableResponse<T> removeColumn(String fieldName){
		table.removeColumn(fieldName);
		model.removeColumn(fieldName);
		return this;
	}
}
