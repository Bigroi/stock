package com.bigroi.transport.controller.resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.transport.controller.rendering.BaseRenderingController;
import com.bigroi.transport.json.ResultBean;

public abstract class BaseResourseController extends BaseRenderingController{

	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public String handlerException(Throwable t){
		Logger logger = Logger.getLogger(getClass());
		logger.warn("Controller throw exception: ", t);
		return new ResultBean(-2, t.getMessage()).toString();
	}
	
}
