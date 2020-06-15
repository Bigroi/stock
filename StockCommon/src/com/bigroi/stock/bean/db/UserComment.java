package com.bigroi.stock.bean.db;

import java.io.Serializable;
import java.util.Date;

public class UserComment implements Serializable {

    private static final long serialVersionUID = -2101255437447756054L;

    private long id;
    private long companyId;
    private long reporterId;
    private int mark;
    private String comment;
    private Date commentDate;
    private long dealId;

    public long getDealId() {
        return dealId;
    }

    public void setDealId(long dealId) {
        this.dealId = dealId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getReporterId() {
        return reporterId;
    }

    public void setReporterId(long reporterId) {
        this.reporterId = reporterId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getComment() {
        return comment;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public Date getDate() {
        return new Date();
    }
}
