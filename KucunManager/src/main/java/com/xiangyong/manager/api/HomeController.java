package com.xiangyong.manager.api;

import com.xiangyong.manager.biz.interfaces.ColorBizService;
import com.xiangyong.manager.core.exception.DataNotFoundException;
import com.xiangyong.manager.dto.SimpleMemberDto;
import com.xiangyong.manager.entities.ColorEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "首页接口")
@RestController
@RequestMapping("/home/")
public class HomeController {
    private static Logger logger= LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ColorBizService colorBizService;

    @ApiOperation(value = "颜色查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ColorEntity GetColor(@PathVariable(name = "id")int id,@ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        ColorEntity colorEntity= colorBizService.GetColorById(id);
        if(colorEntity == null ) {
            throw new DataNotFoundException(id);
        }

        logger.info("User {} query color with id {}", userInfo.getUserId(), id);

        return colorEntity;
    }
}
