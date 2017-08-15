package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.Blacklist;
import com.bigroi.stock.dao.BlacklistDao;
import com.bigroi.stock.dao.DaoException;
import com.mysql.jdbc.Statement;

public class BlacklistDaoImpl implements BlacklistDao {
	
	private static final Logger logger = Logger.getLogger(BlacklistDaoImpl.class);
	
	private static final String ADD_BLACKLIST_BY_ID = "INSERT INTO blacklist "
			+ " (id, tender_Id, lot_Id) VALUES (?, ?, ?)";

	private static final String DELETE_BLACKLIST_BY_ID = "DELETE FROM blacklist WHERE id = ? ";

	private static final String UPDATE_BLACKLIST_BY_ID = "UPDATE blacklist SET "
			+ " tender_Id = ?, lot_Id = ? WHERE id = ?";
	
	private static final String GET_LOT_ID_AND_TENDER_ID = "SELECT id FROM "
			+ " blacklist WHERE tender_Id = ? AND lot_Id = ? ";
	
	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void add(Blacklist blacklist) throws DaoException {
		logger.info("exection BlackListDaoImpl.add");
		logger.info(blacklist);
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_BLACKLIST_BY_ID, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, blacklist.getId());
				ps.setLong(2, blacklist.getTenderId());
				ps.setLong(3, blacklist.getLotId());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		blacklist.setId(id);
		logger.info("exection BlackListDaoImpl.add successfully finished");
	}

	@Override
	public boolean deletedById(long id) throws DaoException {
		logger.info("exection BlackListDaoImpl.deletedById");
		logger.info(id);
		JdbcTemplate template = new JdbcTemplate(datasource);
		logger.info("exection BlackListDaoImpl.deletedById successfully finished");
		return template.update(DELETE_BLACKLIST_BY_ID, id) == 1;
	}

	@Override
	public boolean updateById( Blacklist blacklist) throws DaoException {
		logger.info("exection BlackListDaoImpl.updateById");
		logger.info(blacklist);
		JdbcTemplate template = new JdbcTemplate(datasource);
		logger.info("exection BlackListDaoImpl.updateById successfully finished");
		return template.update(UPDATE_BLACKLIST_BY_ID, 
				blacklist.getTenderId(), blacklist.getLotId(), blacklist.getId()) == 1;
	}

	@Override
	public boolean getTenderIdAndLotId(long tenderId, long lotId) throws DaoException {
		logger.info("exection BlackListDaoImpl.getTenderIdAndLotId");
		logger.info(tenderId);
		logger.info(lotId);
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Blacklist> list = template.query(GET_LOT_ID_AND_TENDER_ID, 
				new BeanPropertyRowMapper<Blacklist>(Blacklist.class), tenderId, lotId);
		if(list.size() == 0){
			logger.info("exection BlackListDaoImpl.getTenderIdAndLotId return false, successfully finished");
			return false;
		}else{
			logger.info("exection BlackListDaoImpl.getTenderIdAndLotId return true, successfully finished");
			return true;
		}
	}
}
