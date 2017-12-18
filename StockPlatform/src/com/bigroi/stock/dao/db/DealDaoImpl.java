package com.bigroi.stock.dao.db;

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
import org.springframework.jdbc.core.RowMapper;

import com.bigroi.stock.bean.Deal;
import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.common.DealStatus;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DealDao;
import com.bigroi.stock.jobs.trade.TradeLot;
import com.bigroi.stock.jobs.trade.TradeTender;

public class DealDaoImpl implements DealDao {
	
	private static final String ADD = 
			  " INSERT INTO DEAL(LOT_ID, TENDER_ID, SELLER_ID CUSTOMER_ID, "
			+ " PRICE, VOLUME, TIME, CUSTOMER_APPROVED, SELLER_APPROVED, PRODUCT_ID) "
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	
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
					  " SELECT D.ID, D.`TIME`, D.CUSTOMER_APPROVED, D.SELLER_APPROVED, "
					+ " D.PRICE, D.VOLUME, D.LOT_ID, D.TENDER_ID, D.PRODUCT_ID, "
					+ " CUSTOMER.ID CUSTOMER_ID, CUSTOMER.NAME CUSTOMER_NAME, "
					+ " CUSTOMER.PHONE CUSTOMER_PHONE, CUSTOMER.REG_NUMBER CUSTOMER_REG_NUMBER, "
					+ " CONCAT(CONCAT(CONCAT(CONCAT(CUSTOMER.COUNTRY, ' '), CUSTOMER.CITY), ' '),CUSTOMER.ADDRESS) CUSTOMER_ADDRESS, "
					+ " CUSTOMER.LONGITUDE CUSTOMER_LONGITUDE, CUSTOMER.LATITUDE CUSTOMER_LATITUDE, "
					+ " SELLER.ID SELLER_ID, SELLER.NAME SELLER_NAME, SELLER.PHONE SELLER_PHONE, "
					+ " SELLER.REG_NUMBER SELLER_REG_NUMBER, P.NAME PRODUCT_NAME, "
					+ " CONCAT(CONCAT(CONCAT(CONCAT(SELLER.COUNTRY, ' '), SELLER.CITY), ' '), SELLER.ADDRESS) SELLER_ADDRESS, "
					+ " SELLER.LONGITUDE SELLER_LONGITUDE, SELLER.LATITUDE SELLER_LATITUDE, "
					+ " T.DESCRIPTION CUSTOMER_DESCRIPTION, L.DESCRIPTION SELLER_DESCRIPTION "
					+ " FROM DEAL D "
					+ " JOIN PRODUCT P "
					+ " ON D.PRODUCT_ID = P.ID "
					+ " JOIN COMPANY CUSTOMER "
					+ " ON D.CUSTOMER_ID = CUSTOMER.ID "
					+ " JOIN COMPANY SELLER "
					+ " ON D.SELLER_ID = SELLER.ID "
					+ " LEFT JOIN LOT L "
					+ " ON D.LOT_ID = L.ID "
					+ " LEFT JOIN TENDER T "
					+ " ON D.TENDER_ID = T.ID "
					+ " WHERE D.ID = ?";

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

	private static final String GET_BY_COMPANY_ID = 
			  " SELECT D.ID, D.`TIME`, D.CUSTOMER_APPROVED, D.SELLER_APPROVED, "
			+ " D.PRICE, D.VOLUME, D.LOT_ID, D.TENDER_ID, D.PRODUCT_ID, "
			+ " CUSTOMER.ID CUSTOMER_ID, CUSTOMER.NAME CUSTOMER_NAME, "
			+ " CUSTOMER.PHONE CUSTOMER_PHONE, CUSTOMER.REG_NUMBER CUSTOMER_REG_NUMBER, "
			+ " CONCAT(CONCAT(CONCAT(CONCAT(CUSTOMER.COUNTRY, ' '), CUSTOMER.CITY), ' '),CUSTOMER.ADDRESS) CUSTOMER_ADDRESS, "
			+ " CUSTOMER.LONGITUDE CUSTOMER_LONGITUDE, CUSTOMER.LATITUDE CUSTOMER_LATITUDE, "
			+ " SELLER.ID SELLER_ID, SELLER.NAME SELLER_NAME, SELLER.PHONE SELLER_PHONE, "
			+ " SELLER.REG_NUMBER SELLER_REG_NUMBER, P.NAME PRODUCT_NAME, "
			+ " CONCAT(CONCAT(CONCAT(CONCAT(SELLER.COUNTRY, ' '), SELLER.CITY), ' '), SELLER.ADDRESS) SELLER_ADDRESS, "
			+ " SELLER.LONGITUDE SELLER_LONGITUDE, SELLER.LATITUDE SELLER_LATITUDE, "
			+ " T.DESCRIPTION CUSTOMER_DESCRIPTION, L.DESCRIPTION SELLER_DESCRIPTION "
			+ " FROM DEAL D "
			+ " JOIN PRODUCT P "
			+ " ON D.PRODUCT_ID = P.ID "
			+ " JOIN COMPANY CUSTOMER "
			+ " ON D.CUSTOMER_ID = CUSTOMER.ID "
			+ " JOIN COMPANY SELLER "
			+ " ON D.SELLER_ID = SELLER.ID "
			+ " LEFT JOIN LOT L "
			+ " ON D.LOT_ID = L.ID "
			+ " LEFT JOIN TENDER T "
			+ " ON D.TENDER_ID = T.ID "
			+ " WHERE CUSTOMER.ID = ? OR SELLER.ID = ? ";

