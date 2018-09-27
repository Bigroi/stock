package com.bigroi.stock.bean.ui;

import java.util.Date;

import com.bigroi.stock.bean.db.UserComment;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Id;

public class UserCommentForUI {
	
	@Id
	private long id;
	@Id
	private long companyId;
	@Id
	private long reporterId;

	@Column(value = "label.comment.mark", responsivePriority=-1)
	private int mark;

	@Column(value = "label.comment.comment", responsivePriority=-2)
	private String comment;

	@Column(value = "label.comment.date", responsivePriority=-3)
	private Date commentDate;
	
	public UserCommentForUI(UserComment userComment) {
		this.id = userComment.getId();
		this.companyId = userComment.getCompanyId();
		this.reporterId = userComment.getReporterId();
		this.mark = userComment.getMark();
		this.comment = userComment.getComment();
		this.commentDate = userComment.getDate();
	}

}
