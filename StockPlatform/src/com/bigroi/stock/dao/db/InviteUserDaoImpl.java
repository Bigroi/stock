package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.InviteUser;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.InviteUserDao;

public class InviteUserDaoImpl implements InviteUserDao {
	
	private static final String ADD_INVITE_USER = " INSERT INTO INVITE_USER  "
			+ " (INVITE_EMAIL, GENERATED_KEY, COMPANY_ID, CREATION_DATE) VALUES (?, ?, ?, ?) ";
	
	private static final String GET_INVITE_USER_BY_CODE = " SELECT ID, INVITE_EMAIL, GENERATED_KEY,"
			+ " COMPANY_ID FROM INVITE_USER WHERE GENERATED_KEY = ? ";
	
	public static final  String DELETE_INVITE_USER_BY_CODE = " DELETE FROM INVITE_USER  WHERE GENERATED_KEY = ? ";
	
	public static final String GET_ALL_INVITE_USER_BY_DATE = " SELECT GENERATED_KEY, CREATION_DATE FROM INVITE_USER "
			+ " WHERE ? > CREATION_DATE ";
	
	public static final String DELETE_INVITE_USER_BY_DATE = " DELETE FROM  INVITE_USER WHERE CREATION_DATE < ? ";
	
	private DataSource datasource;

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void add(InviteUser inviteUser) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_INVITE_USER, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, inviteUser.getInviteEmail());
				ps.setString(2, inviteUser.getGeneratedKey());
				ps.setLong(3, inviteUser.getCompanyId());
				ps.setDate(4, new Date(inviteUser.getCreationDate().getTime()));
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		inviteUser.setId(id);
	}

	@Override
	public InviteUser getInviteUserByCode(String code) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<InviteUser> list = template.query(GET_INVITE_USER_BY_CODE, 
				new BeanPropertyRowMapper<InviteUser>(InviteUser.class), code);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public boolean deleteInviteUserByCode(String code) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(DELETE_INVITE_USER_BY_CODE, code) == 1;
	}

	@Override
	public List<InviteUser> getAllInviteUserByDate() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		Calendar calendar = Calendar.getInstance();
		calendar.roll(Calendar.DAY_OF_YEAR, -2);
		 return template.query(GET_ALL_INVITE_USER_BY_DATE, 
				 new BeanPropertyRowMapper<InviteUser>(InviteUser.class), calendar.getTime());
	}

	@Override
	public boolean deleteInviteUsersByDate() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		Calendar calendar = Calendar.getInstance();
		calendar.roll(Calendar.DAY_OF_YEAR, -2);
		return template.update(DELETE_INVITE_USER_BY_DATE, calendar) == 1;
	}
}
