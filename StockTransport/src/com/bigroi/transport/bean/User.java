package com.bigroi.transport.bean;

import java.util.Date;

public class User {

	private long id;
	private String username;
	private String password;
	private long companyId;
	private long keysId;
	private int loginCount;
	private Date lastLogin;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public int getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", companyId=" + companyId
				+ ", keysId=" + keysId + ", loginCount=" + loginCount + ", lastLogin=" + lastLogin + "]";
	}
}
