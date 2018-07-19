package com.bigroi.stock.dao;

import com.bigroi.stock.bean.db.TempKey;

public interface GenerateKeyDao {

	boolean checkResetKey(String email, String key) throws DaoException;

	TempKey generateKey() throws DaoException;

	void deleteGenerateKeysByDate() throws DaoException;

	void deleteGenerateKey(String code);
}
