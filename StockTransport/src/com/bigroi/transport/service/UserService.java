package com.bigroi.transport.service;

import java.util.List;

import com.bigroi.transport.bean.User;

public interface UserService {
	
	List<User> getAll () throws ServiceException;

}
