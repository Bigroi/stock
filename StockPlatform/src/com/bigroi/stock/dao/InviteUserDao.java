package com.bigroi.stock.dao;


import java.util.List;

import com.bigroi.stock.bean.InviteUser;

public interface InviteUserDao {
	
	void add(InviteUser inviteUser) throws DaoException;
	
	InviteUser getInviteUserByCode(String code) throws DaoException;
	
	boolean deleteInviteUserByCode(String code) throws DaoException;
	
	List<InviteUser> getAllInviteUserByDate() throws DaoException;
	
	boolean deleteInviteUsersByDate() throws DaoException;
		

}
