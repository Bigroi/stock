package com.bigroi.stock.service.impl;

import com.bigroi.stock.bean.db.UserComment;
import com.bigroi.stock.dao.UserCommentDao;
import com.bigroi.stock.service.UserCommentService;

import java.util.Date;
import java.util.List;

public class UserCommentServiceImpl implements UserCommentService {

    private final UserCommentDao userCommentDao;

    public UserCommentServiceImpl(UserCommentDao userCommentDao) {
        this.userCommentDao = userCommentDao;
    }

    @Override
    public void merge(UserComment userComment) {
        if (!userCommentDao.update(userComment)) {
            userCommentDao.add(userComment);
        }
    }

    @Override
    public List<UserComment> getComments(long companyId) {
        return userCommentDao.getComments(companyId);
    }

    @Override
    public UserComment getUserCommentByDealId(long dealId, long reporterId) {
        var userComment = userCommentDao.userCommentByDealId(dealId, reporterId);
        if (userComment == null) {
            userComment = new UserComment();
            userComment.setId(-1);
            userComment.setDealId(dealId);
            userComment.setCommentDate(new Date());
        }
        return userComment;
    }
}
