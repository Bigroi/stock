package com.bigroi.stock.bean;

import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Id;

public class User {
	
	@Id
	private long id;
	
	@Column("skock.table.tender.login")
	private String login;
	private String password;
	private String role;
	
	@Column("skock.table.tender.companyId")
	private long companyId;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
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
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password + ", role="
				+ role + ", companyId=" + companyId + "]";
	}

	

}