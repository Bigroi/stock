package com.bigroi.stock.dao.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.UserComment;
import com.bigroi.stock.dao.UserCommentDao;

@Repository
public class UserCommentDaoImpl implements UserCommentDao {

	private static final String ADD = 
			"INSERT INTO USER_COMMENT (COMPANY_ID, REPORTER_ID, MARK, COMMENT, COMMENT_DATE) "
			+ " VALUES (?, ?, ?, ?, ?)";
	
	@Autowired
	private DataSource datasource;
	
	@Override
	public void add(UserComment userComment) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(ADD, 
				userComment.getCompanyId(),
				userComment.getReporterId(),
				userComment.getMark(),
				userComment.getComment(),
				userComment.getCommentDate());
	}

}
