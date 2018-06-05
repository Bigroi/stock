package com.bigroi.stock.dao.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Address;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DealDao;
import com.bigroi.stock.jobs.trade.TradeLot;
import com.bigroi.stock.jobs.trade.TradeTender;

@Repository
public class DealDaoImpl implements DealDao {
	
	private static final String ADD = 
			  " INSERT INTO DEAL(LOT_ID, TENDER_ID, TIME, BUYER_APPROVED, SELLER_APPROVED, "
			+ " PRICE, VOLUME, PRODUCT_ID, SELLER_FOTO, MAX_TRANSPORT_PRICE "
			+ " SELLER_ADDRESS_ID, BUYER_ADDRESS_ID, SELLER_DESCRIPTION, BUYER_DESCRIPTION) "
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	
	private static final String GET_POSIBLE_BIDS = 
			  "SELECT "
			  + " L.ID LOT_ID, L.DESCRIPTION LOT_DESCRIPTION, L.MIN_PRICE LOT_PRICE, "
			  + " L.`STATUS` LOT_STATUS, L.EXPARATION_DATE LOT_EXP_DATE, L.MAX_VOLUME LOT_VOLUME, L.MIN_VOLUME LOT_MIN_VOLUME, "
			  + " LA.LONGITUDE LOT_LONGITUDE, LA.LATITUDE LOT_LATITUDE, LA.COMPANY_ID LOT_COMPANY_ID, LA.ID LOT_ADDRESS_ID, "
			  
			  + " T.ID TENDER_ID, T.DESCRIPTION TENDER_DESCRIPTION, T.MAX_PRICE TENDER_PRICE, "
			  + " T.`STATUS` TENDER_STATUS, T.EXPARATION_DATE TENDER_EXP_DATE, T.MAX_VOLUME TENDER_VOLUME, T.MIN_VOLUME TENDER_MIN_VOLUME,  "
			  + " TA.LONGITUDE TENDER_LONGITUDE, TA.LATITUDE TENDER_LATITUDE, TA.COMPANY_ID TENDER_COMPANY_ID, TA.ID TENDER_ADDRESS_ID, "
			  
			  + " L.PRODUCT_ID PRODUCT_ID "
			  + " FROM LOT L "
			  + " JOIN ADDRESS LA "
			  + " ON L.ADDRESS_ID = LA.ID "
			  + " AND L.`STATUS` = 'ACTIVE' "
			  + " JOIN TENDER T "
			  + " ON L.PRODUCT_ID = T.PRODUCT_ID "
			  + " AND T.`STATUS` = 'ACTIVE' "
			  + " AND L.MAX_VOLUME >= L.MIN_VOLUME "
			  + " AND T.MAX_VOLUME >= T.MIN_VOLUME "
			  + " AND T.MAX_PRICE >= L.MIN_PRICE "
			  + " AND T.MIN_VOLUME <= L.MAX_VOLUME "
			  + " AND L.MIN_VOLUME <= T.MAX_VOLUME "
			  + " JOIN ADDRESS TA "
			  + " ON T.ADDRESS_ID = TA.ID "
			  + " AND TA.COMPANY_ID <>LA.COMPANY_ID "
			  + " LEFT JOIN BLACK_LIST BL "
			  + " ON BL.TENDER_ID = T.ID AND BL.LOT_ID = L.ID "
			  + " WHERE BL.ID IS NULL AND T.PRODUCT_ID = ?";

	private static final String GET_BY_ID = 
			" SELECT " + DealRowMapper.ALL_COLUMNS
			+ " FROM DEAL D "
			+ " JOIN PRODUCT P "
			+ " ON D.PRODUCT_ID = P.ID "
			+ " JOIN ADDRESS SA "
			+ " ON SA.ID = D.SELLER_ADDRESS_ID "
			+ " JOIN ADDRESS BA "
			+ " ON BA.ID = D.BUYER_ADDRESS_ID "
			+ " WHERE D.ID = ?";

	private static final String GET_ON_APPROVE = 
					  " SELECT " + DealRowMapper.ALL_COLUMNS
					  + " FROM DEAL D "
					  + " JOIN PRODUCT P "
					  + " ON D.PRODUCT_ID = P.ID "
					  + " JOIN ADDRESS SA "
					  + " ON SA.ID = D.SELLER_ADDRESS_ID "
					  + " JOIN ADDRESS BA "
					  + " ON BA.ID = D.BUYER_ADDRESS_ID "
					  + " WHERE (BUYER_APPROVED IS NULL AND SELLER_APPROVED <> 'N') "
					+ " OR (SELLER_APPROVED IS NULL AND BUYER_APPROVED <> 'N') "
					+ " OR (SELLER_APPROVED IS NULL AND BUYER_APPROVED IS NULL)";

