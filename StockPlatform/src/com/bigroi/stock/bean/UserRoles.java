package com.bigroi.stock.bean;

public class UserRoles {
	
	private long userId;
	private String role;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "UserRoles [userId=" + userId + ", role=" + role + "]";
	}
	
	
}
