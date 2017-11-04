package com.bigroi.stock.messager.message;

public class MessageException extends Exception {

	private static final long serialVersionUID = 183631564486069992L;

	public MessageException() {
	}

	public MessageException(String arg0) {
		super(arg0);
	}

	public MessageException(Throwable arg0) {
		super(arg0);
	}

	public MessageException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
