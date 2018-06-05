package com.bigroi.transport.json;

import java.text.SimpleDateFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {

static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
	
	private static final Gson gson = new GsonBuilder()
								.setDateFormat(FORMATTER.toPattern())
								.create();
	
	public static Gson getGson() {
		return gson;
	}

}
