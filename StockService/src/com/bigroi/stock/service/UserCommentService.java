package com.bigroi.stock.service;

import java.util.List;

import com.bigroi.stock.bean.db.UserComment;

public interface UserCommentService {
	
	void merge(UserComment userComment, long companyId);
	
	List<UserComment> getComments(long companyId);

	void delete(long id, long reporterId);

	UserComment userCommentById(long id);

}
