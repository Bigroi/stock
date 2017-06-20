/*package com.bigroi.stock.dao.template;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bigroi.stock.bean.User;
import com.bigroi.stock.controller.AppContext;
import com.bigroi.stock.dao.crud.UserInterface;
import com.bigroi.stock.dao.exceptions.DaoExeptions;
import com.bigroi.stock.dao.sqlQueries.UserSQLQueries;

public class UserTemplate implements UserSQLQueries, UserInterface {

	private DataSource datasource = (DataSource) AppContext.getContext().getBean("userDAO");

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	
	@Override
	public List<User> getAll() throws DaoExeptions {
		JdbcTemplate template = new JdbcTemplate(datasource);
		
		return template.query(SELECT_ALL_USER, new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user = (User) AppContext.getContext().getBean("userDAO");
				user.setId(rs.getLong("id"));
				user.setLogin(rs.getString("login"));
				user.setPassword(rs.getString("password"));
				return user;
			}

		});
	}

	@Override
	public User getId(long id) throws DaoExeptions {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(long id, User user) throws DaoExeptions {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(long id) throws DaoExeptions {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(User user) throws DaoExeptions {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return "UserTemplate [datasource=" + datasource + "]";
	}

}
*/