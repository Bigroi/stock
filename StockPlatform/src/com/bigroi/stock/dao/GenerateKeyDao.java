package com.bigroi.stock.dao;

import com.bigroi.stock.bean.GeneratedKey;

public interface GenerateKeyDao {

	boolean �heckResetKey(String email, String key) throws DaoException;

	GeneratedKey generateKey() throws DaoException;
}
