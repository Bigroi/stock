package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.UserComment;
import com.bigroi.stock.dao.UserCommentDao;
import com.bigroi.stock.service.UserCommentService;

@Repository
public class UserCommentServiceImpl implements UserCommentService{
	
	@Autowired
	private UserCommentDao userCommentDao;

	@Override
	public void merge(UserComment userComment, long companyId) {
		if(userComment.getId() == -1){
			userComment.setCompanyId(companyId);
			userCommentDao.add(userComment);
		}else{
			userComment.setCompanyId(companyId);
			userCommentDao.update(userComment);
		}
	}

	@Override
	public List<UserComment> getComments(long companyId){
		return userCommentDao.getComments(companyId);
	}

	@Override
	public void delete(long id, long reporterId) {
		userCommentDao.delete(id, reporterId);
		
	}

	@Override
	public UserComment userCommentById(long id) {
		return userCommentDao.userCommentById(id);
	}
}
