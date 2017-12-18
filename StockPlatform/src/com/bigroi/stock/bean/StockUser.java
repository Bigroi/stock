package com.bigroi.stock.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Id;

public class StockUser implements UserDetails {
	
	private static final long serialVersionUID = 3098775983055977418L;

	@Id
	private long id;
	
	@Column("stock.table.tender.login")
	private String login;
	private String password;
	private List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
	
	
	@Column("stock.table.tender.companyId")
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
	
	public void addAuthority(GrantedAuthority grant){
		grantedAuthorities.add(grant);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password + ", grantedAuthorities="
				+ grantedAuthorities + ", companyId=" + companyId + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return grantedAuthorities;
	}

	@Override
	public String getUsername() {
		return login;
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

	

}