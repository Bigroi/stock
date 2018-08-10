package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.db.Email;

public interface EmailDao {

	List<Email> getAll();

	void add(Email email);

	boolean deleteById(long id);

}
