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

import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.UserDao;

public class UserDaoImpl implements UserDao {

	private static final String GET_USER_BY_USERNAME = " SELECT ID, USERNAME, PASSWORD, COMPANY_ID "
			+ " FROM USER "
			+ " WHERE USERNAME = ? ";

	private static final String GET_ALL_USERS = " SELECT ID, USERNAME, PASSWORD, COMPANY_ID "
			+ " FROM USER ";

	private static final String GET_USER_BY_USERNAME_AND_PASSWORD = " SELECT ID, USERNAME, PASSWORD, COMPANY_ID "
			+ " FROM USER WHERE USERNAME = ? AND PASSWORD = ? ";

	private static final String ADD_USERS_BY_ID = "INSERT INTO USER (ID, USERNAME, PASSWORD, COMPANY_ID) "
			+ " VALUES (?, ?, ?, ?) ";

	private static final String UPDATE_USERS_BY_ID = "UPDATE USER "
			+ " SET  USERNAME = ?, PASSWORD = ?, COMPANY_ID = ? "
			+ " WHERE ID = ? ";

	private static final String LOAD_USER_BY_JOIN_TABLES = "SELECT USER.ID, USER.COMPANY_ID, USER.USERNAME, USER.PASSWORD, "
			+ " USER_ROLE.ROLE "
			+ " FROM  USER "
			+ " INNER JOIN COMPANY "
			+ " ON USER.COMPANY_ID = COMPANY.ID AND COMPANY.`STATUS` = '" + CompanyStatus.VERIFIED.name() +"' "
			+ " LEFT JOIN USER_ROLE "
			+ " ON USER.ID = USER_ROLE.USER_ID "
			+ " WHERE USER.USERNAME = ? ";

	private static final String GET_USER_BY_ID = "SELECT ID, USERNAME, PASSWORD, COMPANY_ID "
			+ " FROM USER "
			+ " WHERE ID = ? ";
	
	private static final String UPDATE_PASSWORD_BY_USERNAME = "UPDATE USER SET PASSWORD = ? WHERE USERNAME = ? ";
	
	private static final String UPDATE_KEYS_BY_USERNAME = "UPDATE USER SET keys_id = ? WHERE USERNAME = ? ";
	
	private static final String UPDATE_COUNT_LOGINS_AND_TIME = " UPDATE USER SET LOGIN_COUNT = LOGIN_COUNT + 1, "
			+ " LAST_LOGIN = CURRENT_TIMESTAMP() WHERE ID = ? ";
	
	private DataSource datasource;

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	public List<StockUser> getAllUser() {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<StockUser> users = template.query(GET_ALL_USERS, new BeanPropertyRowMapper<StockUser>(StockUser.class));
		return users;
	}

	public StockUser getByUsernameAndPassword(String username, String password) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<StockUser> users = template.query(GET_USER_BY_USERNAME_AND_PASSWORD, new BeanPropertyRowMapper<StockUser>(StockUser.class),
				username, password);
		if (users.size() == 0) {
			return null;
		} else {
			return users.get(0);
		}
	}

	@Override
	public StockUser getByUsername(String username) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<StockUser> users = template.query(GET_USER_BY_USERNAME, new BeanPropertyRowMapper<StockUser>(StockUser.class), username);
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
				ps.setString(2, user.getUsername());
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
		return template.update(UPDATE_USERS_BY_ID, user.getUsername(), user.getPassword(), user.getCompanyId(),
				user.getId()) == 1;
	}

	@Override
	public StockUser getByUsernameWithRoles(String username) throws DaoException {
		StockUser user = new StockUser();
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<StockUser> list = template.query(LOAD_USER_BY_JOIN_TABLES, new RowMapper<StockUser>(){
			@Override
			public StockUser mapRow(ResultSet rs, int rowNum) throws SQLException {
				user.setId(rs.getLong("ID"));
				user.setCompanyId(rs.getLong("COMPANY_ID"));
				user.setUsername(rs.getString("USERNAME"));
				user.setPassword(rs.getString("PASSWORD"));
				String role = rs.getString("ROLE");
				if(role != null){
					user.addAuthority(new SimpleGrantedAuthority(role));
				}
				return user;
			}
		 }, username);
		if(list.size() == 0){
			return null;
		}else{
			updateCountAndLastTimeForLogin(user);
			return list.get(0);
		}
	}
	
	@Override
	public StockUser getById(long id) throws DaoException {
		try{
			JdbcTemplate template = new JdbcTemplate(datasource);
			List<StockUser> list = template.query(GET_USER_BY_ID, new BeanPropertyRowMapper<StockUser>(StockUser.class), id);
			if (list.size() == 0){
				return null;
			} else {
				return list.get(0);
			}
		}catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public boolean updatePassword(StockUser user) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_PASSWORD_BY_USERNAME, user.getPassword(),user.getUsername()) == 1;
	}

	@Override
	public boolean updateForKeyId(StockUser user) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_KEYS_BY_USERNAME, user.getKeysId(), user.getUsername()) == 1;
	}

	private boolean updateCountAndLastTimeForLogin(StockUser user) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_COUNT_LOGINS_AND_TIME, user.getId()) == 0;
	}
}
