package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.Blacklist;
import com.bigroi.stock.dao.BlacklistDao;
import com.bigroi.stock.dao.DaoException;
import com.mysql.jdbc.Statement;

public class BlacklistDaoImpl implements BlacklistDao {
	
	private static final String ADD_BLACKLIST_BY_ID = "INSERT INTO blacklist "
			+ "(id, tender_Id, lot_Id) VALUES (?, ?, ?)";

	private static final String DELETE_BLACKLIST_BY_ID = "DELETE FROM blacklist WHERE id = ?";

	private static final String UPDATE_BLACKLIST_BY_ID = "UPDATE blacklist SET "
			+ "tender_Id = ?, lot_Id = ? WHERE id = ?";
	
	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void add(Blacklist blacklist) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_BLACKLIST_BY_ID, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, blacklist.getId());
				ps.setLong(2, blacklist.getTenderId());
				ps.setLong(3, blacklist.getLotId());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		blacklist.setId(id);
	}

	@Override
	public boolean deletedById(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(DELETE_BLACKLIST_BY_ID, id) == 1;
	}

	@Override
	public boolean updateById( Blacklist blacklist) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_BLACKLIST_BY_ID, blacklist.getId(), 
				blacklist.getTenderId(), blacklist.getLotId()) == 1;
		
	}


}
