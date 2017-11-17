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

import com.bigroi.stock.bean.Product;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.ProductDao;

public class ProductDaoImpl implements ProductDao {
	
	private static final String GET_ALL_PRODUCTS = "SELECT ID, NAME, DESCRIPTION, ARCHIVE FROM PRODUCT ";

	private static final String GET_ALL_ACTIVE_PRODUCTS = "SELECT ID, NAME, DESCRIPTION, ARCHIVE FROM PRODUCT WHERE ARCHIVE = 'N'";
	
	private static final String ADD_PRODUCT = "INSERT INTO PRODUCT (NAME, DESCRIPTION, ARCHIVE) " 
	        + "VALUES (?, ?, ?)";

	private static final String UPDATE_PRODUCTS_BY_ID = "UPDATE PRODUCT SET NAME = ?, "
			+ " DESCRIPTION = ? WHERE ID = ?";

	private static final String GET_PRODUCT_BY_ID = "SELECT ID, NAME, DESCRIPTION "
			+ " FROM PRODUCT WHERE ID = ? ";
	
	private static final String SET_ARCHIVE_PRODUCT_BY_ID = "UPDATE product SET archive = 'Y' WHERE id = ?";
	
	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public List<Product> getAllProducts() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Product> products = template.query(GET_ALL_PRODUCTS, new BeanPropertyRowMapper<Product>(Product.class));
		return products;
	}
	
	@Override
	public List<Product> getAllActiveProducts() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Product> products = template.query(GET_ALL_ACTIVE_PRODUCTS, new BeanPropertyRowMapper<Product>(Product.class));
		return products;
	}

	@Override
	public void add(Product product) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_PRODUCT, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, product.getName());
				ps.setString(2, product.getDescription());
				ps.setString(3, product.getArchiveData());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		product.setId(id);
	}

	@Override
	public boolean update(Product product) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return	template.update(UPDATE_PRODUCTS_BY_ID, product.getName(), 
								product.getDescription(), product.getId()) == 1;
	}

	@Override
	public Product getById(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Product> product =  template.query(GET_PRODUCT_BY_ID,
				new BeanPropertyRowMapper<Product>(Product.class), id);
		if (product.size() == 0) {
			return null;
		} else {
			return product.get(0);
		}
	}

	@Override
	public void setArchived(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(SET_ARCHIVE_PRODUCT_BY_ID,  id);
	}

}
