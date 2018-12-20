package com.bigroi.stock.bean.ui;

import com.bigroi.stock.bean.db.Comment;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Id;

public class CommentForUI {
	
	@Id
	private final long id;

	@Column(value = "label.comment.mark", responsivePriority=-1)
	private final byte mark;

	@Column(value = "label.comment.comment", responsivePriority=-2)
	private final String comment;

	public CommentForUI(Comment comment){
		this.id = comment.getId();
		this.mark = comment.getMark();
		this.comment = comment.getComment();
	}
}
