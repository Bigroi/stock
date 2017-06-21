package com.bigroi.stock.dao.template;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bigroi.stock.bean.User;


public class UserTemplate {

	private static DataSource datasource;

	

	public static DataSource getDatasource() {
		return datasource;
	}

	public static void setDatasource(DataSource datasource) {
		UserTemplate.datasource = datasource;
	}

	public User getUser() {
	
		JdbcTemplate template = new JdbcTemplate(datasource);
		User user = (User) template.query("SELECT * FROM user", new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getLong("id"));
				user.setLogin(rs.getString("login"));
				user.setPassword(rs.getString("password"));
				return user;
			}
		});
		return user;
	}

	public static User getUser(String login, String password) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		User user = template.queryForObject("SELECT * FROM user WHERE login = ?", new Object[] { login, password },
				new BeanPropertyRowMapper<User>(User.class));
		return user;
	}

	public static User getLoginPassword(String login, String password) {
		User user = getUser(login, password);
		if ((login != null && login.equals(user.getLogin()))
				&& (password != null && password.equals(user.getPassword()))) {
			return user;
		} else {
			return null;
		}

	}

	

}
