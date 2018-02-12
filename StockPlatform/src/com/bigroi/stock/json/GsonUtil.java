package com.bigroi.stock.json;

import com.bigroi.stock.bean.Bid;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {

	private static final Gson gson = new GsonBuilder()
								.setDateFormat(Bid.FORMATTER.toPattern())
								.create();
	
	public static Gson getGson() {
		return gson;
	}

}
