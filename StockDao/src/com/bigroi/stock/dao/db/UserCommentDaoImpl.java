package com.bigroi.stock.dao.db;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.UserComment;
import com.bigroi.stock.dao.UserCommentDao;

@Repository
public class UserCommentDaoImpl implements UserCommentDao {

	private static final String ADD = 
			"INSERT INTO USER_COMMENT (COMPANY_ID, REPORTER_ID, MARK, COMMENT, COMMENT_DATE) "
			+ " VALUES (?, ?, ?, ?, ?)";
	
	private static final String UPDATE = 
			"UPDATE USER_COMMENT SET COMPANT_ID = ?, REPORTER_ID = ?, MARK = ?, "
			+ " COMMENT = ?, COMMENT_DATE = ? WHERE ID = ?";
	
	private static final String DELETE = 
			"DELETE FROM USER_COMMENT WHERE ID = ? AND REPORTER_ID = ? ";
	
	private static final String GET_BY_COMPANY_ID = 
			"SELECT MARK, COMMENT, COMMENT_DATE FROM USER_COMMENT WHERE COMPANT_ID = ? ";
	
	private static final String GET_USER_COMMENT_BY_ID =
			"SELECT MARK, COMMENT, COMMENT_DATE FROM USER_COMMENT WHERE ID = ? ";
	
	@Autowired
	private DataSource datasource;
	
	@Override
	public List<UserComment> getComments(long companyId){
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_BY_COMPANY_ID, 
				new BeanPropertyRowMapper<UserComment>(UserComment.class),
				companyId);
	}
	
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

	@Override
	public boolean update(UserComment comment) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE, 
				comment.getCompanyId(),
				comment.getReporterId(),
				comment.getMark(),
				comment.getComment(),
				comment.getCommentDate(),
				comment.getCompanyId()) == 1;
	}

	@Override
	public boolean delete(long id, long reporterId) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(DELETE, id, reporterId) == 1;
	}

	@Override
	public UserComment userCommentById(long id) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<UserComment> userComment = template.query(GET_USER_COMMENT_BY_ID, 
				new BeanPropertyRowMapper<UserComment>(UserComment.class), id);
		if(userComment.isEmpty()){
		return null;
		}else{
			return userComment.get(0);
		}
	}

}
