package com.bigroi.stock.bean;

public class Email {

	private long id;
	private String toEmail;
	private String emailSubject;
	private String emailText;

	public Email() {// init
	}
	
	public Email(String toEmail, String emailSublect, String emailText) {
		this.toEmail = toEmail;
		this.emailSubject = emailSublect;
		this.emailText = emailText;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSublect) {
		this.emailSubject = emailSublect;
	}

	public String getEmailText() {
		return emailText;
	}

	public void setEmailText(String emailText) {
		this.emailText = emailText;
	}

	@Override
	public String toString() {
		return "Email [id=" + id + ", toEmail=" + toEmail + ", emailSublect=" + emailSubject + ", emailText="
				+ emailText + "]";
	}

}
