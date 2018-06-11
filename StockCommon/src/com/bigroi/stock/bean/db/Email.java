package com.bigroi.stock.bean.db;

public class Email {

	private long id;
	private String recipient;
	private String subject;
	private String body;

	public Email() {// init
	}
	
	public Email(String recipient, String subject, String body) {
		this.recipient = recipient;
		this.subject = subject;
		this.body = body;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Email [id=" + id + ", recipient=" + recipient + ", subject=" + subject + ", body=" + body + "]";
	}

}
