package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.db.Comment;

public interface CommentDao {

	List<Comment> getCommentsByCompanyId(long companyId);
}
