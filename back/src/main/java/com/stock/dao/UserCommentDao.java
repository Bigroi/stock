package com.stock.dao;

import com.stock.entity.business.UserCommentRecord;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RegisterBeanMapper(UserCommentRecord.class)
public interface UserCommentDao {

    @SqlQuery("SELECT * FROM USER_COMMENT WHERE COMPANY_ID = :companyId")
    List<UserCommentRecord> getByCompanyId(@Bind("companyId") UUID companyId);

    @SqlQuery("SELECT * FROM USER_COMMENT WHERE DEAL_ID = :dealId AND REPORTER_ID = :reporterId")
    Optional<UserCommentRecord> getByDealIdAndReporterId(
            @Bind("dealId") UUID dealId,
            @Bind("reporterId") UUID reporterId
    );

    @SqlUpdate("INSERT INTO USER_COMMENT (ID, COMPANY_ID, REPORTER_ID, MARK, COMMENT, COMMENT_DATE, DEAL_ID) " +
            "VALUES (:id, :companyId, :reporterId, :mark, :comment, :commentDate, :dealId)")
    boolean create(@BindBean UserCommentRecord newComment);

    @SqlUpdate("UPDATE USER_COMMENT " +
            "SET MARK = :mark, COMMENT = :comment " +
            "WHERE DEAL_ID = :dealId AND REPORTER_ID = :reporterId")
    boolean updateByDealIdAndReporterId(
            @Bind("dealId") UUID dealId,
            @Bind("reporterId") UUID reporterId,
            @Bind("comment") String comment,
            @Bind("mark") int mark
    );
}
