package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.Product;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.ProductDao;
import com.mysql.jdbc.Statement;

public class ProductDaoImpl implements ProductDao {
	
	private static final Logger logger = Logger.getLogger(ProductDaoImpl.class);
	
	private static final Logger lOG = Logger.getLogger(ProductDaoImpl.class);

	private static final String GET_ALL_PRODUCTS = "SELECT id, name, description FROM product ";

	private static final String ADD_PRODUCTS_BY_ID = "INSERT INTO product (id, name, description) " 
	        + "VALUES (?, ?, ?)";

	private static final String DELETE_PRODUCTS_BY_ID = "DELETE FROM product WHERE id = ? ";

	private static final String UPDATE_PRODUCTS_BY_ID = "UPDATE product SET name = ?, "
			+ " description = ? WHERE id = ?";

	private static final String SELECT_PRODUCTS_BY_ID = "SELECT id, name, description "
			+ " FROM product WHERE id = ? ";
	
	private static final String SELECT_PROUCTS_BY_PROUCTS_ID = "SELECT DISTINCT poduct_Id "
			+ "FROM lot WHERE status = 'IN_GAME' AND poduct_Id IN"
			+ " ( SELECT product_Id FROM tender WHERE status = 'IN_GAME')";

	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public List<Product> getAllProduct() throws DaoException {
		logger.info("exection ProductDaoImpl.getAllProduct");
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Product> products = template.query(GET_ALL_PRODUCTS, new BeanPropertyRowMapper<Product>(Product.class));
		lOG.debug(products);
		logger.info("exection ProductDaoImpl.getAllProduct successfully finished");
		return products;
	}

	@Override
	public void add(Product product) throws DaoException {
		logger.info("exection ProductDaoImpl.add");
		logger.info(product);
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_PRODUCTS_BY_ID, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, product.getId());
				ps.setString(2, product.getName());
				ps.setString(3, product.getDescription());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		product.setId(id);
		logger.info("exection ProductDaoImpl.add successfully finished");
	}

	@Override
	public boolean deletedById(long id) throws DaoException {
		logger.info("exection ProductDaoImpl.deletedById");
		logger.info(id);
		JdbcTemplate template = new JdbcTemplate(datasource);
		logger.info("exection ProductDaoImpl.deletedById successfully finished");
		return	template.update(DELETE_PRODUCTS_BY_ID, id) == 1;
	}

	@Override
	public boolean updateById(Product product) throws DaoException {
		logger.info("exection ProductDaoImpl.updateById");
		logger.info(product);
		JdbcTemplate template = new JdbcTemplate(datasource);
		logger.info("exection ProductDaoImpl.updateById successfully finished");
		return	template.update(UPDATE_PRODUCTS_BY_ID, product.getName(), 
								product.getDescription(), product.getId()) == 1;
	}

	@Override
	public Product getById(long id) throws DaoException {
		logger.info("exection ProductDaoImpl.getById");
		logger.info(id);
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Product> product =  template.query(SELECT_PRODUCTS_BY_ID,
				new BeanPropertyRowMapper<Product>(Product.class), id);
		if (product.size() == 0) {
			logger.info("exection ProductDaoImpl.getById return null, successfully finished");
			return null;
		} else {
			logger.info("exection ProductDaoImpl.getById return product.get(0), successfully finished");
			return product.get(0);
		}
	}

	@Override
	public List<Long> getAllProductIdInGame() throws DaoException {
		logger.info("exection ProductDaoImpl.getAllProductIdInGame");
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Long> products = template.query(SELECT_PROUCTS_BY_PROUCTS_ID, new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int num) throws SQLException {
				return rs.getLong("poduct_Id");
			}
		});
		logger.info("exection ProductDaoImpl.getAllProductIdInGame successfully finished");
		return products;
	}
}
