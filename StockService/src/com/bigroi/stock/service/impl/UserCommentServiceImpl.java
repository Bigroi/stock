package com.bigroi.stock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.bean.db.UserComment;
import com.bigroi.stock.dao.UserCommentDao;
import com.bigroi.stock.service.UserCommentService;

public class UserCommentServiceImpl implements UserCommentService{
	
	@Autowired
	private UserCommentDao userComment;

	@Override
	public void merge(UserComment comment, long companyId) {
		if(comment.getId() == -1){
			comment.setCompanyId(companyId);
			userComment.add(comment);
		}else{
			comment.setCompanyId(companyId);
			userComment.update(comment);
		}
	}

}
