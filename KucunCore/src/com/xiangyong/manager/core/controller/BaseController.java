package com.xiangyong.manager.core.controller;

import com.xiangyong.manager.core.exception.ApplicationException;
import com.xiangyong.manager.core.exception.ResponseCode;
import org.springframework.validation.BindingResult;

public class BaseController {

    protected void validate(BindingResult br){
        if (br.hasErrors()) {
            throw new ApplicationException(ResponseCode.INVALID_PARAM, br.getFieldErrors().get(0).getDefaultMessage());
        }
    }
}
