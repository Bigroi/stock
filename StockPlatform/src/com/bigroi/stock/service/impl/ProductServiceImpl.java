package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.Product;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.service.ProductService;
import com.bigroi.stock.service.ServiceException;

public class ProductServiceImpl implements ProductService {

	private ProductDao productDao;
	private LotDao lotDao;
	private TenderDao tenderDao;
	
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public void setLotDao(LotDao lotDao) {
		this.lotDao = lotDao;
	}

	public void setTenderDao(TenderDao tenderDao) {
		this.tenderDao = tenderDao;
	}
	
	@Override
	public List<Product> getAllProducts() throws ServiceException {
		try {
			return productDao.getAllProducts();
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<Product> getAllActiveProducts() throws ServiceException {
		try {
			return productDao.getAllActiveProducts();
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Product getProductById(long id) throws ServiceException{
		try {
			Product product;
			if (id == -1) {
				product = new Product();
				product.setId(-1);
			} else {
				product = productDao.getById(id);
			}
			return product;
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	@Override
	public void merge(Product product) throws ServiceException{
		try {
			if (product.getId() == -1) {
				productDao.add(product);
			} else {
				productDao.update(product);
			}
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	@Override
	@Transactional
	public void delete(long id) throws ServiceException {
		try {
			Product product = productDao.getById(id);
			product.setArchive(true);
			productDao.update(product);
			lotDao.setStatusByProductId(id, Status.CANCELED);
			tenderDao.setStatusByProductId(id, Status.CANCELED);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}

	}
}
