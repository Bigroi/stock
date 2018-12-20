package com.bigroi.stock.dao.db;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.Comment;
import com.bigroi.stock.dao.CommentDao;

@Repository
public class CommentDaoImpl implements CommentDao{
	
	private static final String GET_COMMENTS_BY_COMPANY_ID = 
			" SELECT ID, MARK, COMMENT "
			+ "FROM USER_COMMENT "
			+ "WHERE COMPANY_ID = ? ";
	
	@Autowired
	private DataSource datasource;

	@Override
	public List<Comment> getCommentsByCompanyId(long companyId) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Comment> list = template.query(GET_COMMENTS_BY_COMPANY_ID, 
				new BeanPropertyRowMapper<Comment>(Comment.class), companyId);
		return list;
	}
}
