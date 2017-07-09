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
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.LotDao;
import com.mysql.jdbc.Statement;

public class LotDaoImpl implements LotDao {
	
	private static final Logger lOG = Logger.getLogger(UserDaoImpl.class);

	private static final String ADD_LOTS_BY_ID = "INSERT INTO lot (id, description, "
			+ " poduct_Id,min_price, seller_Id, status, exp_date) "
			+ "VALUES ( ?, ?, ?, ?, ?, ?, ?) ";

	private static final String DELETE_LOTS_BY_ID = "DELETE FROM lot WHERE id = ? ";

	private static final String UPDATE_LOTS_BY_ID = "UPDATE lot SET "
			+ "description = ?, poduct_Id = ?, min_price = ?, seller_Id = ?, " 
			+ "status = ?, exp_date = ? WHERE id = ? ";
	
	private static final String SELECT_LOTS_BY_ID = "SELECT id, description, poduct_Id, "
			+ " min_price, seller_Id, status, exp_date FROM lot WHERE id = ? ";
	
	private static final String SELECT_LOTS_BY_SALLER_ID = "SELECT id, description, "
			+ "poduct_Id, min_price, seller_Id, status, exp_date FROM lot WHERE seller_Id  = ? ";
	
	private static final String SELECT_LOTS_BY_PRODUCT_ID ="SELECT id, description, "
			+ "poduct_Id, min_price, seller_Id, status, exp_date FROM lot "
			+ " WHERE poduct_Id = ? ORDER BY min_price ";

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
				ps.setLong(5, lot.getSellerId());
				ps.setString(6, lot.getStatus().name().toUpperCase());
				ps.setDate(7, new Date(lot.getExpDate().getTime()));
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		lot.setId(id);
	}

	@Override
	public boolean deletedById(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(DELETE_LOTS_BY_ID, id) == 1;

	}

	@Override
	public boolean updateById( Lot lot) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_LOTS_BY_ID, lot.getId(), lot.getDescription(), 
				lot.getPoductId(), lot.getMinPrice(),
				lot.getSellerId(), lot.getStatus().name().toUpperCase(), lot.getExpDate()) == 1;

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
		List<Lot> lot =  template.query(SELECT_LOTS_BY_SALLER_ID, new RowMapper<Lot>(){
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
				return lot;
			}
		}, sellerId);
		lOG.info(lot);
		return lot;
	}

	@Override
	public List<Lot> getByProductId(long productId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Lot> lot =  template.query(SELECT_LOTS_BY_PRODUCT_ID, new RowMapper<Lot>() {
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
				return lot;
			}
		}, productId);
		
		return lot;
	}

	
	
	

}
