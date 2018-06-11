package com.bigroi.stock.dao;

import com.bigroi.stock.bean.db.GeneratedKey;

public interface GenerateKeyDao {

	boolean ñheckResetKey(String email, String key) throws DaoException;

	GeneratedKey generateKey() throws DaoException;

	void deleteGenerateKeysByDate() throws DaoException;
}
