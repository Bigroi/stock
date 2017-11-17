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

	private static final String GET_ALL_EMAILS = "SELECT ID, TO_EMAIL, " 
	        + " EMAIL_SUBJECT, EMAIL_TEXT FROM EMAIL ";

	private static final String ADD_EMAILS = "INSERT INTO EMAIL " 
	        + " (TO_EMAIL, EMAIL_SUBJECT, EMAIL_TEXT) "
			+ " VALUES (?, ?, ?) ";

	private static final String DELETE_EMAILS_BY_ID = " DELETE FROM EMAIL "
			+ " WHERE ID =  ? ";

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
		return template.query(GET_ALL_EMAILS, 
				new BeanPropertyRowMapper<Email>(Email.class));
	}

	@Override
	public void add(Email email) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_EMAILS, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, email.getToEmail());
				ps.setString(2, email.getEmailSubject());
				ps.setString(3, email.getEmailText());
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
