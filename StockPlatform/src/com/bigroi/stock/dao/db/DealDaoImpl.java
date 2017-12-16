package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.Deal;
import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DealDao;
import com.bigroi.stock.jobs.trade.TradeLot;
import com.bigroi.stock.jobs.trade.TradeTender;

public class DealDaoImpl implements DealDao {
	
	private static final String ADD = 
			  " INSERT INTO DEAL(LOT_ID, TENDER_ID, SELLER_ID CUSTOMER_ID, "
			+ " PRICE, VOLUME, TIME, CUSTOMER_APPROVED, SELLER_APPROVED) "
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	
	private static final String GET_POSIBLE_BIDS = "SELECT L.ID LOT_ID, L.DESCRIPTION LOT_DESCRIPTION, L.PRODUCT_ID PRODUCT_ID, L.MIN_PRICE LOT_PRICE, "
			+ "L.SELLER_ID SELLER_ID, L.`STATUS` LOT_STATUS, L.EXP_DATE LOT_EXP_DATE, L.VOLUME LOT_VOLUME, L.MIN_VOLUME LOT_MIN_VOLUME, "
			+ "T.ID TENDER_ID, T.DESCRIPTION TENDER_DESCRIPTION, T.MAX_PRICE TENDER_PRICE, T.CUSTOMER_ID CUSTOMER_ID, "
			+ "T.`STATUS` TENDER_STATUS, T.EXP_DATE TENDER_EXP_DATE, T.VOLUME TENDER_VOLUME, T.MIN_VOLUME TENDET_MIN_VOLUME "
			+ "FROM LOT L "
			+ "JOIN TENDER T "
			+ "ON L.PRODUCT_ID = T.PRODUCT_ID "
			+ "AND L.`STATUS` = '" + BidStatus.ACTIVE +"' "
			+ "AND T.`STATUS` = '" + BidStatus.ACTIVE +"' "
			+ "AND L.MAX_VOLUME > L.MIN_VOLUME "
			+ "AND T.MAX_VOLUME > T.MIN_VOLUME "
			+ "AND T.MAX_PRICE <= L.MIN_PRICE "
			+ "AND T.MIN_VOLUME > L.MAX_VOLUME "
			+ "AND L.MIN_VOLUME > T.MAX_VOLUME "
			+ "LEFT JOIN BLACKLIST BL "
			+ "ON BL.TENDER_ID = T.ID AND BL.LOT_ID = L.ID "
			+ "WHERE BL.ID IS NULL AND T.PRODUCT_ID = ?";

	private static final String GET_BY_ID = 
			  " SELECT ID, LOT_ID, TENDER_ID, SELLER_ID CUSTOMER_ID, "
			+ " PRICE, VOLUME, TIME, CUSTOMER_APPROVED, SELLER_APPROVED "
			+ " FROM DEAL "
			+ " WHERE ID = ?";

	private static final String GET_ON_APPROVE = 
					  " SELECT ID, LOT_ID, TENDER_ID, SELLER_ID CUSTOMER_ID, "
					+ " PRICE, VOLUME, TIME, CUSTOMER_APPROVED, SELLER_APPROVED "
					+ " FROM DEAL "
					+ " WHERE (CUSTOMER_APPROVED IS NULL AND SELLER_APPROVED IS NULL) "
					+ " OR (CUSTOMER_APPROVED = 'Y' AND SELLER_APPROVED IS NULL) "
					+ " OR (CUSTOMER_APPROVED IS NULL AND SELLER_APPROVED = 'Y') ";