	private static final String DELETE_ON_APPROVE = 
					  " DELETE "
					+ " FROM DEAL "
					+ " WHERE (BUYER_APPROVED IS NULL AND SELLER_APPROVED <> 'N') "
					+ " OR (SELLER_APPROVED IS NULL AND BUYER_APPROVED <> 'N')"
					+ " OR (SELLER_APPROVED IS NULL AND BUYER_APPROVED IS NULL)";

	private static final String GET_BY_COMPANY_ID = 
			" SELECT " + DealRowMapper.ALL_COLUMNS
					+ " FROM DEAL D "
					+ " JOIN PRODUCT P "
					+ " ON D.PRODUCT_ID = P.ID "
					+ " JOIN ADDRESS SA "
					+ " ON SA.ID = D.SELLER_ADDRESS_ID "
					+ " JOIN ADDRESS BA "
					+ " ON BA.ID = D.BUYER_ADDRESS_ID "
					+ " WHERE BA.COMPANY_ID = ? OR SA.COMPANY_ID = ?";

	private static final String SET_SELLER_STATUS = 
			  " UPDATE DEAL "
			+ " SET SELLER_APPROVED = ? "
			+ " WHERE ID = ? ";
	
	private static final String SET_BUYER_STATUS = 
			  " UPDATE DEAL "
			+ " SET BUYER_APPROVED = ? "
			+ " WHERE ID = ? ";

	@Autowired
	private DataSource datasource;
	
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
					lot.setExparationDate(rs.getDate("LOT_EXP_DATE"));
					lot.setMinPrice(rs.getDouble("LOT_PRICE"));
					lot.setProductId(rs.getLong("PRODUCT_ID"));
					lot.setStatus(BidStatus.valueOf(rs.getString("LOT_STATUS")));
					lot.setMaxVolume(rs.getInt("LOT_VOLUME"));
					lot.setMinVolume(rs.getInt("LOT_MIN_VOLUME"));
					lot.setAddressId(rs.getLong("LOT_ADDRESS_ID"));
					
					Address address = new Address();
					address.setLatitude(rs.getDouble("LOT_LATITUDE"));
					address.setLongitude(rs.getDouble("LOT_LONGITUDE"));
					address.setCompanyId(rs.getLong("LOT_COMPANY_ID"));
					address.setId(rs.getLong("LOT_ADDRESS_ID"));
					lot.setAddress(address);
					
