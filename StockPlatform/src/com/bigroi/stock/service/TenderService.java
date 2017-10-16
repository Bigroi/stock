package com.bigroi.stock.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

import com.bigroi.stock.bean.Tender;

public interface TenderService {
	
	ModelMap callEditTender(long id, HttpSession session) throws ServiceException;
	
	List<Tender> getMyTenderList(HttpSession session) throws ServiceException;
	
	void  setTenderInGame(long id) throws ServiceException;
	
	void  setLotCancel(long id) throws ServiceException;

}
