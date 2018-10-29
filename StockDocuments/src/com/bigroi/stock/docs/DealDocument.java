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
			map.put("{product_name}", deal.getProduct().getName());
			map.put("{deal_date}", new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
			map.put("{seller_city}", deal.getSellerAddress().getCity());
			map.put("{seller_company_name}", deal.getSellerAddress().getCompany().getName());
			map.put("{seller_company_reg_number}", deal.getSellerAddress().getCompany().getRegNumber());
			map.put("{seller_address}", deal.getSellerAddress().getAddress());
			map.put("{buyer_company_name}", deal.getBuyerAddress().getCompany().getName());
			map.put("{buyer_company_reg_number}", deal.getBuyerAddress().getCompany().getRegNumber());
			map.put("{buyer_city}", deal.getBuyerAddress().getCity());
			map.put("{buyer_address}", deal.getBuyerAddress().getAddress());
			map.put("{deal_volume}", deal.getVolume());
			map.put("{seller_phone}", deal.getSellerAddress().getCompany().getPhone());
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			String language = deal.getSellerAddress().getCompany().getLanguage();
			replaceText(map,language).write(baos);
			return baos.toByteArray();
		} catch (IOException e) {
			throw new StockRuntimeException(e);
		}
		
	}
	
}
