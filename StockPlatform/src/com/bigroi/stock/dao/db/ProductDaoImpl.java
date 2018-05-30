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

import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.ui.ProductForUI;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.ProductDao;

public class ProductDaoImpl implements ProductDao {
	
	private static final String GET_ALL_PRODUCTS = 
			"SELECT ID, NAME, DESCRIPTION, REMOVED, DELIVARY_PRICE, PICTURE "
			+ " FROM PRODUCT ";

	private static final String GET_ALL_ACTIVE_PRODUCTS = 
			"SELECT ID, NAME, DESCRIPTION, REMOVED, DELIVARY_PRICE, PICTURE "
			+ " FROM PRODUCT "
			+ " WHERE REMOVED = 'N'";
	
	private static final String GET_ALL_ACTIVE_PRODUCTS_FOR_UI = 
			" SELECT P.ID, P.NAME, P.DESCRIPTION, P.DELIVARY_PRICE, P.PICTURE, "
			+ " IFNULL(L.SELLVOLUME, 0) SELLVOLUME, "
			+ " IFNULL(L.SELLPRICE, 0) SELLPRICE, "
			+ " IFNULL(T.BUYVOLUME, 0) BUYVOLUME, "
			+ " IFNULL(T.BUYPRICE, 0) BUYPRICE "
			+ " FROM PRODUCT P "
			+ " LEFT JOIN (SELECT PRODUCT_ID, SUM(MAX_VOLUME) SELLVOLUME, "
			+ " 	  SUM(MIN_PRICE)/COUNT(*) SELLPRICE "
			+ "		  FROM LOT "
			+ "		  GROUP BY PRODUCT_ID) L "
			+ " ON L.PRODUCT_ID = P.ID "
			+ " LEFT JOIN (SELECT PRODUCT_ID, SUM(MAX_VOLUME) BUYVOLUME, "
			+ "		  SUM(MAX_PRICE)/COUNT(*) BUYPRICE "
			+ " 	  FROM TENDER "
			+ "		  GROUP BY PRODUCT_ID) T "
			+ " ON T.PRODUCT_ID = P.ID"
			+ " WHERE P.REMOVED = 'N'";
	
	private static final String ADD_PRODUCT = 
			"INSERT INTO PRODUCT (NAME, DESCRIPTION, REMOVED, DELIVARY_PRICE) " 
	        + "VALUES (?, ?, ?, ?)";

	private static final String UPDATE_PRODUCTS_BY_ID = 
			"UPDATE PRODUCT "
			+ " SET NAME = ?, DESCRIPTION = ?, DELIVARY_PRICE = ?, REMOVED = ? "
			+ " WHERE ID = ?";

	private static final String GET_PRODUCT_BY_ID = 
			"SELECT ID, NAME, DESCRIPTION, REMOVED, DELIVARY_PRICE "
			+ " FROM PRODUCT "
			+ " WHERE ID = ? ";
	
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
	public List<ProductForUI> getAllActiveProductsForUI() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_ALL_ACTIVE_PRODUCTS_FOR_UI, 
				new BeanPropertyRowMapper<ProductForUI>(ProductForUI.class));
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
				ps.setString(3, product.getRemoved());
				ps.setDouble(4, product.getDelivaryPrice());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		product.setId(id);
	}

	@Override
	public boolean update(Product product) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return	template.update(UPDATE_PRODUCTS_BY_ID, 
				product.getName(), 
				product.getDescription(), 
				product.getDelivaryPrice(),
				product.getRemoved(),
				product.getId()) == 1;
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

}
