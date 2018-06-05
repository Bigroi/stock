package com.bigroi.transport.json;

public class ResultBean {

	private int result;
	private Object data;
	private String message;

	public ResultBean() {

	}

	public ResultBean(int result, String message) {
		this.result = result;
		this.message = message;
	}
	
	public ResultBean(int result, Object data, String message) {
		this.result = result;
		this.message = message;
		this.data = data;
	}

	public int getResult() {
		return result;
	}

	public Object getData() {
		return data;
	}

	@Override
	public String toString() {
		return GsonUtil.getGson().toJson(this);
	}

	public String getMessage() {
		return message;
	}
	
}
