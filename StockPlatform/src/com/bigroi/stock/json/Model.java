package com.bigroi.stock.json;

import java.lang.reflect.Field;

class Model<T> {

	private String idColumn;
	
	private String editColumn;
	
	private String editForm;
	
	private String removeUrl;
	
	private String statusColumn;
	
	private String activateUrl;
	
	private String deactivateUrl;
	
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
			}
		}
	}
	
	public void setIdColumn(String idColumn) {
		this.idColumn = idColumn;
	}
	
	public String getIdColumn() {
		return idColumn;
	}

	public String getEditColumn() {
		return editColumn;
	}

	public void setEditColumn(String editColumn) {
		this.editColumn = editColumn;
	}

	public String getEditForm() {
		return editForm;
	}

	public void setEditForm(String editForm) {
		this.editForm = editForm;
	}

	public String getRemoveUrl() {
		return removeUrl;
	}

	public void setRemoveUrl(String removeUrl) {
		this.removeUrl = removeUrl;
	}

	public String getStatusColumn() {
		return statusColumn;
	}

	public void setStatusColumn(String statusColumn) {
		this.statusColumn = statusColumn;
	}

	public String getActivateUrl() {
		return activateUrl;
	}

	public void setActivateUrl(String activateUrl) {
		this.activateUrl = activateUrl;
	}

	public String getDeactivateUrl() {
		return deactivateUrl;
	}

	public void setDeactivateUrl(String deactivateUrl) {
		this.deactivateUrl = deactivateUrl;
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
