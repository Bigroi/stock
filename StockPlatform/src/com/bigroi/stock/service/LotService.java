package com.bigroi.stock.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

import com.bigroi.stock.bean.Lot;

public interface LotService {
	
	Lot getById (long id) throws ServiceException;
	
	void addLot(Lot lot) throws ServiceException;
	
	boolean updateByIdLot(Lot lot) throws ServiceException;
	
	List<Lot> getBySellerId(long salerId) throws ServiceException;
	
	void setStatusInGame(long id) throws ServiceException;
	
	void setStatusCancel(long id) throws ServiceException;
	
	ModelMap callLotEdit(long id, HttpSession session) throws ServiceException;

}
