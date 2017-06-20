package com.bigroi.stock.dao.exceptions;

public class DaoExeptions extends Exception {

	private static final long serialVersionUID = -1905277515382086343L;

	public DaoExeptions() {
		super();

	}

	public DaoExeptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public DaoExeptions(String message, Throwable cause) {
		super(message, cause);

	}

	public DaoExeptions(String message) {
		super(message);

	}

	public DaoExeptions(Throwable cause) {
		super(cause);

	}
}
