package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.UserDao;

public class UserDaoImpl implements UserDao {

	private static final String GET_USER_BY_LOGIN = "SELECT id, login, password, company_Id FROM user "
			+ " WHERE login = ? ";

	private static final String GET_ALL_USERS = "SELECT id, login, password, company_Id FROM user ";

	private static final String GET_USER_BY_LOGIN_AND_PASSWORD = "SELECT id, login, password, company_Id FROM user "
			+ " WHERE login = ? AND password = ? ";

	private static final String ADD_USERS_BY_ID = "INSERT INTO user (id, login, password, company_Id) "
			+ " VALUES (?, ?, ?, ?) ";

	private static final String UPDATE_USERS_BY_ID = "UPDATE user SET  login = ?, password = ?, "
			+ " company_Id = ? WHERE id= ? ";

	private static final String LOAD_USER_BY_JOIN_TABLES = "SELECT user.login, user.password, user_role.role fROM  user "
			+ " INNER JOIN company ON user.company_Id = company.id AND company.`status` = '" + CompanyStatus.VERIFIED +"' "
			+ " LEFT JOIN user_role ON user.id = user_role.user_id WHERE user.login = ? ";

	private DataSource datasource;

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	public List<StockUser> getAllUser() {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<StockUser> users = template.query(GET_ALL_USERS, new BeanPropertyRowMapper<StockUser>(StockUser.class));
		return users;
	}

	public StockUser getByLoginAndPassword(String login, String password) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<StockUser> users = template.query(GET_USER_BY_LOGIN_AND_PASSWORD, new BeanPropertyRowMapper<StockUser>(StockUser.class),
				login, password);
		if (users.size() == 0) {
			return null;
		} else {
			return users.get(0);
		}
	}

	@Override
	public StockUser getByLogin(String login) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<StockUser> users = template.query(GET_USER_BY_LOGIN, new BeanPropertyRowMapper<StockUser>(StockUser.class), login);
		if (users.size() == 0) {
			return null;
		} else {
			return users.get(0);
		}
	}

	@Override
	public void add(StockUser user) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_USERS_BY_ID, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setLong(1, user.getId());
				ps.setString(2, user.getLogin());
				ps.setString(3, user.getPassword());
				ps.setLong(4, user.getCompanyId());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		user.setId(id);
	}

	@Override
	public boolean update(StockUser user) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_USERS_BY_ID, user.getLogin(), user.getPassword(), user.getCompanyId(),
				user.getId()) == 1;
	}

	@Override
	public UserDetails loadUser(String username) throws DaoException {
		StockUser user = new StockUser();
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<StockUser> list = template.query(LOAD_USER_BY_JOIN_TABLES, new RowMapper<StockUser>(){

			@Override
			public StockUser mapRow(ResultSet rs, int rowNum) throws SQLException {
				user.setLogin(rs.getString("login"));
				user.setPassword(rs.getString("password"));
				String role = rs.getString("role");
				if(role != null){
					user.addAuthority(new SimpleGrantedAuthority(role));
				}
				return user;
			}
			
		}, username);
		if(list.size() == 0){
			return null;
		}else{
			return list.get(0);
		}

		
	}

}
