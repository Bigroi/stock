package com.bigroi.stock.controller.resource;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.Bid;
import com.bigroi.stock.controller.rendering.BaseRenderingController;
import com.bigroi.stock.json.Button;
import com.bigroi.stock.json.ResultBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public abstract class BaseResourseController extends BaseRenderingController{

	protected Gson gson;

	public BaseResourseController() {
		gson = new GsonBuilder()
				.setDateFormat(Bid.FORMATTER.toPattern())
				.registerTypeAdapter(Button.class, new ButtonTypeAdapter())
				.create();
	}
	
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public String handlerException(Throwable t){
		Logger logger = Logger.getLogger(getClass());
		logger.warn("Controller throw exception: ", t);
		return new ResultBean(-2, t.getMessage()).toString();
	}
	
	public static class ButtonTypeAdapter extends TypeAdapter<Button>{

		@Override
		public void write(JsonWriter out, Button value) throws IOException {
			 out.beginObject();
			 out.name("name").value(value.name());
			 out.name("url").value(value.url());
			 out.endObject();
		}

		@Override
		public Button read(JsonReader in) throws IOException {
			return null;
		}
		
	}
}
