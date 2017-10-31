package com.bigroi.stock.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.Product;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.messager.MailManagerException;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.ProductService;
import com.bigroi.stock.service.ServiceException;

public class ProductServiceImpl implements ProductService {

	private ProductDao productDao;
	private LotDao lotDao;
	private TenderDao tenderDao;
	private CompanyDao companyDao;
	
	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

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
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<Product> getAllActiveProducts() throws ServiceException {
		try {
			return productDao.getAllActiveProducts();
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
		return new ArrayList<>();
	}

	@Override
	public Map<String, ?> getTradeOffers(long id) throws ServiceException {
		ModelMap model = new ModelMap();
		try {
			model.addAttribute("product", productDao.getById(id));
			model.addAttribute("listOfLots", lotDao.getActiveByProductId(id));
			model.addAttribute("listOfTenders", tenderDao.getActiveByProductId(id));
			return model;
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
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
			MessagerFactory.getMailManager().sendToAdmin(e);
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
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException();
		}
	}
	
	@Override
	@Transactional
	public void delete(long id, long companyId) throws ServiceException {
		try {
			productDao.setArchived(id);
			lotDao.setStatusByProductId(id, Status.CANCELED);
			tenderDao.setStatusByProductId(id, Status.CANCELED);
			Company company = companyDao.getById(companyId);
			//TODO send correct text
			MessagerFactory.getMailManager().send(company.getEmail(), "deleteComapny", " some text");
		} catch (DaoException | MailManagerException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}

	}
}
