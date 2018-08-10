package com.bigroi.stock.dao;


import com.bigroi.stock.bean.db.StockUser;

public interface UserDao {
	
	void add(StockUser user);
	
	boolean update(StockUser user);
	
	StockUser getByUsernameWithRoles(String username);

	boolean updatePassword(StockUser user);

	boolean updateKeyById(StockUser user);
}
