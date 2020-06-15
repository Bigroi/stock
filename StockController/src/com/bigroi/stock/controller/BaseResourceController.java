package com.bigroi.stock.controller;

import com.bigroi.stock.json.ResultBean;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public abstract class BaseResourceController extends BaseController {

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public String handlerException(Throwable t) {
        var logger = Logger.getLogger(getClass());
        logger.warn("Controller throw exception: ", t);
        return new ResultBean(-2, t.getMessage()).toString();
    }

}
