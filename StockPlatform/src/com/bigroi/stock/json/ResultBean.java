package com.bigroi.stock.json;

import com.bigroi.stock.bean.Bid;
import com.bigroi.stock.controller.resource.BaseResourseController.ButtonTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResultBean {

	private int result;
	private Object data;

	public ResultBean() {

	}

	public ResultBean(int result, Object data) {
		this.result = result;
		this.data = data;

	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder()
				.setDateFormat(Bid.FORMATTER.toPattern())
				.registerTypeAdapter(Button.class, new ButtonTypeAdapter())
				.create();
		return gson.toJson(this);
	}

}
