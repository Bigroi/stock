package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.Email;
import com.bigroi.stock.dao.EmailDao;

@Repository
public class EmailDaoImpl implements EmailDao {

	private static final String GET_ALL_EMAILS = 
			" SELECT ID, RECIPIENT, SUBJECT, BODY, FILE, FILE_NAME FROM EMAIL ";

	private static final String ADD_EMAILS = "INSERT INTO EMAIL " 
	        + " (RECIPIENT, SUBJECT, BODY, FILE, FILE_NAME) "
			+ " VALUES (?, ?, ?, ?, ?) ";

	private static final String DELETE_EMAILS_BY_ID = " DELETE FROM EMAIL "
			+ " WHERE ID =  ? ";

	@Autowired
	private DataSource datasource;

	@Override
	public List<Email> getAll() {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_ALL_EMAILS, 
				new BeanPropertyRowMapper<Email>(Email.class));
	}

	@Override
	public void add(Email email) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_EMAILS, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, email.getRecipient());
				ps.setString(2, email.getSubject());
				ps.setString(3, email.getBody());
				ps.setBytes(4, email.getFile());
				ps.setString(5, email.getFileName());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		email.setId(id);
	}

	@Override
	public boolean deleteById(long id) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(DELETE_EMAILS_BY_ID, id) == 1;

	}

}
