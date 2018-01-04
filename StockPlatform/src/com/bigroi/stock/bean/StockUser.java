package com.bigroi.stock.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class StockUser implements UserDetails {
	
	private static final long serialVersionUID = 3098775983055977418L;

	private long id;
	
	private String username;
	private String password;
	private List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
	
	private long companyId;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
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
	
	public void addAuthority(GrantedAuthority grant){
		grantedAuthorities.add(grant);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", grantedAuthorities="
				+ grantedAuthorities + ", companyId=" + companyId + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return grantedAuthorities;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}