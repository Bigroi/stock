package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigroi.stock.bean.db.UserComment;
import com.bigroi.stock.dao.UserCommentDao;
import com.bigroi.stock.service.UserCommentService;

public class UserCommentServiceImpl implements UserCommentService{
	
	@Autowired
	private UserCommentDao userCommentDao;

	@Override
	public void merge(UserComment comment, long companyId) {
		if(comment.getId() == -1){
			comment.setCompanyId(companyId);
			userCommentDao.add(comment);
		}else{
			comment.setCompanyId(companyId);
			userCommentDao.update(comment);
		}
	}

	@Override
	public List<UserComment> getComments(long companyId){
		return userCommentDao.getComments(companyId);
	}
}
