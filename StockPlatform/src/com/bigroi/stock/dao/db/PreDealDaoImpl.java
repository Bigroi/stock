package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.PreDealDao;
import com.bigroi.stock.util.TmprCountEdges;


public class PreDealDaoImpl implements PreDealDao {
	
	private static final String ADD_PREDEALS_BY_ID = "INSERT INTO predeal "
			+ "(id, sellerHashCode, customerHashCode, tender_Id, lot_Id, "
			+ "sellerApprov, custApprov, dealDate) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";

	private static final String DELETE_PREDEALS_BY_ID = "DELETE FROM predeal "
			+ "WHERE id = ?";
	
	private static final String DELETE_ALL_PREDEALS_BY_ID = "DELETE  FROM predeal ";

	private static final String UPDATE_PREDEALS_BY_ID = "UPDATE predeal SET "
			+ "sellerHashCode =?, customerHashCode =?, tender_Id =?, lot_Id =?, "
			+ "sellerApprov =?, custApprov =?, dealDate =? WHERE id = ?";
			
	
	private static final String GET_ALL_PREDEALS_BY_ID = "SELECT id, sellerHashCode, "
			+ "customerHashCode, tender_Id, lot_Id, sellerApprov, custApprov, "
			+ "dealDate FROM predeal ";
	
	private static final String SELECT_PREDEALS_BY_ID = "SELECT id, sellerHashCode, "
			+ "customerHashCode, tender_Id, lot_Id, sellerApprov, "
			+ "custApprov, dealDate FROM predeal WHERE id = ? ";
	
	private static final String GET_ALL_EDGES =" SELECT  tender.id AS tender_id, "
			+ "lot.id AS lot_id, tender.product_Id, tender.max_price, "
			+ "lot.min_price FROM lot JOIN tender ON  tender.product_Id = lot.poduct_Id "
			+ "AND tender.`status` = 'IN_GAME' AND lot.`status` = 'IN_GAME' "
			+ "AND lot.min_price <= tender.max_price LEFT JOIN blacklist ON "
			+ "blacklist.tender_Id = tender.id AND blacklist.lot_Id = lot.id WHERE blacklist.id = blacklist.id ";
	//TODO: WHERE blacklist.id = blacklist.id --> WHERE blacklist.id IS NULL ...?
	
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
				PreparedStatement ps = con.prepareStatement(ADD_PREDEALS_BY_ID, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, preDeal.getId());
				ps.setString(2, preDeal.getSellerHashCode());
				ps.setString(3, preDeal.getCustomerHashCode());
				ps.setLong(4, preDeal.getTenderId());
				ps.setLong(5, preDeal.getLotId());
				ps.setString(6, preDeal.getSellerApprov());
				ps.setString(7, preDeal.getCustApprov());
				ps.setDate(8, new Date(preDeal.getDealDate().getTime()));
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
	public boolean updateById( PreDeal preDeal) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
	return	template.update(UPDATE_PREDEALS_BY_ID, preDeal.getSellerHashCode(), 
				preDeal.getCustomerHashCode(), preDeal.getTenderId(),preDeal.getLotId(), 
				preDeal.getSellerApprov(), preDeal.getCustApprov(), 
				preDeal.getDealDate(), preDeal.getId()) == 1;
	}

	@Override
	public List<PreDeal> getAllPreDeal() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<PreDeal> list = template.query(GET_ALL_PREDEALS_BY_ID, new BeanPropertyRowMapper<PreDeal>(PreDeal.class));
		return list;
	}

	@Override
	public PreDeal getById(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<PreDeal> list = template.query(SELECT_PREDEALS_BY_ID, new BeanPropertyRowMapper<PreDeal>(PreDeal.class), id);
		if(list.size() == 0){
			return null;
		}else{
			return list.get(0);
		}
	}

	@Override
	public List<TmprCountEdges> getAllEdges() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<TmprCountEdges> list = template.query(GET_ALL_EDGES, new RowMapper<TmprCountEdges>() {
			@Override
			public TmprCountEdges mapRow(ResultSet rs, int rowNum) throws SQLException {
				TmprCountEdges temp = new TmprCountEdges();
				temp.setTenderId(rs.getLong("tender_id"));
				temp.setLotId(rs.getLong("lot_id"));
				temp.setProductId(rs.getLong("product_Id"));
				temp.setMaxPrice(rs.getDouble("max_price"));
				temp.setMinPrice(rs.getDouble("min_price"));
				return temp;
			}
		});	
		return list;
	}
}
