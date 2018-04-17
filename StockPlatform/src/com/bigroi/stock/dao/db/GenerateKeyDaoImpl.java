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

import com.bigroi.stock.bean.GeneratedKey;
import com.bigroi.stock.bean.tmp.UserKeysTmp;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.GenerateKeyDao;

public class GenerateKeyDaoImpl implements GenerateKeyDao {

	private static final String GET_GENERATED_KEY = "SELECT USERNAME, GENERATED_KEY FROM USER  "
			+ " JOIN GENERATED_KEY AS K ON USER.KEYS_ID = K.ID " + " WHERE USER.USERNAME = ? AND K.GENERATED_KEY = ? ";

	private static final String ADD_GENERATED_KEY = "INSERT INTO GENERATED_KEY ( GENERATED_KEY, EXPIRATION_DATE) "
			+ " VALUES (?, ?) ";

	private static final String GET_ALL_BY_ID = " SELECT ID, GENERATED_KEY, EXPIRATION_DATE FROM GENERATED_KEY WHERE ID =? ";

	public static final String GET_ALL_BY_DATE = " SELECT GENERATED_KEY, EXPIRATION_DATE FROM INVITE_USER "
			+ " WHERE ? > expiration_date ";

	public static final String DELETE_ALL_BY_DATE = " DELETE FROM  INVITE_USER WHERE EXPIRATION_DATE < ? ";

	private DataSource datasource;

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public boolean ñheckResetKey(String email, String code) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<UserKeysTmp> list = template.query(GET_GENERATED_KEY,
				new BeanPropertyRowMapper<UserKeysTmp>(UserKeysTmp.class), email, code);
		return list.size() != 0;
	}

	@Override
	public GeneratedKey generateKey() {
		JdbcTemplate template = new JdbcTemplate(datasource);
		GeneratedKey key = new GeneratedKey();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_GENERATED_KEY, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, key.getGeneratedKey());
				ps.setDate(2, new Date(key.getExpirationDate().getTime()));
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		key.setId(id);
		return key;
	}

	@Override
	public GeneratedKey getGeneratedKeyById(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<GeneratedKey> list = template.query(GET_ALL_BY_ID,
				new BeanPropertyRowMapper<GeneratedKey>(GeneratedKey.class), id);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public List<GeneratedKey> getGenerateKeysByDate() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		Calendar calendar = Calendar.getInstance();
		calendar.roll(Calendar.DAY_OF_YEAR, -2);
		return template.query(GET_ALL_BY_DATE, new BeanPropertyRowMapper<GeneratedKey>(GeneratedKey.class),
				calendar.getTime());
	}

	@Override
	public boolean deleteGenerateKeysByDate() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		Calendar calendar = Calendar.getInstance();
		calendar.roll(Calendar.DAY_OF_YEAR, -2);
		return template.update(DELETE_ALL_BY_DATE, calendar) == 1;
	}

}
