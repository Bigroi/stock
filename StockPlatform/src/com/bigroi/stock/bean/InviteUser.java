package com.bigroi.stock.bean;

import java.util.Date;

public class InviteUser {
	
	private long id;
	private String inviteEmail;
	private String generatedKey;
	private long companyId;
	private Date creationDate =  new Date();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getInviteEmail() {
		return inviteEmail;
	}
	public void setInviteEmail(String inviteEmail) {
		this.inviteEmail = inviteEmail;
	}
	public String getGeneratedKey() {
		return generatedKey;
	}
	public void setGeneratedKey(String generatedKey) {
		this.generatedKey = generatedKey;
	}
	
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	@Override
	public String toString() {
		return "InviteUser [id=" + id + ", inviteEmail=" + inviteEmail + ", generatedKey=" + generatedKey
				+ ", companyId=" + companyId + ", creationDate=" + creationDate + "]"+"\n";
	}	
}
