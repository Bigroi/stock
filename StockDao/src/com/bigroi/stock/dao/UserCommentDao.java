package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.db.UserComment;

public interface UserCommentDao {

	void add(UserComment userComment);

	boolean update(UserComment comment);
	
	boolean delete(long id, long reporterId);
	
	List<UserComment> getComments(long reporterId);

	UserComment userCommentById(long id);
}
