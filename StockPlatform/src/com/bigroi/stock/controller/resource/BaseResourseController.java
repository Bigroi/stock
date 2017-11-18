package com.bigroi.stock.controller.resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.json.ResultBean;

public abstract class BaseResourseController {

	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public String handlerException(Throwable t){
		Logger logger = Logger.getLogger(getClass());
		logger.warn("Controller throw exception: ", t);
		return new ResultBean(-2, t.getMessage()).toString();
		
	}
}
