package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
			+ " JOIN GENERATED_KEY AS K ON USER.KEYS_ID = K.ID "
			+ " WHERE USER.USERNAME = ? AND K.GENERATED_KEY = ? ";

	private static final String ADD_GENERATED_KEY = "INSERT INTO GENERATED_KEY ( GENERATED_KEY, EXPIRATION_DATE) "
			+ " VALUES (?, ?) ";

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
}
