package com.bigroi.stock.service;

import com.bigroi.stock.bean.db.UserComment;

public interface UserCommentService {
	
	void merge(UserComment comment, long companyId);

}
