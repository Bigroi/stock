package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.LotDao;

@Repository
public class LotDaoImpl implements LotDao {
	
	private static final String ADD_LOT = 
			"INSERT INTO LOT "
			+ "(DESCRIPTION, PRODUCT_ID, MIN_PRICE, MIN_VOLUME, "
			+ " MAX_VOLUME, COMPANY_ID, `STATUS`, CREATION_DATE, EXPARATION_DATE, "
			+ " ADDRESS_ID, FOTO) "
			+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

	private static final String UPDATE_LOT_BY_ID = 
			"UPDATE LOT "
			+ " SET MAX_VOLUME = ?, STATUS = ? "
			+ " WHERE ID = ?";
	
	private static final String UPDATE_LOT_BY_ID_AND_SELLER = 
			"UPDATE LOT SET "
			+ " DESCRIPTION = ?, PRODUCT_ID = ?, MIN_PRICE = ?, MIN_VOLUME = ?, "
			+ " MAX_VOLUME = ?, `STATUS` = ?, CREATION_DATE = ?, EXPARATION_DATE = ?, "
			+ " ADDRESS_ID = ?, FOTO = ? "
			+ " WHERE ID = ? AND COMPANY_ID = ?";
	
	private static final String GET_LOT_BY_ID = 
			"SELECT " + LotRowMapper.ALL_COLUMNS
			+ " FROM LOT L "
			+ " JOIN PRODUCT P "
			+ " ON L.PRODUCT_ID = P.ID "
			+ " WHERE L.ID = ?";
	
	private static final String GET_ACTIVE_LOTS = 
			"SELECT L.ID, L.DESCRIPTION, L.PRODUCT_ID, L.MIN_PRICE, L.MIN_VOLUME, "
			+ " L.MAX_VOLUME, L.COMPANY_ID, L.`STATUS`, L.CREATION_DATE, L.EXPARATION_DATE, "
			+ " L.ADDRESS_ID, L.FOTO, P.NAME PRODUCT_NAME "
			+ " FROM LOT L "
			+ " JOIN PRODUCT P "
			+ " ON L.PRODUCT_ID = P.ID "
			+ " WHERE  L.`STATUS` = '" + BidStatus.ACTIVE.name() + "'";
	
	private static final String GET_ACTIVE_LOTS_BY_PRODUCT_ID = 
			"SELECT " + LotRowMapper.ALL_COLUMNS
			+ " FROM LOT L "
			+ " JOIN PRODUCT P "
			+ " ON L.PRODUCT_ID = P.ID "
			+ " WHERE L.PRODUCT_ID = ? "
			+ " AND L.`STATUS` = '" + BidStatus.ACTIVE.name() + "'";
	
	private static final String GET_LOTS_BY_COMPANY = 
			"SELECT " + LotRowMapper.ALL_COLUMNS
			+ " FROM LOT L "
			+ " JOIN PRODUCT P "
			+ " ON L.PRODUCT_ID = P.ID "
			+ " WHERE L.COMPANY_ID = ?"
			+ " AND MIN_VOLUME <= MAX_VOLUME";
	
	private static final String SET_STATUS_BY_COMPANY =
			  "UPDATE LOT SET "
			+ "STATUS = ? "
			+ "WHERE COMPANY_ID = ?";
	
	private static final String SET_STATUS_BY_ID_AND_COMPANY =
			  "UPDATE LOT SET "
			+ "STATUS = ? "
			+ "WHERE ID = ? AND COMPANY_ID = ?";
	
	private static final String SET_STATUS_BY_PRODUCT_ID =
			  "UPDATE LOT SET "
			+ "STATUS = ? "
			+ "WHERE PRODUCT_ID = ?";

	private static final String DELETE_BY_ID_AND_COMPANY = 
			"DELETE "
			+ " FROM LOT "
			+ " WHERE ID = ? AND COMPANY_ID = ?";
	
	private static final String CLOSE_LOTS = 
			"DELETE "
			+ " FROM LOT "
			+ " WHERE MIN_VOLUME > MAX_VOLUME";
	
