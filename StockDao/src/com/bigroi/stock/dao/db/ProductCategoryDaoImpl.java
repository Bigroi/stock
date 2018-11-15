package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.ProductCategory;
import com.bigroi.stock.dao.ProductCategoryDao;

@Repository
public class ProductCategoryDaoImpl implements ProductCategoryDao{

	private static final String WHERE_ID = " WHERE ID = ?";
	private static final String SELECT_ALL_COLUMNS = "SELECT ID, PRODUCT_ID, CATEGORY_NAME, REMOVED";
	private static final String FROM = " FROM PRODUCT_CATEGORY ";
	
	private static final String GET_BY_PRODUCT_ID = 
			SELECT_ALL_COLUMNS
			+ FROM
			+ " WHERE PRODUCT_ID = ?";
	
	private static final String GET_ACTIVE_BY_PRODUCT_ID = 
			SELECT_ALL_COLUMNS
			+ FROM
			+ " WHERE PRODUCT_ID = ? AND REMOVED = 'N'";
	
	private static final String GET_BY_ID = 
			SELECT_ALL_COLUMNS
			+ FROM
			+ WHERE_ID;
	
	private static final String DELETE_BY_ID = 
			"UPDATE PRODUCT_CATEGORY "
			+ " SET REMOVED = 'Y' "
			+ WHERE_ID;
	
	private static final String UPDATE_BY_ID = 
			"UPDATE PRODUCT_CATEGORY "
			+ " SET CATEGORY_NAME = ?, REMOVED = 'N' "
			+ WHERE_ID;
	
	private static final String ADD_CATEGORY = 
			"INSERT INTO PRODUCT_CATEGORY (" + SELECT_ALL_COLUMNS + ")"
			+ " VALUES (DEFAULT, ?, ? , 'N') ";
	
	@Autowired
	private DataSource datasource;

	@Override
	public ProductCategory getById(long id) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<ProductCategory> list = template.query(GET_BY_ID, new BeanPropertyRowMapper<ProductCategory>(ProductCategory.class), id);
		if (list.isEmpty()){
			return null;
		} else {
			return list.get(0);
		}
	}
	
	@Override
	public List<ProductCategory> getByProductId(long productId) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_BY_PRODUCT_ID, new BeanPropertyRowMapper<ProductCategory>(ProductCategory.class), productId);
	}
	
	@Override
	public List<ProductCategory> getActiveByProductId(long productId) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_ACTIVE_BY_PRODUCT_ID, new BeanPropertyRowMapper<ProductCategory>(ProductCategory.class), productId);
	}
	
	@Override
	public boolean update(ProductCategory category){
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_BY_ID, 
				category.getCategoryName().trim(),
				category.getId()) == 1;
	}
	
	@Override
	public void add(ProductCategory category){
		JdbcTemplate template = new JdbcTemplate(datasource);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_CATEGORY, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setLong(1, category.getProductId());
				ps.setString(2, category.getCategoryName().trim());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		category.setId(id);
		category.setRemoved("N");
	}
		
	@Override
	public boolean delete(long id){
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(DELETE_BY_ID, id) == 1;
	}

}
