package com.bigroi.stock.bean.db;

public class Comment {

	private long id;
	private byte mark;
	private String comment;
	private long companyId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public byte getMark() {
		return mark;
	}
	public void setMark(byte mark) {
		this.mark = mark;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	@Override
	public String toString() {
		return "Comment [id=" + id + ", mark=" + mark + ", comment=" + comment + ", companyId=" + companyId + "]";
	}
}
