package com.xiangyong.manager.utils;

import com.xiangyong.manager.core.exception.CustomerError;
import com.xiangyong.manager.core.exception.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public CustomerError NotFoundError(DataNotFoundException e){
        logger.error(HttpStatus.NOT_FOUND.getReasonPhrase(),e);
        if(e.getId()>0) {
            return new CustomerError(HttpStatus.NOT_FOUND.value(), String.format("Data not found:id = %d.", e.getId()));
        } else {
            return new CustomerError(HttpStatus.NOT_FOUND.value(), "Data not found.");
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public CustomerError NotFoundError(Exception e){
        logger.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),e);
        return new CustomerError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
