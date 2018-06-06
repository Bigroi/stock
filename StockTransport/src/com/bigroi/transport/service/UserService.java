package com.bigroi.transport.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.bigroi.transport.bean.db.TransUser;

public interface UserService extends UserDetailsService {
	
	List<TransUser> getAll () throws ServiceException;

}
