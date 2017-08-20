package com.bigroi.stock.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.ResultBean;

public abstract class ResourseBeanException {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public String handlerException(Exception e){
		return new ResultBean(-1, e.getMessage()).toString();
		
	}
}
