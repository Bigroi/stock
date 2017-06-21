package com.bigroi.stock.dao.crud;


import java.util.List;

import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.exceptions.DaoExeptions;

public interface UserInterface {
	
	public List<User> getAll() throws DaoExeptions;

	public User getId(long id) throws DaoExeptions;

	public void update(long id, User user) throws DaoExeptions;

	public void delete(long id) throws DaoExeptions;

	public void add(User user) throws DaoExeptions;
}
