package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bigroi.stock.bean.Product;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.messager.MessagerFactory;
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
	public ModelMap tradeOffers(long id) throws ServiceException {
		ModelMap model = new ModelMap();
		try {
			model.addAttribute("product", productDao.getById(id));
			model.addAttribute("listOfLots", lotDao.getByProductIdInGameOrderMinPrice(id));
			model.addAttribute("listOfTenders", tenderDao.getByProductIdInGameOrderMaxPriceDesc(id));
			return model;
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
		return null;

	}

	@Override
	@Transactional
	public ModelMap callEditProduct(long id) throws ServiceException {
		ModelMap model = new ModelMap();
		try {
			Product product;
			if (id == -1) {
				product = new Product();
				product.setId(-1);
			} else {
				product = productDao.getById(id);
				model.addAttribute("id", product.getId());
			}
			model.addAttribute("product", product);
			return model;
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
		return null;
	}

	@Override
	@Transactional
	public void callSaveProduct(long id, Product product) throws ServiceException {
		try {
			if (id == -1) {
				productDao.add(product);
				id = product.getId();
			} else {
				product.setId(id);
				productDao.updateById(product);
			}
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
	}
}
