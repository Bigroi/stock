package com.bigroi.stock.dao;

import com.bigroi.stock.bean.db.UserComment;

public interface UserCommentDao {

	void add(UserComment userComment);

	boolean update(UserComment comment);
	
	boolean delete(long id, long companyId);
}
