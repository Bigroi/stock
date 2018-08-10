package com.bigroi.stock.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.bigroi.stock.bean.db.StockUser;

@Service
public interface UserService extends UserDetailsService{

	void addUser(StockUser user);
	
	void update(StockUser user);
	
	void deleteGenerateKeys();
	
	void sendLinkResetPassword(String username);
	
	boolean changePassword(String email, String code);

	StockUser getByUsername(String username);
	
}
