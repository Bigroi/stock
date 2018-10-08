package com.bigroi.stock.service.impl;

import java.util.Date;
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
	public void merge(UserComment userComment) {
		if(!userCommentDao.update(userComment)){
			userCommentDao.add(userComment);
		}
	}

	@Override
	public List<UserComment> getComments(long companyId){
		return userCommentDao.getComments(companyId);
	}

	@Override
	public UserComment getUserCommentByDealId(long dealId, long reporterId) {
		UserComment userComment = userCommentDao.userCommentByDealId(dealId, reporterId);
		if(userComment == null){
			userComment = new UserComment();
			userComment.setId(-1);
			userComment.setDealId(dealId);
			userComment.setCommentDate(new Date());
		}
		return userComment;
	}
}
