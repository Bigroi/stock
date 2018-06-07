package com.bigroi.transport.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.bigroi.transport.bean.db.TransUser;

@Service
public interface UserService extends UserDetailsService {
	
	List<TransUser> getAll () throws ServiceException;

}
