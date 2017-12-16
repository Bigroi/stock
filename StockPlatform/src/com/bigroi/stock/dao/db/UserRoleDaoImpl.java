package com.bigroi.stock.dao.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import com.bigroi.stock.bean.UserRole;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.UserRoleDao;

public class UserRoleDaoImpl implements UserRoleDao {
	
	private static final String ADD_USER_ROLE_BY_USER_ID = " INSERT INTO USER_ROLE (USER_ID, ROLE) VALUES(?,?) ";
	
	private DataSource datasource;

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void add(List<UserRole> userRoles) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.batchUpdate(ADD_USER_ROLE_BY_USER_ID, userRoles, userRoles.size(), 
				new ParameterizedPreparedStatementSetter<UserRole>() {
			@Override
			public void setValues(PreparedStatement ps, UserRole userRole) throws SQLException {
				ps.setLong(1, userRole.getUserId());
				ps.setString(2, userRole.getRole().name());
			}
		});
	}
}
