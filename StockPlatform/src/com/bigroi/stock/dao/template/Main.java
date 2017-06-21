package com.bigroi.stock.dao.template;

import java.sql.SQLException;

import com.bigroi.stock.dao.exceptions.DaoExeptions;

public class Main {

	public static void main(String[] args) throws DaoExeptions, SQLException {
		UserTemplate user = new UserTemplate();
		user.getAll();
		//user.getDatasource();
		System.out.println("out: "+user);

	}

}
