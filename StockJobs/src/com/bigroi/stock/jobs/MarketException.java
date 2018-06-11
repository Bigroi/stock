package com.bigroi.stock.jobs;

public class MarketException extends Exception {

	private static final long serialVersionUID = -1716491748469890094L;

	public MarketException() {
	}

	public MarketException(String message) {
		super(message);
	}

	public MarketException(Throwable cause) {
		super(cause);
	}

	public MarketException(String message, Throwable cause) {
		super(message, cause);
	}

}
