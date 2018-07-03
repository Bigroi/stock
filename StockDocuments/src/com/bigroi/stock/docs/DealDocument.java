package com.bigroi.stock.docs;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.Deal;

@Repository
public class DealDocument extends Document<Deal>{

	public DealDocument(String fileName) throws DocumentException{
		super(fileName);
	}
	
	public byte[] getDocument(Deal deal) throws DocumentException{
		Map<String, String> map = new HashMap<>();
		return replaceText(map).getDataStream();
	}
}
