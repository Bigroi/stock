package com.bigroi.stock.dao;

import com.bigroi.stock.bean.db.TempKey;

public interface GenerateKeyDao {

	boolean checkResetKey(String email, String key);

	TempKey generateKey();

	void deleteGenerateKeysByDate();

	void deleteGenerateKey(String code);
}
