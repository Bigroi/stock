package com.bigroi.stock.bean;

import com.google.gson.Gson;

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
		return new Gson().toJson(this);
	}

}
