package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.Archive;
import com.bigroi.stock.dao.ArchiveDao;
import com.bigroi.stock.dao.DaoException;
import com.mysql.jdbc.Statement;

public class ArchiveDaoImpl implements ArchiveDao {

	private static final String ADD_ARCHIVE_BY_ID = "INSERT INTO archive "
			+ "(id, salerId, customerId, productId, price, tms_tmp)"
			+ " VALUES (?, ?, ?, ?, ?, ?)";

	private static final String DELETE_ARCHIVE_BY_ID = "DELETE FROM archive WHERE id = ?";

	private static final String UPDATE_ARCHIVE_BY_ID = "UPDATE archive SET id = ?, salerId = ?,"
			+ "customerId = ?, productId = ?, price = ?, tms_tmp = ? WHERE id = ?";

	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void add(Archive archive) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_ARCHIVE_BY_ID, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, archive.getId());
				ps.setLong(2, archive.getSalerId());
				ps.setLong(3, archive.getCustomerId());
				ps.setLong(4, archive.getProductId());
				ps.setDouble(5, archive.getPrice());
				ps.setDate(6, new Date(archive.getTmsTmp().getTime()));
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		archive.setId(id);
	}

	@Override
	public void delete(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(DELETE_ARCHIVE_BY_ID, id);
	}

	@Override
	public void update(long id, Archive archive) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(UPDATE_ARCHIVE_BY_ID, archive.getId(), archive.getSalerId(), 
				archive.getCustomerId(),archive.getProductId(), archive.getPrice(), 
				archive.getTmsTmp(), id);

	}

}
