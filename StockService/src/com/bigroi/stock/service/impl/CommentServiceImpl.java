package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.Comment;
import com.bigroi.stock.dao.CommentDao;
import com.bigroi.stock.service.CommentService;

@Repository
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentDao commentDao;

	@Override
	public List<Comment> getCommentsByCompanyId(long companyId) {
		return commentDao.getCommentsByCompanyId(companyId);
	}

}
