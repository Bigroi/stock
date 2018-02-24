package com.bigroi.stock.json;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateTimeAdapter implements JsonSerializer<Date>{

	SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

	@Override
	public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
		JsonElement object = new JsonPrimitive(format.format(src));
		return object;
	}
	
	

}
