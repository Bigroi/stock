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

import com.bigroi.stock.bean.Deals;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DealsDao;

public class DealsDaoImpl implements DealsDao {
	
	private static final String ADD_DEALS_BY_ID = "INSERT INTO deals(id, lot_Id, tender_Id, deals_Time) "
			+ "VALUES (?, ?, ?, ?) ";

	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void add(Deals deals) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_DEALS_BY_ID, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setLong(1, deals.getId());
				ps.setLong(2, deals.getLotId());
				ps.setLong(3, deals.getTenderId());
				ps.setDate(4, new Date(deals.getDealsTime().getTime()));
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		deals.setId(id);
	}

}
