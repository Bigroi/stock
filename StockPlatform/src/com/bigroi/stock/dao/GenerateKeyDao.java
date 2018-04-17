package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.GeneratedKey;

public interface GenerateKeyDao {

	boolean ñheckResetKey(String email, String key) throws DaoException;

	GeneratedKey generateKey() throws DaoException;

	GeneratedKey getGeneratedKeyById(long id) throws DaoException;

	List<GeneratedKey> getGenerateKeysByDate() throws DaoException;

	boolean deleteGenerateKeysByDate() throws DaoException;
}
