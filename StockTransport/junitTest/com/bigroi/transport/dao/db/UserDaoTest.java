package com.bigroi.transport.dao.db;

import org.junit.Test;

import com.bigroi.transport.dao.DaoException;
import com.bigroi.transport.dao.DaoFactory;

public class UserDaoTest {

	@Test
	public void getAll() throws DaoException{
		System.out.println(DaoFactory.getAllUsers().getAll());
	}
	
}
