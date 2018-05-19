package com.bigroi.stock.bean.db;

import com.bigroi.stock.bean.common.Role;

public class UserRole {
	
	private long userId;
	private Role role;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "UserRoles [userId=" + userId + ", role=" + role + "]";
	}
	
	
}
