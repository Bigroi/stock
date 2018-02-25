package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.InviteUser;

public interface InviteUserDao {
	
	void add(InviteUser inviteUser) throws DaoException;
	
	List<InviteUser> getAllInviteUser() throws DaoException;
		
	

}
