package com.bigroi.transport.dao.db;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bigroi.transport.bean.User;
import com.bigroi.transport.dao.DaoException;
import com.bigroi.transport.dao.UserDao;

public class UserDaoImpl implements UserDao {
	
	private static final String  GET_ALL_USERS = " SELECT ID, USERNAME, PASSWORD FROM USER ";
	
	public DataSource datasource;
	
	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}
	

	@Override
	public List<User> getAll() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<User> users = template.query(GET_ALL_USERS, new BeanPropertyRowMapper<User>(User.class));
		return users;
	}

}
