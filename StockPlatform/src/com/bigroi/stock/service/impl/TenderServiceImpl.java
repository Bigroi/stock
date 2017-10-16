package com.bigroi.stock.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.User;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.TenderService;

public class TenderServiceImpl implements TenderService{
	
	private TenderDao tenderDao;
	
	public void setTenderDao(TenderDao tenderDao) {
		this.tenderDao = tenderDao;
	}

	@Override
	@Transactional
	public ModelMap callEditTender(long id, HttpSession session) throws ServiceException {
		ModelMap model = new ModelMap();
		try{
		Tender tender;
		if (id == -1) {
			tender = new Tender();
			User user = (User) session.getAttribute("user");
			tender.setCustomerId(user.getCompanyId());
			tender.setStatus(Status.DRAFT);
			tender.setId(-1);
		} else {
			tender = tenderDao.getById(id);
			model.addAttribute("id", tender.getId());
		}
		model.addAttribute("tender", tender);
		model.put("listOfProducts", DaoFactory.getProductDao().getAllProduct());
		return model;
		}catch(DaoException e){
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<Tender> getMyTenderList(HttpSession session) throws ServiceException {
		try{
		User user = (User) session.getAttribute("user");
		List<Tender> tenders = tenderDao.getByCustomerId(user.getCompanyId());
		return tenders;
		}catch(DaoException e){
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
		return null;
	}

	@Override
	@Transactional
	public void setTenderInGame(long id) throws ServiceException {
		try{
		Tender tender = tenderDao.getById(id);
		if (tender.getStatus() == Status.DRAFT){
			tender.setStatus(Status.IN_GAME);
			tenderDao.updateById(tender);
		}
		}catch(DaoException e){
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
		
	}

	@Override
	@Transactional
	public void setLotCancel(long id) throws ServiceException {
		try{
		Tender tender = DaoFactory.getTenderDao().getById(id);
		if ((tender.getStatus() == Status.DRAFT) || (tender.getStatus() == Status.IN_GAME)){
			tender.setStatus(Status.CANCELED);
			tenderDao.updateById(tender);
		}
		}catch(DaoException e){
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
	}
	

}
