package com.bigroi.stock.json;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class Model<T> {

	private String idColumn;
	
	private String editColumn;
	
	private String editForm;
	
	private String removeUrl;
	
	private String detailsUrl;
	
	private String statusColumn;
	
	private String activateUrl;
	
	private String deactivateUrl;
	
	private Map<String, FilterMethod> filterColumns = new HashMap<>();
	
	public Model(Class<T> clazz) {
		for (Field field : clazz.getDeclaredFields()){
			if (field.getAnnotation(Id.class) != null){
				idColumn = field.getName();
			}
			Status status = field.getAnnotation(Status.class);
			if (status != null){
				statusColumn = field.getName();
				activateUrl = status.activate();
				deactivateUrl = status.deactivate();
			}
			
			Edit edit = field.getAnnotation(Edit.class);
			if (edit != null){
				editColumn = field.getName();
				editForm = edit.edit();
				removeUrl = edit.remove();
				detailsUrl = edit.details();
			}
			
			Column column = field.getAnnotation(Column.class);
			if (column != null){
				this.filterColumns.put(field.getName(), column.filterMethod());
			}
		}
	}
	
	public String getIdColumn() {
		return idColumn;
	}

	public String getEditColumn() {
		return editColumn;
	}

	public String getEditForm() {
		return editForm;
	}

	public String getRemoveUrl() {
		return removeUrl;
	}

	public String getStatusColumn() {
		return statusColumn;
	}

	public String getActivateUrl() {
		return activateUrl;
	}

	public String getDeactivateUrl() {
		return deactivateUrl;
	}

	public String getDetailsUrl() {
		return detailsUrl;
	}
	
	public Map<String, FilterMethod> getFilterColumns() {
		return filterColumns;
	}

	public void removeColumn(String fieldName) {
		if (fieldName.equals(editColumn)){
			editColumn = null;
		}
		
		if (fieldName.equals(statusColumn)){
			statusColumn = null;
		}
		
		if (fieldName.equals(idColumn)){
			idColumn = null;
		}
	}
	
}
