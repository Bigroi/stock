package com.bigroi.transport.bean.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class TransUser  implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String username;
	private String password;
	private long companyId;
	private long keysId;
	private int loginCount;
	private Date lastLogin;
	private List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
	private Company company;
	
	
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
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", companyId=" + companyId
				+ ", keysId=" + keysId + ", loginCount=" + loginCount + ", lastLogin=" + lastLogin + "]";
	}
	
	public void addAuthority(GrantedAuthority grant){
		grantedAuthorities.add(grant);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
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
