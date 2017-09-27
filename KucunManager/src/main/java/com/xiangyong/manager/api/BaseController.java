package com.xiangyong.manager.api;

import com.xiangyong.manager.core.exception.ApplicationException;
import com.xiangyong.manager.core.exception.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;

public class BaseController {
    protected static Logger logger= LoggerFactory.getLogger(BaseController.class);

    protected void validate(BindingResult br){
        if (br.hasErrors()) {
            throw new ApplicationException(ResponseCode.INVALID_PARAM, br.getFieldErrors().get(0).getDefaultMessage());
        }
    }
}