	@Autowired
	private DataSource datasource;

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
				ps.setInt(4, lot.getMinVolume());
				ps.setInt(5, lot.getMaxVolume());
				ps.setLong(6, lot.getCompanyId());
				ps.setString(7, lot.getStatus().name().toUpperCase());
				ps.setDate(8, new Date(lot.getCreationDate().getTime()));
				ps.setDate(9, new Date(lot.getExparationDate().getTime()));
				ps.setLong(10, lot.getAddressId());
				ps.setString(11, lot.getFoto());
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
				lot.getMinVolume(),
				lot.getMaxVolume(), 
				lot.getStatus().name(),
				lot.getCreationDate(),
				lot.getExparationDate(), 
				lot.getAddressId(),
				lot.getFoto(),
				lot.getId(),
				companyId) == 1;
	}

	@Override
	public Lot getById(long id, long companyId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Lot> lots = template.query(GET_LOT_BY_ID, new LotRowMapper(), id);
		if(lots.size() == 0 || (companyId != -1 && lots.get(0).getCompanyId() != companyId)){
			return null;
		}else{
			return lots.get(0);
		}
	}

	@Override
	public List<Lot> getByCompanyId(long companyId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_LOTS_BY_COMPANY, new LotRowMapper(), companyId);
	}

	@Override
	public  boolean setStatusByCompanyId(long companyId, BidStatus status) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(SET_STATUS_BY_COMPANY, status.toString(), companyId) == 1;
		 
	}

	@Override
	public boolean setStatusByProductId(long productId, BidStatus status) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(SET_STATUS_BY_PRODUCT_ID, status.toString(), productId) == 1;
	}

	
	@Override
	public boolean setStatusById(long id, long companyId, BidStatus status) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(SET_STATUS_BY_ID_AND_COMPANY, status.name(), id, companyId) == 1;
	}

	@Override
	public void update(Collection<Lot> lotsToUpdate) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.batchUpdate(UPDATE_LOT_BY_ID, lotsToUpdate, lotsToUpdate.size(), new ParameterizedPreparedStatementSetter<Lot>() {

			@Override
			public void setValues(PreparedStatement ps, Lot lot) throws SQLException {
				ps.setInt(1, lot.getMaxVolume());
				ps.setString(2, lot.getStatus().name());
				ps.setLong(3, lot.getId());
			}
		});
		
	}

	@Override
	public void delete(long id, long companyId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(DELETE_BY_ID_AND_COMPANY, id, companyId);
	}
	
	@Override
	public void closeLots() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(CLOSE_LOTS);
	}

	@Override
	public List<Lot> getActive() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_ACTIVE_LOTS, new LotRowMapper());
	}

	@Override
	public List<Lot> getActiveByProductId(long productId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_ACTIVE_LOTS_BY_PRODUCT_ID, new LotRowMapper(), productId);
	}
	
	private static class LotRowMapper implements RowMapper<Lot>{

		public static final String ALL_COLUMNS = 
				" L.ID, L.DESCRIPTION, L.PRODUCT_ID, L.MIN_PRICE, L.MIN_VOLUME, "
			+ " L.MAX_VOLUME, L.COMPANY_ID, L.`STATUS`, L.CREATION_DATE, L.EXPARATION_DATE, "
			+ " L.ADDRESS_ID, L.FOTO, P.NAME PRODUCT_NAME ";
		
		@Override
		public Lot mapRow(ResultSet rs, int rowNum) throws SQLException {
			Lot lot = new Lot();
			lot.setAddressId(rs.getLong("ADDRESS_ID"));
			lot.setCompanyId(rs.getLong("COMPANY_ID"));
			lot.setCreationDate(rs.getTimestamp("CREATION_DATE"));
			lot.setDescription(rs.getString("DESCRIPTION"));
			lot.setExparationDate(rs.getTimestamp("EXPARATION_DATE"));
			lot.setFoto(rs.getString("FOTO"));
			lot.setId(rs.getLong("ID"));
			lot.setMaxVolume(rs.getInt("MAX_VOLUME"));
			lot.setMinVolume(rs.getInt("MIN_VOLUME"));
			lot.setMinPrice(rs.getDouble("MIN_PRICE"));
			lot.setProductId(rs.getLong("PRODUCT_ID"));
			lot.setStatus(BidStatus.valueOf(rs.getString("STATUS")));
			
			Product product = new Product();
			product.setName(rs.getString("PRODUCT_NAME"));
			product.setId(rs.getLong("PRODUCT_ID"));
			
			lot.setProduct(product);
			return lot;
		}
	}
}