	private static final String DELETE_ON_APPROVE = 
					  " DELETE "
					+ " FROM DEAL "
					+ " WHERE (CUSTOMER_APPROVED IS NULL AND SELLER_APPROVED IS NULL) "
					+ " OR (CUSTOMER_APPROVED = 'Y' AND SELLER_APPROVED IS NULL) "
					+ " OR (CUSTOMER_APPROVED IS NULL AND SELLER_APPROVED = 'Y') ";

	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void add(Deal deal) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setLong(1, deal.getLotId());
				ps.setLong(2, deal.getTenderId());
				ps.setLong(3, deal.getSellerId());
				ps.setLong(4, deal.getCustomerId());
				ps.setDouble(5, deal.getPrice());
				ps.setInt(6, deal.getVolume());
				ps.setTimestamp(7, new Timestamp(deal.getTime().getTime()));
				ps.setString(8, deal.getCustomerApproved());
				ps.setString(8, deal.getSellerApproved());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		deal.setId(id);
	}
	
	@Override
	public void getPosibleDeals(List<TradeLot> tradeLots, List<TradeTender> tradeTenders, long productId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		Map<Long, TradeLot> lots = new HashMap<>();
		Map<Long, TradeTender> tenders = new HashMap<>();
		template.query(GET_POSIBLE_BIDS, new RowMapper<Void>(){

			@Override
			public Void mapRow(ResultSet rs, int rowNumber) throws SQLException {
				long lotId = rs.getLong("LOT_ID");
				TradeLot lot = lots.get(lotId);
				if (lot == null){
					lot = new TradeLot();
					lot.setId(lotId);
					lot.setDescription(rs.getString("LOT_DESCRIPTION"));
					lot.setExpDate(rs.getDate("LOT_EXP_DATE"));
					lot.setMinPrice(rs.getDouble("LOT_PRICE"));
					lot.setProductId(rs.getLong("PRODUCT_ID"));
					lot.setSellerId(rs.getLong("SELLER_ID"));
					lot.setStatus(BidStatus.valueOf(rs.getString("LOT_STATUS")));
					lot.setMaxVolume(rs.getInt("LOT_VOLUME"));
					lot.setMinVolume(rs.getInt("LOT_MIN_VOLUME"));
					lots.put(lotId, lot);
				}
				
				long tenderId = rs.getLong("TENDER_ID");
				TradeTender tender = tenders.get(tenderId);
				if (tender == null){
					tender = new TradeTender();
					tender.setId(tenderId);
					tender.setDescription(rs.getString("TENDER_DESCRIPTION"));
					tender.setExpDate(rs.getDate("TENDER_EXP_DATE"));
					tender.setMaxPrice(rs.getDouble("TENDER_PRICE"));
					tender.setProductId(rs.getLong("PRODUCT_ID"));
					tender.setCustomerId(rs.getLong("CUSTOMER_ID"));
					tender.setStatus(BidStatus.valueOf(rs.getString("TENDER_STATUS")));
					tender.setMaxVolume(rs.getInt("TENDER_VOLUME"));
					tender.setMinVolume(rs.getInt("TENDER_MIN_VOLUME"));
					tenders.put(tenderId, tender);
				}
				tender.addPosiblePartner(lot);
				lot.addPosiblePartner(tender);
				return null;
			}
			
		}, productId);
		
		tradeLots.addAll(lots.values());
		tradeTenders.addAll(tenders.values());
	}

	@Override
	public Deal getById(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Deal> list = template.query(GET_BY_ID, new BeanPropertyRowMapper<Deal>(), id);
		if (list.size() == 0){
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public List<Deal> getOnApprove() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_ON_APPROVE, new BeanPropertyRowMapper<Deal>());
	}

	@Override
	public void deleteOnApprove() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(DELETE_ON_APPROVE);
	}

	@Override
	public void add(List<Deal> deals) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.batchUpdate(ADD, deals, deals.size(), 
				new ParameterizedPreparedStatementSetter<Deal>() {

					@Override
					public void setValues(PreparedStatement ps, Deal deal) throws SQLException {
						ps.setLong(1, deal.getLotId());
						ps.setLong(2, deal.getTenderId());
						ps.setLong(3, deal.getSellerId());
						ps.setLong(4, deal.getCustomerId());
						ps.setDouble(5, deal.getPrice());
						ps.setInt(6, deal.getVolume());
						ps.setTimestamp(7, new Timestamp(deal.getTime().getTime()));
						ps.setString(8, deal.getCustomerApproved());
						ps.setString(8, deal.getSellerApproved());
					}
		});
	}

}
