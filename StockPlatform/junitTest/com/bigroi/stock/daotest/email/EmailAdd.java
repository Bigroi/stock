package com.bigroi.stock.daotest.email;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigroi.stock.bean.Email;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class EmailAdd {

	private static Email email;

	@BeforeClass
	public static void init() {
		email = new Email();
		email.setId(7);
		email.setToEmail("@todo");
		email.setEmailSubject("subj");
		email.setEmailText("text");
	}

	@Test
	public void add() throws DaoException {
		DaoFactory.getEmailDao().add(email);
		Assert.assertNotNull(email);
	}

	@BeforeClass
	public static void delete() throws DaoException {
		DaoFactory.getEmailDao().deleteById(email.getId());
	}

}