					lots.put(lotId, lot);
				}

				long tenderId = rs.getLong("TENDER_ID");
				TradeTender tender = tenders.get(tenderId);
				if (tender == null){
					tender = new TradeTender();
					tender.setId(tenderId);
					tender.setDescription(rs.getString("TENDER_DESCRIPTION"));
					tender.setExparationDate(rs.getDate("TENDER_EXP_DATE"));
					tender.setMaxPrice(rs.getDouble("TENDER_PRICE"));
					tender.setProductId(rs.getLong("PRODUCT_ID"));
					tender.setStatus(BidStatus.valueOf(rs.getString("TENDER_STATUS")));
					tender.setMaxVolume(rs.getInt("TENDER_VOLUME"));
					tender.setMinVolume(rs.getInt("TENDER_MIN_VOLUME"));
					tender.setAddressId(rs.getLong("TENDER_ADDRESS_ID"));
					
					Address address = new Address();
					address.setLatitude(rs.getDouble("TENDER_LATITUDE"));
					address.setLongitude(rs.getDouble("TENDER_LONGITUDE"));
					address.setCompanyId(rs.getLong("TENDER_COMPANY_ID"));
					address.setId(rs.getLong("TENDER_ADDRESS_ID"));
					tender.setAddress(address);
					
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
		List<Deal> list = template.query(GET_BY_ID, new DealRowMapper(), id);
		if (list.size() == 0){
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public List<Deal> getOnApprove() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_ON_APPROVE, new DealRowMapper());
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
						ps.setTimestamp(3, new Timestamp(deal.getTime().getTime()));
						ps.setString(4, deal.getBuyerApproved());
						ps.setString(5, deal.getSellerApproved());
						ps.setDouble(6, deal.getPrice());
						ps.setInt(7, deal.getVolume());
						ps.setLong(8, deal.getProductId());
						ps.setString(9, deal.getSellerFoto());
						ps.setDouble(10, deal.getMaxTransportPrice());
						ps.setLong(11, deal.getSellerAddressId());
						ps.setLong(12, deal.getBuyerAddressId());
						ps.setString(13, deal.getSellerDescription());
						ps.setString(14, deal.getBuyerDescription());
						
					}
		});
	}

	@Override
	public List<Deal> getByCompanyId(long companyId) throws DaoException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		return jdbcTemplate.query(GET_BY_COMPANY_ID, new DealRowMapper(), companyId, companyId);
	}
	
	@Override
	public void setBuyerStatus(Deal deal) throws DaoException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		jdbcTemplate.update(SET_BUYER_STATUS, deal.getBuyerApproved(), deal.getId());
	}
	
	@Override
	public void setSellerStatus(Deal deal) throws DaoException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		jdbcTemplate.update(SET_SELLER_STATUS, deal.getSellerApproved(), deal.getId());
	}

	private static final class DealRowMapper implements RowMapper<Deal>{

		private static final String ALL_COLUMNS = 
				" D.ID, D.LOT_ID, D.TENDER_ID, D.TIME, D.BUYER_APPROVED, D.SELLER_APPROVED, "
				+ " D.PRICE, D.MAX_TRANSPORT_PRICE, D.VOLUME, D.PRODUCT_ID, D.SELLER_FOTO, "
				+ " D.SELLER_ADDRESS_ID, D.BUYER_ADDRESS_ID, D.SELLER_DESCRIPTION, D.BUYER_DESCRIPTION, "
				+ " P.NAME PRODUCT_NAME,  "
				+ " SA.COMPANY_ID SELLER_COMPANY_ID, SA.LATITUDE SELLER_LATITUDE, SA.LONGITUDE SELLER_LONGITUDE, "
				+ " SA.CITY SELLER_CITY, SA.COUNTRY SELLER_COUNTRY, SA.ADDRESS SELLER_ADDRESS, "
				+ " BA.COMPANY_ID BUYER_COMPANY_ID, BA.LATITUDE BUYER_LATITUDE, BA.LONGITUDE BUYER_LONGITUDE, "
				+ " BA.CITY BUYER_CITY, BA.COUNTRY BUYER_COUNTRY, BA.ADDRESS BUYER_ADDRESS ";
		
		
		@Override
		public Deal mapRow(ResultSet rs, int rowNum) throws SQLException {
			Deal deal = new Deal();
			
			deal.setId(rs.getLong("ID"));
			deal.setLotId(rs.getLong("LOT_ID"));
			deal.setTenderId(rs.getLong("TENDER_ID"));
			deal.setTime(rs.getTimestamp("TIME"));
			deal.setBuyerApproved(rs.getString("BUYER_APPROVED"));
			deal.setSellerApproved(rs.getString("SELLER_APPROVED"));
			deal.setPrice(rs.getDouble("PRICE"));
			deal.setMaxTransportPrice(rs.getDouble("MAX_TRANSPORT_PRICE"));
			deal.setVolume(rs.getInt("VOLUME"));
			deal.setProductId(rs.getLong("PRODUCT_ID"));
			deal.setSellerFoto(rs.getString("SELLER_FOTO"));
			deal.setSellerAddressId(rs.getLong("SELLER_ADDRESS_ID"));
			deal.setBuyerAddressId(rs.getLong("BUYER_ADDRESS_ID"));
			deal.setSellerDescription(rs.getString("SELLER_DESCRIPTION"));
			deal.setBuyerDescription(rs.getString("BUYER_DESCRIPTION"));
			
			Product product = new Product();
			product.setId(rs.getLong("PRODUCT_ID"));
			product.setName(rs.getString("PRODUCT_NAME"));
			deal.setProduct(product);
			
			Address address = new Address();
			address.setCompanyId(rs.getLong("SELLER_COMPANY_ID"));
			address.setLatitude(rs.getDouble("SELLER_LATITUDE"));
			address.setLongitude(rs.getDouble("SELLER_LONGITUDE"));
			address.setCity(rs.getString("SELLER_CITY"));
			address.setCountry(rs.getString("SELLER_COUNTRY"));
			address.setAddress(rs.getString("SELLER_ADDRESS"));
			address.setId(rs.getLong("SELLER_ADDRESS_ID"));
			deal.setSellerAddress(address);
			
			address = new Address();
			address.setCompanyId(rs.getLong("BUYER_COMPANY_ID"));
			address.setLatitude(rs.getDouble("BUYER_LATITUDE"));
			address.setLongitude(rs.getDouble("BUYER_LONGITUDE"));
			address.setCity(rs.getString("BUYER_CITY"));
			address.setCountry(rs.getString("BUYER_COUNTRY"));
			address.setAddress(rs.getString("BUYER_ADDRESS"));
			address.setId(rs.getLong("BUYER_ADDRESS_ID"));
			deal.setBuyerAddress(address);
			
			return deal;
		}
		
	}
}
