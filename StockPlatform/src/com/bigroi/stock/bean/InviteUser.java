package com.bigroi.stock.bean;

public class InviteUser {

	private long id;
	private String inviteEmail;
	private long companyId;
	private long keysId;

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

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getKeysId() {
		return keysId;
	}

	public void setKeysId(long keysId) {
		this.keysId = keysId;
	}

	@Override
	public String toString() {
		return "InviteUser [id=" + id + ", inviteEmail=" + inviteEmail + ", companyId=" + companyId + ", keysId="
				+ keysId + "]";
	}
}
