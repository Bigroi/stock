package com.bigroi.stock.service;

import java.util.List;

import com.bigroi.stock.bean.db.UserComment;

public interface UserCommentService {
	
	void merge(UserComment comment, long companyId);
	
	List<UserComment> getComments(long companyId);

}
