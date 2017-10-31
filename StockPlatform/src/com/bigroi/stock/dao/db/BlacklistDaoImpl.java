package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.Blacklist;
import com.bigroi.stock.dao.BlacklistDao;
import com.bigroi.stock.dao.DaoException;

public class BlacklistDaoImpl implements BlacklistDao {
	
	private static final String ADD_BLACKLIST_BY_ID = "INSERT INTO blacklist "
			+ " (id, tender_Id, lot_Id) VALUES (?, ?, ?)";

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
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_BLACKLIST_BY_ID, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setLong(1, blacklist.getId());
				ps.setLong(2, blacklist.getTenderId());
				ps.setLong(3, blacklist.getLotId());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		blacklist.setId(id);
	}

	@Override
	public boolean getTenderIdAndLotId(long tenderId, long lotId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Blacklist> list = template.query(GET_LOT_ID_AND_TENDER_ID, 
				new BeanPropertyRowMapper<Blacklist>(Blacklist.class), tenderId, lotId);
		if(list.size() == 0){
			return false;
		}else{
			return true;
		}
	}
}
