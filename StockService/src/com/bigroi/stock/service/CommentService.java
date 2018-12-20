package com.bigroi.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bigroi.stock.bean.db.Comment;

@Service
public interface CommentService {
	List<Comment> getCommentsByCompanyId(long companyId);
}
