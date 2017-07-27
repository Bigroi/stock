package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.Email;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.EmailDao;

public class EmailDaoImpl implements EmailDao {

	private static final String GET_ALL_EMAILS = "SELECT id, to_email, " 
	        + " email_subject, email_text FROM email ";

	private static final String ADD_EMAILS_BY_ID = "INSERT INTO email " 
	        + " (id, to_email, email_subject, email_text) "
			+ " VALUES (?, ?, ?, ?) ";

	private static final String DELETE_EMAILS_BY_ID = " DELETE FROM email "
			+ " WHERE id =  ? ";

	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public List<Email> getAll() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Email> list = template.query(GET_ALL_EMAILS, 
				new BeanPropertyRowMapper<Email>(Email.class));
		return list;
	}

	@Override
	public void add(Email email) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_EMAILS_BY_ID, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, email.getId());
				ps.setString(2, email.getToEmail());
				ps.setString(3, email.getEmailSubject());
				ps.setString(4, email.getEmailText());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		email.setId(id);
	}

	@Override
	public boolean deleteById(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(DELETE_EMAILS_BY_ID, id) == 1;

	}

}
