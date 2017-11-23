package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.LotDao;

public class LotDaoImpl implements LotDao {
	
	private static final String ADD_LOT = "INSERT INTO LOT "
			+ "(DESCRIPTION, PRODUCT_ID, MIN_PRICE, SELLER_ID, STATUS, EXP_DATE, VOLUME, MIN_VOLUME) "
			+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?) ";

	private static final String UPDATE_LOT_BY_ID = "UPDATE LOT SET "
			+ " DESCRIPTION = ?, PRODUCT_ID = ?, MIN_PRICE = ?, SELLER_ID = ?, " 
			+ " STATUS = ?, EXP_DATE = ?, VOLUME = ?, MIN_VOLUME = ? "
			+ " WHERE ID = ? ";
	
	private static final String GET_LOT_BY_ID = "SELECT ID, DESCRIPTION, PRODUCT_ID, "
			+ " MIN_PRICE, SELLER_ID, STATUS, EXP_DATE, VOLUME, MIN_VOLUME FROM LOT WHERE ID = ? ";
	
	private static final String GET_LOTS_BY_SALLER_ID = "SELECT ID, DESCRIPTION, "
			+ " PRODUCT_ID, MIN_PRICE, SELLER_ID, STATUS, EXP_DATE, VOLUME, MIN_VOLUME FROM LOT WHERE SELLER_ID  = ? ";
	
	private static final String GET_ACTIVE_LOTS_BY_PRODUCT_ID = "SELECT ID, DESCRIPTION, "
			+ " PRODUCT_ID, MIN_PRICE, SELLER_ID, STATUS, EXP_DATE, VOLUME, MIN_VOLUME FROM LOT WHERE "
			+ " PRODUCT_ID = ? AND STATUS = '" + Status.ACTIVE +"' ORDER BY MIN_PRICE ";
	
	private static final String GET_ALL_ACTIVE_LOTS = "SELECT ID, DESCRIPTION,"
			+ " PRODUCT_ID, MIN_PRICE, SELLER_ID, STATUS, EXP_DATE, VOLUME, MIN_VOLUME FROM LOT "
			+ " WHERE STATUS ='" + Status.ACTIVE +"' ";
	
	private static final String SET_STATUS_BY_SELLER_ID =
			  "UPDATE LOT SET "
			+ "STATUS = ? "
			+ "WHERE SELLER_ID = ?";
	
	private static final String SET_STATUS_BY_ID =
			  "UPDATE LOT SET "
			+ "STATUS = ? "
			+ "WHERE ID = ?";
	
	private static final String SET_STATUS_BY_PRODUCT_ID =
			  "UPDATE LOT SET "
			+ "STATUS = ? "
			+ "WHERE PRODUCT_ID = ?";
	
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
				PreparedStatement ps = con.prepareStatement(ADD_LOT, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, lot.getDescription());
				ps.setLong(2, lot.getProductId());
				ps.setDouble(3, lot.getMinPrice());
				ps.setLong(4, lot.getSellerId());
				ps.setString(5, lot.getStatus().name().toUpperCase());
				ps.setDate(6, new Date(lot.getExpDate().getTime()));
				ps.setInt(7, lot.getVolume());
				ps.setInt(8, lot.getMinVolume());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		lot.setId(id);
	}

	@Override
	public boolean update(Lot lot) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_LOT_BY_ID, 
				lot.getDescription(), 
				lot.getProductId(), 
				lot.getMinPrice(),
				lot.getSellerId(), 
				lot.getStatus().name().toUpperCase(), 
				lot.getExpDate(), 
				lot.getVolume(), 
				lot.getMinVolume(),
				lot.getId()) == 1;
	}

	@Override
	public Lot getById(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Lot> lot = template.query(GET_LOT_BY_ID, new BeanPropertyRowMapper<Lot>(Lot.class), id);
		if(lot.size() == 0){
			return null;
		}else{
			return lot.get(0);
		}
	}

	@Override
	public List<Lot> getBySellerId(long sellerId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_LOTS_BY_SALLER_ID, new RowMapper<Lot>(){
			@Override
			public Lot mapRow(ResultSet rs, int rowNum) throws SQLException {
				Lot lot = new Lot();
				lot.setId(rs.getLong("id"));
				lot.setDescription(rs.getString("description"));
				lot.setProductId(rs.getLong("PRODUCT_ID"));
				lot.setMinPrice(rs.getDouble("min_price"));
				lot.setSellerId(rs.getLong("seller_Id"));
				lot.setStatus(Status.valueOf(rs.getString("status").toUpperCase()));
				lot.setExpDate(rs.getDate("exp_date"));
				lot.setVolume(rs.getInt("VOLUME"));
				lot.setMinVolume(rs.getInt("MIN_VOLUME"));
				return lot;
			}
		}, sellerId);
	}

	@Override
	public List<Lot> getActiveByProductId(long productId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_ACTIVE_LOTS_BY_PRODUCT_ID, 
				new BeanPropertyRowMapper<Lot>(Lot.class),productId);
	}

	@Override
	public List<Lot> getActive() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_ALL_ACTIVE_LOTS, 
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
	public boolean setStatusById(long id, Status status) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(SET_STATUS_BY_ID, status.toString(), id) == 1;
	}

	@Override
	public void update(Collection<Lot> lotsToUpdate) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.batchUpdate(UPDATE_LOT_BY_ID, lotsToUpdate, lotsToUpdate.size(), new ParameterizedPreparedStatementSetter<Lot>() {

			@Override
			public void setValues(PreparedStatement ps, Lot lot) throws SQLException {
				ps.setString(1, lot.getDescription());
				ps.setLong(2, lot.getProductId());
				ps.setDouble(3, lot.getMinPrice());
				ps.setLong(4, lot.getSellerId());
				ps.setString(5, lot.getStatus().name());
				ps.setDate(6, new Date(lot.getExpDate().getTime()));
				ps.setInt(7, lot.getVolume());
				ps.setInt(8, lot.getMinVolume());
				ps.setLong(9, lot.getId());
			}
		});
		
	}
}