	private static final String UPDATE_BY_ID = 
			  " UPDATE DEAL "
			+ " SET lot_Id = ?, "
			+ " tender_Id = ?, "
			+ " time = ?, "
			+ " customer_approved = ?, "
			+ " seller_id = ?, "
			+ " seller_approved = ?, "
			+ " customer_id = ?, "
			+ " price = ?, "
			+ " volume = ?, "
			+ " product_id = ? "
			+ " WHERE id = ? ";

	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
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
	public Deal getById(long id, long companyId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Deal> list = template.query(GET_BY_ID, new DealRowMapper(companyId), id);
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
						ps.setString(9, deal.getSellerApproved());
						ps.setLong(10, deal.getProductId());
					}
		});
	}

	@Override
	public List<Deal> getByCompanyId(long companyId) throws DaoException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		return jdbcTemplate.query(GET_BY_COMPANY_ID, new DealRowMapper(companyId), companyId, companyId);
	}
	
	@Override
	public void update(Deal deal) throws DaoException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		jdbcTemplate.update(UPDATE_BY_ID, 
				deal.getLotId(),
				deal.getTenderId(),
				deal.getTime(),
				deal.getCustomerApproved(),
				deal.getSellerId(),
				deal.getSellerApproved(),
				deal.getCustomerId(),
				deal.getPrice(),
				deal.getVolume(),
				deal.getProductId(),
				deal.getId());
		
	}

	private class DealRowMapper implements RowMapper<Deal>{

		private long companyId;
		
		public DealRowMapper(long companyId) {
			this.companyId = companyId;
		}
		
		@Override
		public Deal mapRow(ResultSet rs, int lineNumber) throws SQLException {
			Deal deal = new Deal();
			deal.setVolume(rs.getInt("VOLUME"));
			deal.setPrice(rs.getDouble("PRICE"));
			deal.setId(rs.getLong("ID"));
			deal.setTime(rs.getTimestamp("TIME"));
			deal.setProductName(rs.getString("PRODUCT_NAME"));
			deal.setCustomerId(rs.getLong("CUSTOMER_ID"));
			deal.setSellerId(rs.getLong("SELLER_ID"));
			deal.setTenderId(rs.getLong("TENDER_ID"));
			deal.setLotId(rs.getLong("LOT_ID"));
			deal.setProductId(rs.getLong("PRODUCT_ID"));

			long customerId = rs.getLong("CUSTOMER_ID");
			String you;
			String partner;
			if (customerId == companyId){
				you = "CUSTOMER";
				partner = "SELLER";
			} else {
				partner = "CUSTOMER";
				you = "SELLER";
			}
			
			deal.setPartnerAddress(rs.getString(partner + "_ADDRESS"));
			deal.setPartnerComment(rs.getString(partner + "_DESCRIPTION"));
			deal.setPartnerName(rs.getString(partner + "_NAME"));
			deal.setPartnerPhone(rs.getString(partner + "_PHONE"));
			deal.setPartnerRegNumber(rs.getString(partner + "_REG_NUMBER"));
			deal.setLatitude(rs.getDouble(partner + "_LATITUDE"));
			deal.setLongitude(rs.getDouble(partner + "_LONGITUDE"));
			
			String approvedByYou = rs.getString(you + "_APPROVED");
			String approvedByPartner = rs.getString(partner + "_APPROVED");
			if ((approvedByPartner != null && approvedByPartner.equals("N")) || 
					(approvedByYou != null && approvedByYou.equals("N"))){
				deal.setStatus(DealStatus.REJECTED);
			} else if (approvedByYou == null){
				deal.setStatus(DealStatus.ON_APPROVE);
			} else if (approvedByPartner == null){
				deal.setStatus(DealStatus.ON_PARTNER_APPROVE);
			} else {
				deal.setStatus(DealStatus.APPROVED);
			}
			return deal;
		}
		
	}

}
