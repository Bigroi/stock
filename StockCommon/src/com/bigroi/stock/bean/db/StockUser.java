package com.bigroi.stock.bean.db;

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
	private String passwordRepeat;
	private List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
	private long companyId;
	private Company company;
	private long keyId;
	
	public long getId() {
		return id;
	}
	
	public String getPasswordRepeat() {
		return passwordRepeat;
	}
	
	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
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

	public long getKeyId() {
		return keyId;
	}

	public void setKeyId(long keyId) {
		this.keyId = keyId;
	}

	@Override
	public String toString() {
		return "StockUser [id=" + id + ", username=" + username + ", password=" + password + ", grantedAuthorities="
				+ grantedAuthorities + ", companyId=" + companyId + ", keysId=" + keyId + "]";
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

	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public StockUser cloneUser(){
		StockUser newOne = new StockUser();
		newOne.company = this.company;
		newOne.companyId = this.companyId;
		newOne.grantedAuthorities = this.grantedAuthorities;
		newOne.id = this.id;
		newOne.keyId = this.keyId;
		newOne.password = this.password;
		newOne.passwordRepeat = this.passwordRepeat;
		newOne.username = this.username;
		return newOne;
	}
}