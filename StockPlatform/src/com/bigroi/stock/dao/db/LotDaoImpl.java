package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.LotDao;

public class LotDaoImpl implements LotDao {
	
	private static final String ADD_LOT = "INSERT INTO LOT "
			+ "(DESCRIPTION, PRODUCT_ID, MIN_PRICE, SELLER_ID, STATUS, EXP_DATE, MAX_VOLUME, MIN_VOLUME, CREATION_DATE, DELIVERY, PACKAGING) "
			+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

	private static final String UPDATE_LOT_BY_ID = "UPDATE LOT SET "
			+ " DESCRIPTION = ?, PRODUCT_ID = ?, MIN_PRICE = ?, STATUS = ?, " 
			+ " EXP_DATE = ?, MAX_VOLUME = ?, MIN_VOLUME = ?, DELIVERY = ?, PACKAGING = ? "
			+ " WHERE ID = ?";
	
	private static final String UPDATE_LOT_BY_ID_AND_SELLER = "UPDATE LOT SET "
			+ " DESCRIPTION = ?, PRODUCT_ID = ?, MIN_PRICE = ?, STATUS = ?, " 
			+ " EXP_DATE = ?, MAX_VOLUME = ?, MIN_VOLUME = ?, DELIVERY = ?, PACKAGING = ? "
			+ " WHERE ID = ? AND SELLER_ID = ?";
	
	private static final String GET_LOT_BY_ID = "SELECT ID, DESCRIPTION, PRODUCT_ID, "
			+ " MIN_PRICE, SELLER_ID, STATUS, EXP_DATE, MAX_VOLUME, MIN_VOLUME, CREATION_DATE, DELIVERY, PACKAGING FROM LOT "
			+ " WHERE ID = ? ";
	
	private static final String GET_LOTS_BY_SELLER_ID = "SELECT L.ID, L.DESCRIPTION, "
			+ " PRODUCT_ID, MIN_PRICE, SELLER_ID, STATUS, EXP_DATE, MAX_VOLUME, MIN_VOLUME, "
			+ " P.NAME AS PRODUCT_NAME, CREATION_DATE, DELIVERY, PACKAGING "
			+ " FROM LOT L "
			+ " JOIN PRODUCT P "
			+ " ON L.PRODUCT_ID = P.ID "
			+ " WHERE SELLER_ID  = ? ";
	
	private static final String GET_ACTIVE_LOTS_BY_PRODUCT_ID = "SELECT ID, DESCRIPTION, "
			+ " PRODUCT_ID, MIN_PRICE, SELLER_ID, STATUS, EXP_DATE, MAX_VOLUME, MIN_VOLUME FROM LOT WHERE "
			+ " PRODUCT_ID = ? AND STATUS = '" + BidStatus.ACTIVE +"' ORDER BY MIN_PRICE ";
	
	private static final String GET_ALL_ACTIVE_LOTS = "SELECT L.ID, L.DESCRIPTION,"
			+ " PRODUCT_ID, MIN_PRICE, SELLER_ID, STATUS, EXP_DATE, MAX_VOLUME, MIN_VOLUME, P.NAME AS PRODUCT_NAME, CREATION_DATE "
			+ " FROM LOT L "
			+ " JOIN PRODUCT P "
			+ " ON L.PRODUCT_ID = P.ID "
			+ " WHERE STATUS ='" + BidStatus.ACTIVE +"' ";
	
	private static final String SET_STATUS_BY_SELLER_ID =
			  "UPDATE LOT SET "
			+ "STATUS = ? "
			+ "WHERE SELLER_ID = ?";
	
	private static final String SET_STATUS_BY_ID =
			  "UPDATE LOT SET "
			+ "STATUS = ? "
			+ "WHERE ID = ? AND SELLER_ID = ?";
	
	private static final String SET_STATUS_BY_PRODUCT_ID =
			  "UPDATE LOT SET "
			+ "STATUS = ? "
			+ "WHERE PRODUCT_ID = ?";

	private static final String DELETE_BY_ID = 
			"DELETE "
			+ " FROM LOT "
			+ " WHERE ID = ? AND SELLER_ID = ?";
	
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
				ps.setInt(7, lot.getMaxVolume());
				ps.setInt(8, lot.getMinVolume());
				ps.setDate(9, new Date(lot.getCreationDate().getTime()));
				ps.setInt(10, lot.getDelivery());
				ps.setInt(11, lot.getPackaging());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		lot.setId(id);
	}

	@Override
	public boolean update(Lot lot, long companyId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_LOT_BY_ID_AND_SELLER, 
				lot.getDescription(), 
				lot.getProductId(), 
				lot.getMinPrice(), 
				lot.getStatus().name(),
				lot.getExpDate(), 
				lot.getMaxVolume(), 
				lot.getMinVolume(),
				lot.getDelivery(),
				lot.getPackaging(),
				lot.getId(),
				companyId) == 1;
	}

	@Override
	public Lot getById(long id, long companyId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Lot> lots = template.query(GET_LOT_BY_ID, new BeanPropertyRowMapper<Lot>(Lot.class), id);
		if(lots.size() == 0 || (companyId != -1 && lots.get(0).getSellerId() != companyId)){
			return null;
		}else{
			return lots.get(0);
		}
	}

	@Override
	public List<Lot> getBySellerId(long sellerId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_LOTS_BY_SELLER_ID, new BeanPropertyRowMapper<Lot>(Lot.class), sellerId);
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
	public  boolean setStatusBySellerId(long sellerId, BidStatus status) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(SET_STATUS_BY_SELLER_ID, status.toString(), sellerId) == 1;
		 
	}

	@Override
	public boolean setStatusByProductId(long productId, BidStatus status) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(SET_STATUS_BY_PRODUCT_ID, status.toString(), productId) == 1;
	}

	
	@Override
	public boolean setStatusById(long id, long companyId, BidStatus status) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(SET_STATUS_BY_ID, status.name(), id, companyId) == 1;
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
				ps.setString(4, lot.getStatus().name());
				ps.setDate(5, new Date(lot.getExpDate().getTime()));
				ps.setInt(6, lot.getMaxVolume());
				ps.setInt(7, lot.getMinVolume());
				ps.setInt(8, lot.getDelivery());
				ps.setInt(9, lot.getPackaging());
				ps.setLong(10, lot.getId());
			}
		});
		
	}

	@Override
	public void delete(long id, long companyId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(DELETE_BY_ID, id, companyId);
	}
}
