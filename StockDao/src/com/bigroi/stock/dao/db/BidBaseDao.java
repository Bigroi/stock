package com.bigroi.stock.dao.db;

import java.sql.PreparedStatement;
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

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Bid;
import com.bigroi.stock.bean.db.TradeBid;
import com.bigroi.stock.dao.BidDao;

public abstract class BidBaseDao<T extends Bid, E extends TradeBid> implements BidDao<T, E>{
	
	private static final String AND = " AND ";
	private static final String WHERE = " WHERE ";
	private static final String SET = " SET ";
	private static final String UPDATE = "UPDATE ";
	private static final String STATUS = "STATUS = ? ";
	private static final String DELETE = "DELETE ";

	@Autowired
	protected DataSource datasource;

	private final String getBidById = 
			selectAllColumns()
			+ from()
			+ WHERE + getTableAlias() + ".ID = ?";
	
	private final String getBidsByCompanyId = 
			selectAllColumns()
			+ from()
			+ WHERE + getTableAlias() + ".COMPANY_ID = ?"
			+ AND + getTableAlias() + ".MIN_VOLUME <= " + getTableAlias() + ".MAX_VOLUME";
	
	private final String setStatusByCompanyId =
			UPDATE + getTableName() + SET
			+ STATUS
			+ "WHERE COMPANY_ID = ?";
	
	private final String setStatusByIdAndCompanyId =
			UPDATE + getTableName() + SET
			+ STATUS
			+ "WHERE ID = ? AND COMPANY_ID = ?";
	
	private final String setStatusByProductId =
			UPDATE + getTableName() + SET
			+ STATUS
			+ "WHERE PRODUCT_ID = ?";
	
	private final String deleteByDescription = 
			DELETE
			+ "FROM " + getTableName()
			+ " WHERE DESCRIPTION = ? AND COMPANY_ID = 0";
	
	protected final String updateMaxVolumeById =  
			UPDATE + getTableName()
			+ " SET MAX_VOLUME = ? "
			+ " WHERE ID = ?";
	
	private final String deleteByIdAndCompany = 
			DELETE
			+ " FROM " + getTableName() 
			+ " WHERE ID = ? AND COMPANY_ID = ?";
	
	private final String closeLots = 
			DELETE
			+ " FROM " + getTableName() 
			+ " WHERE MIN_VOLUME > MAX_VOLUME";
	
	private final String getActiveLots = 
			selectAllColumns()
			+ from()
			+ WHERE + getTableAlias() + ".`STATUS` = '" + BidStatus.ACTIVE.name() + "'";
	
	private final String getActiveLotsByProductId = 
			selectAllColumns()
			+ from()
			+ WHERE + getTableAlias() + ".PRODUCT_ID = ? "
			+ AND + getTableAlias() + ".`STATUS` = '" + BidStatus.ACTIVE.name() + "' "
			+ AND + getTableAlias() + ".MIN_VOLUME <= " + getTableAlias() + ".MAX_VOLUME ";
	
	private final String getLotsByDescription = 
			selectAllColumns()
			+ from()
			+ WHERE + getTableAlias() + ".COMPANY_ID = 0 "
			+ AND + getTableAlias() + ".DESCRIPTION = ?";
	
	private final String updateStatusById = 
			UPDATE + getTableName()
			+ " SET STATUS = ? "
			+ " WHERE ID = ?";
	
	protected abstract PreparedStatementCreator getPreparedStatementCreatorForAdding(T bid);
	protected abstract RowMapper<T> getRowMapper();
	protected abstract String selectAllColumns();
	protected abstract String from();
	protected abstract String getTableName();
	protected abstract String getTableAlias();
	
	@Override
	public void add(T bid) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(getPreparedStatementCreatorForAdding(bid), keyHolder);
		long id = keyHolder.getKey().longValue();
		bid.setId(id);
	}
	
	@Override
	public T getById(long id, long companyId) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<T> lots = template.query(getBidById, getRowMapper(), id);
		if(lots.isEmpty() || (companyId != -1 && lots.get(0).getCompanyId() != companyId)){
			return null;
		}else{
			return lots.get(0);
		}
	}
	
	@Override
	public List<T> getByCompanyId(long companyId) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(getBidsByCompanyId, getRowMapper(), companyId);
	}
	
	@Override
	public  boolean setStatusByCompanyId(long companyId, BidStatus status) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(setStatusByCompanyId, status.toString(), companyId) == 1;
		 
	}

	@Override
	public boolean setStatusByProductId(long productId, BidStatus status) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(setStatusByProductId, status.toString(), productId) == 1;
	}

	@Override
	public void updateStatus(List<T> bids) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.batchUpdate(updateStatusById, bids, bids.size(), new ParameterizedPreparedStatementSetter<T>() {
			@Override
			public void setValues(PreparedStatement ps, T bid) throws SQLException {
				ps.setString(1, bid.getStatus().name());
				ps.setLong(2, bid.getId());
			}
		});
	}
	
	@Override
	public boolean setStatusById(long id, long companyId, BidStatus status) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(setStatusByIdAndCompanyId, status.name(), id, companyId) == 1;
	}
	
	@Override
	public void deleteByDescription(String description) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(deleteByDescription, description);
	}
	
	@Override
	public void update(Collection<E> bids) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.batchUpdate(updateMaxVolumeById, bids, bids.size(), new ParameterizedPreparedStatementSetter<E>() {
			@Override
			public void setValues(PreparedStatement ps, E bid) throws SQLException {
				ps.setInt(1, bid.getMaxVolume());
				ps.setLong(2, bid.getId());
			}
		});
	}
	
	@Override
	public void delete(long id, long companyId) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(deleteByIdAndCompany, id, companyId);
	}
	
	@Override
	public void close() {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(closeLots);
	}

	@Override
	public List<T> getActive() {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(getActiveLots, getRowMapper());
	}

	@Override
	public List<T> getActiveByProductId(long productId) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(getActiveLotsByProductId, getRowMapper(), productId);
	}
	
	@Override
	public List<T> getByDescription(String description) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(getLotsByDescription, getRowMapper(), description);
	}
}
