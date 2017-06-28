package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.LotDao;
import com.mysql.jdbc.Statement;

public class LotDaoImpl implements LotDao {
	
	private static final Logger lOG = Logger.getLogger(UserDaoImpl.class);

	private static final String ADD_LOTS_BY_ID = "INSERT INTO lot (id, description,"
			+ " poductId,min_price, salerId, status, exp_date) "
			+ "VALUES ( ?, ?, ?, ?, ?, ?, ?)";

	private static final String DELETE_LOTS_BY_ID = "DELETE FROM lot WHERE id = ?";

	private static final String UPDATE_LOTS_BY_ID = "UPDATE lot SET id = ?,"
			+ "description = ?, poductId = ?, min_price = ?, salerId = ?, " 
			+ "status = ?, exp_date = ? WHERE id = ?";
	
	private static final String SELECT_LOTS_BY_ID = "SELECT id, description, poductId,"
			+ " min_price, salerId, status, exp_date FROM lot WHERE id = ?";
	
	private static final String SELECT_LOTS_BY_SALER_ID = "SELECT id, description, "
			+ "poductId, min_price, salerId, status, exp_date FROM lot WHERE salerId  = ?";

	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void add(Lot lot) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_LOTS_BY_ID, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, lot.getId());
				ps.setString(2, lot.getDescription());
				ps.setLong(3, lot.getPoductId());
				ps.setDouble(4, lot.getMinPrice());
				ps.setLong(5, lot.getSalerId());
				ps.setByte(6, lot.getStatus());
				ps.setDate(7, new Date(lot.getExpDate().getTime()));
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		lot.setId(id);
	}

	@Override
	public void delete(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(DELETE_LOTS_BY_ID, id);

	}

	@Override
	public void update(long id, Lot lot) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(UPDATE_LOTS_BY_ID, lot.getId(), lot.getDescription(), 
				lot.getPoductId(), lot.getMinPrice(),
				lot.getSalerId(), lot.getStatus(), lot.getExpDate(), id);

	}

	@Override
	public Lot getById(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		Lot lot = template.queryForObject(SELECT_LOTS_BY_ID, new Object[] { id },
				new BeanPropertyRowMapper<Lot>(Lot.class));
		return lot;
	}

	@Override
	public List<Lot> getBySalerId(long salerId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Lot> lot =  template.query(SELECT_LOTS_BY_SALER_ID, new RowMapper<Lot>(){
			@Override
			public Lot mapRow(ResultSet rs, int rowNum) throws SQLException {
				Lot lot = new Lot();
				lot.setId(rs.getLong("id"));
				lot.setDescription(rs.getString("description"));
				lot.setPoductId(rs.getLong("poductId"));
				lot.setMinPrice(rs.getDouble("min_price"));
				lot.setSalerId(rs.getLong("salerId"));
				lot.setStatus(rs.getByte("status"));
				lot.setExpDate(rs.getDate("exp_date"));
				return lot;
			}
		}, salerId);
		lOG.info(lot);
		return lot;
	}

	
	
	

}
