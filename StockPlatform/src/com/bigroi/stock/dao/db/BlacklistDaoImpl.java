package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.Blacklist;
import com.bigroi.stock.dao.BlacklistDao;
import com.bigroi.stock.dao.DaoException;

@Repository
public class BlacklistDaoImpl implements BlacklistDao {
	
	private static final String ADD_BLACKLIST = "INSERT INTO BLACK_LIST "
			+ " (TENDER_ID, LOT_ID) VALUES (?, ?)";

	@Autowired
	private DataSource datasource;

	@Override
	public void add(Blacklist blacklist) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_BLACKLIST, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setLong(1, blacklist.getTenderId());
				ps.setLong(2, blacklist.getLotId());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		blacklist.setId(id);
	}

}
