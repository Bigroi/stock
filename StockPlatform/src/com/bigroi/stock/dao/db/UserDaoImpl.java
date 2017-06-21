package com.bigroi.stock.dao.db;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.UserDao;


public class UserDaoImpl implements UserDao{

	private static final String GET_USER_BY_LOGIN = 
			"SELECT id, login, password FROM user "
			+ "WHERE login = ?";
	
	private static final String GET_ALL_USERS = 
			"SELECT id, login, password FROM user";
	
	private static final String GET_USER_BY_LOGIN_AND_PASSWORD = 
			"SELECT id, login, password FROM user "
			+ "WHERE login = ? AND password = ?";
	
	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	public List<User> getAllUser() {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<User> users = template.query(GET_ALL_USERS, new BeanPropertyRowMapper<User>(User.class));
		return users;
	}

	public User getByLoginAndPassword(String login, String password) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<User> users = template.query(GET_USER_BY_LOGIN_AND_PASSWORD,
				new BeanPropertyRowMapper<User>(User.class), login, password);
		if (users.size() == 0){
			return null;
		} else {
			return users.get(0);
		}
	}

	@Override
	public User getByLogin(String login) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<User> users = template.query(GET_USER_BY_LOGIN,
				new BeanPropertyRowMapper<User>(User.class), login);
		if (users.size() == 0){
			return null;
		} else {
			return users.get(0);
		}
	}

}
