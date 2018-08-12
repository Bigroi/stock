package com.bigroi.stock.docs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.util.exception.StockRuntimeException;

@Repository
public class DealDocument extends Document<Deal>{

	public static final String DEAL_DOC_FILE_NAME = "Deal";
	public static final String DEAL_DOC_FILE_EXTENSION = "doc";
	
	public DealDocument(){
		super(DEAL_DOC_FILE_NAME, DEAL_DOC_FILE_EXTENSION);
	}
	
	public byte[] getDocument(Deal deal) {
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("{date}", new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
			map.put("{seller}", deal.getSellerAddress().getCompany().getName());
			map.put("{seller_person}", deal.getSellerAddress().getCompany().getEmail());
			map.put("{buyer}", deal.getBuyerAddress().getCompany().getName());
			map.put("{buyer_person}", deal.getBuyerAddress().getCompany().getEmail());
			map.put("{volume}", deal.getVolume());
			map.put("{price}", deal.getPrice());
			map.put("{product}", deal.getProduct().getName());
			map.put("{seller_reg_number}", deal.getSellerAddress().getCompany().getRegNumber());
			map.put("{seller_phone}", deal.getSellerAddress().getCompany().getPhone());
			map.put("{seller_address}", deal.getSellerAddress().getCountry() + " " +
										deal.getSellerAddress().getCity() + " " +
										deal.getSellerAddress().getAddress());
			
			map.put("{buyer_reg_number}", deal.getBuyerAddress().getCompany().getRegNumber());
			map.put("{buyer_phone}", deal.getBuyerAddress().getCompany().getPhone());
			map.put("{buyer_address}", deal.getBuyerAddress().getCountry() + " " +
										deal.getBuyerAddress().getCity() + " " +
										deal.getBuyerAddress().getAddress());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			String language = deal.getSellerAddress().getCompany().getLanguage();
			replaceText(map,language).write(baos);
			return baos.toByteArray();
		} catch (IOException e) {
			throw new StockRuntimeException(e);
		}
		
	}
	
}
