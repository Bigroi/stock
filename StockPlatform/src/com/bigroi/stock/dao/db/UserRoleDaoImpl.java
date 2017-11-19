package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.bigroi.stock.bean.UserRole;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.UserRoleDao;

public class UserRoleDaoImpl implements UserRoleDao {
	
	private static final String ADD_USER_ROLE_BY_USER_ID = " INSERT INTO user_role (user_id, ROLE) VALUES(?,?) ";
	
	private DataSource datasource;

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void add(UserRole userRole) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_USER_ROLE_BY_USER_ID);
				ps.setLong(1,userRole.getUserId());
				ps.setString(2, userRole.getRole().name());
				return ps;
			}
		});
		
	}

	

}
