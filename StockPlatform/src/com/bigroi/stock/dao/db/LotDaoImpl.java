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
	
	private static final Logger logger = Logger.getLogger(LotDaoImpl.class);
	
	private static final Logger lOG = Logger.getLogger(UserDaoImpl.class);

	private static final String ADD_LOTS_BY_ID = "INSERT INTO lot (id, description, "
			+ " poduct_Id,min_price, seller_Id, status, exp_date, volume_of_lot) "
			+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?) ";

	private static final String DELETE_LOTS_BY_ID = "DELETE FROM lot WHERE id = ? ";

	private static final String UPDATE_LOTS_BY_ID = "UPDATE lot SET "
			+ " description = ?, poduct_Id = ?, min_price = ?, seller_Id = ?, " 
			+ " status = ?, exp_date = ?, volume_of_lot = ? WHERE id = ? ";
	
	private static final String SELECT_LOTS_BY_ID = "SELECT id, description, poduct_Id, "
			+ " min_price, seller_Id, status, exp_date, volume_of_lot FROM lot WHERE id = ? ";
	
	private static final String SELECT_LOTS_BY_SALLER_ID = "SELECT id, description, "
			+ " poduct_Id, min_price, seller_Id, status, exp_date, volume_of_lot FROM lot WHERE seller_Id  = ? ";
	
	private static final String SELECT_LOTS_BY_PRODUCT_ID ="SELECT id, description, "
			+ " poduct_Id, min_price, seller_Id, status, exp_date, volume_of_lot FROM lot "
			+ " WHERE poduct_Id = ? ORDER BY min_price ";
	
	private static final String SELECT_LOTS_BY_PRODUCT_ID_IN_GAME = "SELECT id, description, "
			+ " poduct_Id, min_price, seller_Id, status, exp_date, volume_of_lot FROM lot WHERE "
			+ " poduct_Id = ? AND status = 'IN_GAME' ORDER BY min_price ";
	
	private static final String SELECT_ALL_LOTS_BY_IN_GAME = "SELECT id, description,"
			+ " poduct_Id, min_price, seller_Id, status, exp_date, volume_of_lot FROM lot "
			+ " WHERE status ='IN_GAME' ";
	
	private static final String UPDATE_STATUS_BY_SELLER_ID ="UPDATE lot SET status = 'CANCELED' "
			+ "WHERE seller_Id = ?";
	
	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void add(Lot lot) throws DaoException {
		logger.info("exection LotDaoImpl.add");
		logger.info(lot);
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
				ps.setInt(8, lot.getVolumeOfLot());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		lot.setId(id);
		logger.info("exection LotDaoImpl.add successfully finished");
	}

	@Override
	public boolean deletedById(long id) throws DaoException {
		logger.info("exection LotDaoImpl.deletedById");
		logger.info(id);
		JdbcTemplate template = new JdbcTemplate(datasource);
		logger.info("exection LotDaoImpl.deletedById successfully finished");
		return template.update(DELETE_LOTS_BY_ID, id) == 1;
	}

	@Override
	public boolean updateById(Lot lot) throws DaoException {
		logger.info("exection LotDaoImpl.updateById");
		logger.info(lot);
		JdbcTemplate template = new JdbcTemplate(datasource);
		logger.info("exection LotDaoImpl.updateById successfully finished");
		return template.update(UPDATE_LOTS_BY_ID, lot.getDescription(), 
				lot.getPoductId(), lot.getMinPrice(),
				lot.getSellerId(), lot.getStatus().name().toUpperCase(), 
				lot.getExpDate(), lot.getVolumeOfLot(), lot.getId()) == 1;

	}

	@Override
	public Lot getById(long id) throws DaoException {
		logger.info("exection LotDaoImpl.getById");
		logger.info(id);
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Lot> lot = template.query(SELECT_LOTS_BY_ID, new BeanPropertyRowMapper<Lot>(Lot.class), id);
		if(lot.size() == 0){
			logger.info("exection LotDaoImpl.getById return null, successfully finished");
			return null;
		}else{
			logger.info("exection LotDaoImpl.getById return lot.get(0), successfully finished");
			return lot.get(0);
		}
	}

	@Override
	public List<Lot> getBySellerId(long sellerId) throws DaoException {
		logger.info("exection LotDaoImpl.getBySellerId");
		logger.info(sellerId);
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
				lot.setVolumeOfLot(rs.getInt("volume_of_lot"));
				return lot;
			}
		}, sellerId);
		lOG.info(lot);
		logger.info("exection LotDaoImpl.getBySellerId successfully finished");
		return lot;
	}

	@Override
	public List<Lot> getByProductId(long productId) throws DaoException {
		logger.info("exection LotDaoImpl.getByProductId");
		logger.info(productId);
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
				lot.setVolumeOfLot(rs.getInt("volume_of_lot"));
				return lot;
			}
		}, productId);
		logger.info("exection LotDaoImpl.getByProductId successfully finished");
		return lot;
	}

	@Override
	public List<Lot> getByProductIdInGameOrderMinPrice(long productId) throws DaoException {
		logger.info("exection LotDaoImpl.getByProductIdInGameOrderMinPrice");
		logger.info(productId);
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Lot> lots = template.query(SELECT_LOTS_BY_PRODUCT_ID_IN_GAME, 
				new BeanPropertyRowMapper<Lot>(Lot.class),productId);
		logger.info("exection LotDaoImpl.getByProductIdInGameOrderMinPrice successfully finished");
		return lots;
	}

	@Override
	public List<Lot> getAllInGame() throws DaoException {
		logger.info("exection LotDaoImpl.getAllInGame");
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Lot> lot = template.query(SELECT_ALL_LOTS_BY_IN_GAME, 
				new BeanPropertyRowMapper<Lot>(Lot.class));
		logger.info("exection LotDaoImpl.getAllInGame successfully finished");
		return lot;
	}

	@Override
	public  Lot setStatusCancel(Lot lot) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		  template.update(UPDATE_STATUS_BY_SELLER_ID, lot.getSellerId());
		return lot;
	}

	
}
