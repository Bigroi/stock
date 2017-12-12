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

	private static final String GET_USER_BY_LOGIN = "SELECT ID, LOGIN, PASSWORD, COMPANY_ID "
			+ "FROM USER "
			+ "WHERE LOGIN = ? ";

	private static final String GET_ALL_USERS = "SELECT ID, LOGIN, PASSWORD, COMPANY_ID "
			+ "FROM USER ";

	private static final String GET_USER_BY_LOGIN_AND_PASSWORD = "SELECT ID, LOGIN, PASSWORD, COMPANY_ID "
			+ "FROM USER "
			+ "WHERE LOGIN = ? AND PASSWORD = ? ";

	private static final String ADD_USERS_BY_ID = "INSERT INTO USER (ID, LOGIN, PASSWORD, COMPANY_ID) "
			+ " VALUES (?, ?, ?, ?) ";

	private static final String UPDATE_USERS_BY_ID = "UPDATE USER "
			+ "SET  LOGIN = ?, PASSWORD = ?, COMPANY_ID = ? "
			+ "WHERE ID = ? ";

	private static final String LOAD_USER_BY_JOIN_TABLES = "SELECT USER.ID, USER.COMPANY_ID, USER.LOGIN, USER.PASSWORD, USER_ROLE.ROLE "
			+ " FROM  USER "
			+ " INNER JOIN COMPANY "
			+ " ON USER.COMPANY_ID = COMPANY.ID AND COMPANY.`STATUS` = '" + CompanyStatus.VERIFIED +"' "
			+ " LEFT JOIN USER_ROLE "
			+ " ON USER.ID = USER_ROLE.USER_ID "
			+ " WHERE USER.LOGIN = ? ";

	private static final String GET_USER_BY_ID = "SELECT ID, LOGIN, PASSWORD, COMPANY_ID "
			+ "FROM USER "
			+ "WHERE ID = ?";
	
	private static final String GET_USER_ID =  " SELECT ID  FROM USER ";//MAX(id)
	
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
	public StockUser getByLoginWithRoles(String login) throws DaoException {
		StockUser user = new StockUser();
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<StockUser> list = template.query(LOAD_USER_BY_JOIN_TABLES, new RowMapper<StockUser>(){
			@Override
			public StockUser mapRow(ResultSet rs, int rowNum) throws SQLException {
				user.setId(rs.getLong("ID"));
				user.setCompanyId(rs.getLong("COMPANY_ID"));
				user.setLogin(rs.getString("LOGIN"));
				user.setPassword(rs.getString("PASSWORD"));
				String role = rs.getString("ROLE");
				if(role != null){
					user.addAuthority(new SimpleGrantedAuthority(role));
				}
				return user;
			}
		 }, login);
		if(list.size() == 0){
			return null;
		}else{
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
	public StockUser getUserId() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		StockUser user = new StockUser();
		List<StockUser> list =   template.query(GET_USER_ID, new RowMapper<StockUser>(){
			@Override
			public StockUser mapRow(ResultSet rs, int rowNum) throws SQLException {
				user.setId(rs.getLong("ID"));
				return user;
			}
		});
		if (list.size() == 0){
			return null;
		} else {
			return list.get(0);
		}
	}
}
