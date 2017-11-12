package com.bigroi.stock.util;

public class TmprUserLoad {
	
	private String login;
	private String password;
	private String status;
	private String role;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "TmprUserLoad [login=" + login + ", password=" + password + ", status=" + status + ", role=" + role
				+ "]";
	}
	
}
