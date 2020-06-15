package com.bigroi.stock.service;

import com.bigroi.stock.bean.db.UserComment;

import java.util.List;

public interface UserCommentService {

    void merge(UserComment userComment);

    List<UserComment> getComments(long companyId);

    UserComment getUserCommentByDealId(long dealId, long reporterId);

}
