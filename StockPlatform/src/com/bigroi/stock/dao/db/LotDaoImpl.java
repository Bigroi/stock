package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.Date;
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

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.LotDao;

public class LotDaoImpl implements LotDao {
	
	private static final String ADD_LOTS_BY_ID = "INSERT INTO lot (description, "
			+ " poduct_Id,min_price, seller_Id, status, exp_date, volume_of_lot) "
			+ " VALUES ( ?, ?, ?, ?, ?, ?, ?) ";

	private static final String UPDATE_LOTS_BY_ID = "UPDATE lot SET "
			+ " description = ?, poduct_Id = ?, min_price = ?, seller_Id = ?, " 
			+ " status = ?, exp_date = ?, volume_of_lot = ? WHERE id = ? ";
	
	private static final String SELECT_LOTS_BY_ID = "SELECT id, description, poduct_Id, "
			+ " min_price, seller_Id, status, exp_date, volume_of_lot FROM lot WHERE id = ? ";
	
	private static final String SELECT_LOTS_BY_SALLER_ID = "SELECT id, description, "
			+ " poduct_Id, min_price, seller_Id, status, exp_date, volume_of_lot FROM lot WHERE seller_Id  = ? ";
	
	private static final String SELECT_LOTS_BY_PRODUCT_ID_IN_GAME = "SELECT id, description, "
			+ " poduct_Id, min_price, seller_Id, status, exp_date, volume_of_lot FROM lot WHERE "
			+ " poduct_Id = ? AND status = 'IN_GAME' ORDER BY min_price ";
	
	private static final String SELECT_ALL_LOTS_BY_IN_GAME = "SELECT id, description,"
			+ " poduct_Id, min_price, seller_Id, status, exp_date, volume_of_lot FROM lot "
			+ " WHERE status ='IN_GAME' ";
	
	private static final String SET_STATUS_BY_SELLER_ID =
			  "UPDATE lot SET "
			+ "status = ? "
			+ "WHERE seller_Id = ?";
	
	private static final String SET_STATUS_BY_ID =
			  "UPDATE lot SET "
			+ "status = ? "
			+ "WHERE id = ?";
	
	private static final String SET_STATUS_BY_PRODUCT_ID =
			  "UPDATE lot SET "
			+ "status = ? "
			+ "WHERE poduct_id = ?";
	
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
				PreparedStatement ps = con.prepareStatement(ADD_LOTS_BY_ID, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, lot.getDescription());
				ps.setLong(2, lot.getPoductId());
				ps.setDouble(3, lot.getMinPrice());
				ps.setLong(4, lot.getSellerId());
				ps.setString(5, lot.getStatus().name().toUpperCase());
				ps.setDate(6, new Date(lot.getExpDate().getTime()));
				ps.setInt(7, lot.getVolume());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		lot.setId(id);
	}

	@Override
	public boolean update(Lot lot) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_LOTS_BY_ID, 
				lot.getDescription(), 
				lot.getPoductId(), 
				lot.getMinPrice(),
				lot.getSellerId(), 
				lot.getStatus().name().toUpperCase(), 
				lot.getExpDate(), 
				lot.getVolume(), 
				lot.getId()) == 1;

	}

	@Override
	public Lot getById(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Lot> lot = template.query(SELECT_LOTS_BY_ID, new BeanPropertyRowMapper<Lot>(Lot.class), id);
		if(lot.size() == 0){
			return null;
		}else{
			return lot.get(0);
		}
	}

	@Override
	public List<Lot> getBySellerId(long sellerId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(SELECT_LOTS_BY_SALLER_ID, new RowMapper<Lot>(){
			@Override
			public Lot mapRow(ResultSet rs, int rowNum) throws SQLException {
				Lot lot = new Lot();
				lot.setId(rs.getLong("id"));
				lot.setDescription(rs.getString("description"));
				lot.setPoductId(rs.getLong("poduct_Id"));
				lot.setMinPrice(rs.getDouble("min_price"));
				lot.setSellerId(rs.getLong("seller_Id"));
				lot.setStatus(Status.valueOf(rs.getString("status").toUpperCase()));
				lot.setExpDate(rs.getDate("exp_date"));
				lot.setVolume(rs.getInt("volume_of_lot"));
				return lot;
			}
		}, sellerId);
	}

	@Override
	public List<Lot> getActiveByProductId(long productId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(SELECT_LOTS_BY_PRODUCT_ID_IN_GAME, 
				new BeanPropertyRowMapper<Lot>(Lot.class),productId);
	}

	@Override
	public List<Lot> getActive() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(SELECT_ALL_LOTS_BY_IN_GAME, 
				new BeanPropertyRowMapper<Lot>(Lot.class));
	}

	@Override
	public  boolean setStatusBySellerId(long sellerId, Status status) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(SET_STATUS_BY_SELLER_ID, status.toString(), sellerId) == 1;
		 
	}

	@Override
	public boolean setStatusByProductId(long productId, Status status) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(SET_STATUS_BY_PRODUCT_ID, status.toString(), productId) == 1;
	}

	@Override
	public boolean setStatusById(long id, Status status) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(SET_STATUS_BY_ID, status.toString(), id) == 1;
	}
}
