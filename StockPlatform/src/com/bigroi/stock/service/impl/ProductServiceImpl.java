package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bigroi.stock.bean.Product;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.ProductService;
import com.bigroi.stock.service.ServiceException;

public class ProductServiceImpl implements ProductService { //TODO: add field LotDao and TenderDao

	private ProductDao productDao;

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Override
	@Transactional
	public List<Product> getAllProduct() throws ServiceException {
		try {
			return productDao.getAllProduct();
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
		return null;
	}

	@Override
	@Transactional
	public void addProduct (Product product) throws ServiceException {
		try {
			productDao.add(product);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}

	}

	@Override
	@Transactional
	public void updateProduct(Product product) throws ServiceException {
		try {
			productDao.updateById(product);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
		
	}

	@Override
	@Transactional
	public Product getById(long id) throws ServiceException {
		try {
			return productDao.getById(id);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
		return null;
	}

	@Override
	@Transactional
	public ModelMap tradeOffers(long id) throws ServiceException {
		ModelMap model = new ModelMap();
		try {
			model.addAttribute("product", DaoFactory.getProductDao().getById(id));
		} catch (DaoException e) {
			// tmpr
			e.printStackTrace();
		}
		try {
			model.addAttribute("listOfLots", DaoFactory.getLotDao().getByProductIdInGameOrderMinPrice(id));
		} catch (DaoException e) {
			// tmpr
			e.printStackTrace();
		}
		try {
			model.addAttribute("listOfTenders", DaoFactory.getTenderDao().getByProductIdInGameOrderMaxPriceDesc(id));
		} catch (DaoException e) {
			// tmpr
			e.printStackTrace();
		}
		return model;
	}

}
