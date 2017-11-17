package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.PreDealDao;
import com.bigroi.stock.jobs.trade.TradeLot;
import com.bigroi.stock.jobs.trade.TradeTender;


public class PreDealDaoImpl implements PreDealDao {
	
	private static final String ADD_PREDEAL = "INSERT INTO PREDEAL "
			+ "(SELLERHASHCODE, CUSTOMERHASHCODE, TENDER_ID, LOT_ID, "
			+ "SELLERAPPROV, CUSTAPPROV, DEALDATE, VOLUME) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";

	private static final String DELETE_PREDEALS_BY_ID = "DELETE FROM PREDEAL "
			+ "WHERE ID = ?";
	
	private static final String DELETE_ALL_PREDEALS_BY_ID = "DELETE  FROM PREDEAL ";

	private static final String UPDATE_PREDEAL_BY_ID = "UPDATE PREDEAL SET "
			+ "SELLERHASHCODE =?, CUSTOMERHASHCODE =?, TENDER_ID =?, LOT_ID =?, "
			+ "SELLERAPPROV =?, CUSTAPPROV =?, DEALDATE =?, VOLUME = ? WHERE ID = ?";
			
	
	private static final String GET_ALL_PREDEALS_BY_ID = "SELECT ID, SELLERHASHCODE, "
			+ "CUSTOMERHASHCODE, TENDER_ID, LOT_ID, SELLERAPPROV, CUSTAPPROV, "
			+ "DEALDATE, VOLUME FROM PREDEAL ";
	
	private static final String GET_PREDEALS_BY_ID = "SELECT ID, SELLERHASHCODE, "
			+ "CUSTOMERHASHCODE, TENDER_ID, LOT_ID, SELLERAPPROV, "
			+ "CUSTAPPROV, DEALDATE, VOLUME FROM PREDEAL WHERE ID = ? ";
	
	private static final String GET_POSIBLE_BIDS = "SELECT L.ID LOT_ID, L.DESCRIPTION LOT_DESCRIPTION, L.PODUCT_ID PRODUCT_ID, L.MIN_PRICE LOT_PRICE, "
			+ "L.SELLER_ID SELLER_ID, L.`STATUS` LOT_STATUS, L.EXP_DATE LOT_EXP_DATE, L.VOLUME_OF_LOT LOT_VOLUME, "
			+ "T.ID TENDER_ID, T.DESCRIPTION TENDER_DESCRIPTION, T.MAX_PRICE TENDER_PRICE, T.CUSTOMER_ID CUSTOMER_ID, "
			+ "T.`STATUS` TENDER_STATUS, T.EXP_DATE TENDER_EXP_DATE, T.VOLUME_OF_TENDER TENDER_VOLUME "
			+ "FROM LOT L "
			+ "JOIN TENDER T "
			+ "ON L.PODUCT_ID = T.PRODUCT_ID "
			+ "AND L.`STATUS` = 'IN_GAME' "
			+ "AND T.`STATUS` = 'IN_GAME' "
			+ "AND L.VOLUME_OF_LOT > 0 "
			+ "AND T.VOLUME_OF_TENDER > 0 "
			+ "AND T.MAX_PRICE <= L.MIN_PRICE "
			+ "LEFT JOIN BLACKLIST BL "
			+ "ON BL.TENDER_ID = T.ID AND BL.LOT_ID = L.ID "
			+ "WHERE BL.ID IS NULL AND T.PRODUCT_ID = ?";
	
	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void add(PreDeal preDeal) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_PREDEAL, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, preDeal.getSellerHashCode());
				ps.setString(2, preDeal.getCustomerHashCode());
				ps.setLong(3, preDeal.getTenderId());
				ps.setLong(4, preDeal.getLotId());
				ps.setString(5, preDeal.getSellerApprov());
				ps.setString(6, preDeal.getCustApprov());
				ps.setDate(7, new Date(preDeal.getDealDate().getTime()));
				ps.setInt(8, preDeal.getVolume());
				return ps;
			}
		},keyHolder);
		long id = keyHolder.getKey().longValue();
		preDeal.setId(id);
	}

	@Override
	public boolean deletedById(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return	template.update(DELETE_PREDEALS_BY_ID, id) == 1;
	}

	@Override
	public void deleteAll() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(DELETE_ALL_PREDEALS_BY_ID);
	}

	
	@Override
	public boolean update( PreDeal preDeal) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return	template.update(UPDATE_PREDEAL_BY_ID, preDeal.getSellerHashCode(), 
				preDeal.getCustomerHashCode(), preDeal.getTenderId(),preDeal.getLotId(), 
				preDeal.getSellerApprov(), preDeal.getCustApprov(), 
				preDeal.getDealDate(), preDeal.getVolume(), preDeal.getId()) == 1;
	}

	@Override
	public List<PreDeal> getAll() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<PreDeal> list = template.query(GET_ALL_PREDEALS_BY_ID, new BeanPropertyRowMapper<PreDeal>(PreDeal.class));
		return list;
	}

	@Override
	public PreDeal getById(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<PreDeal> list = template.query(GET_PREDEALS_BY_ID, new BeanPropertyRowMapper<PreDeal>(PreDeal.class), id);
		if(list.size() == 0){
			return null;
		}else{
			return list.get(0);
		}
	}

	@Override
	public void add(List<PreDeal> preDeals) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.batchUpdate(ADD_PREDEAL, preDeals, preDeals.size(), new ParameterizedPreparedStatementSetter<PreDeal>() {
			@Override
			public void setValues(PreparedStatement ps, PreDeal preDeal) throws SQLException {
				ps.setString(1, preDeal.getSellerHashCode());
				ps.setString(2, preDeal.getCustomerHashCode());
				ps.setLong(3, preDeal.getTenderId());
				ps.setLong(4, preDeal.getLotId());
				ps.setString(5, preDeal.getSellerApprov());
				ps.setString(6, preDeal.getCustApprov());
				ps.setDate(7, new Date(System.currentTimeMillis()));
				ps.setInt(8, preDeal.getVolume());
			}
		});
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
					lot.setStatus(Status.valueOf(rs.getString("LOT_STATUS")));
					lot.setVolume(rs.getInt("LOT_VOLUME"));
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
					tender.setStatus(Status.valueOf(rs.getString("TENDER_STATUS")));
					tender.setVolume(rs.getInt("TENDER_VOLUME"));
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
}
