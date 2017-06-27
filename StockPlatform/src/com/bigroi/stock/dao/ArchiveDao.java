package com.bigroi.stock.dao;

import com.bigroi.stock.bean.Archive;

public interface ArchiveDao {
	
    void add(Archive archive) throws DaoException;
	
	void delete(long id) throws DaoException;
	
	void update(long id, Archive archive) throws DaoException;
}
