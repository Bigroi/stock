package com.bigroi.stock.util.exception;

public class StockRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -2081457971110604851L;

	public StockRuntimeException() {
	}

	public StockRuntimeException(String message) {
		super(message);
	}

	public StockRuntimeException(Throwable cause) {
		super(cause);
	}

	public StockRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
