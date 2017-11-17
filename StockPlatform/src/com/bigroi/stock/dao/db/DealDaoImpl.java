package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.Deal;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DealDao;

public class DealDaoImpl implements DealDao {
	
	private static final String ADD_DEALS_BY_ID = "INSERT INTO DEALS(LOT_ID, TENDER_ID, DEALS_TIME) "
			+ "VALUES (?, ?, ?) ";

	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void add(Deal deals) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_DEALS_BY_ID, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setLong(1, deals.getLotId());
				ps.setLong(2, deals.getTenderId());
				ps.setDate(3, new Date(deals.getDealsTime().getTime()));
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		deals.setId(id);
	}

}
