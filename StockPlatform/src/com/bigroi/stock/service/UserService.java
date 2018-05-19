package com.bigroi.stock.service;


import org.springframework.security.core.userdetails.UserDetailsService;

import com.bigroi.stock.bean.db.StockUser;

public interface UserService extends UserDetailsService{

	void addUser(StockUser user)throws ServiceException;
	
	void update(StockUser user) throws ServiceException;
	
	void deleteGenerateKeys() throws ServiceException;
	
	void sendLinkResetPassword(String username) throws ServiceException;
	
	boolean checkCodeAndEmail(String email, String code) throws ServiceException;
	
	void changePassword(String username) throws ServiceException;

	Object getByUsername(String username) throws ServiceException;
	
}
