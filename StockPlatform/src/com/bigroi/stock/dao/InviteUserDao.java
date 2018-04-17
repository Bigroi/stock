package com.bigroi.stock.dao;


import com.bigroi.stock.bean.InviteUser;

public interface InviteUserDao {
	
	void add(InviteUser inviteUser) throws DaoException;
	
	InviteUser getInviteUserByCode(String code) throws DaoException;//	InviteUser
	
	boolean deleteInviteUserByCode(String code) throws DaoException;
		
}
