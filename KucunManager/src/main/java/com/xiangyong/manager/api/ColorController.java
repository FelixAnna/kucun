package com.xiangyong.manager.api;

import com.xiangyong.manager.biz.interfaces.ColorBizService;
import com.xiangyong.manager.core.exception.BadRequestException;
import com.xiangyong.manager.core.exception.DataNotFoundException;
import com.xiangyong.manager.core.exception.ForbiddenException;
import com.xiangyong.manager.dto.SimpleMemberDto;
import com.xiangyong.manager.entities.ColorEntity;
import com.xiangyong.manager.parameter.ColorParameter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Api(tags ="颜色信息接口")
@RestController
@RequestMapping("color")
public class ColorController extends BaseController {
    @Autowired
    private ColorBizService colorBizService;

    @ApiOperation(value = "颜色查询")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ColorEntity GetColor(@PathVariable(name = "id")int id, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        ColorEntity colorEntity= colorBizService.GetColorById(id);

        if(colorEntity == null ) {
            throw new DataNotFoundException(id);
        }

        if(colorEntity.getUserId() != userInfo.getUserId()){
            throw new ForbiddenException(id);
        }

        logger.info("User {} queried color with id {}", userInfo.getUserId(), id);

        return colorEntity;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ColorEntity AddColor(@RequestBody ColorParameter colorParameter, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        if(colorParameter == null){
            throw new BadRequestException("参数错误！");
        }

        ColorEntity colorEntity = new ColorEntity();
        colorEntity.setName(colorParameter.getName());
        colorEntity.setRgb(colorParameter.getRgb());
        colorEntity.setUserId(userInfo.getUserId());
        colorEntity.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));
        ColorEntity newEntity = colorBizService.AddColor(colorEntity);

        logger.info("User {} created color with id {}", userInfo.getUserId(), newEntity.getColorId());
        return newEntity;
    }

    @RequestMapping(value = "{id}/update", method =RequestMethod.POST )
    public ColorEntity UpdateColor(@PathVariable(name = "id")int id, @RequestBody ColorParameter colorParameter, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        if(colorParameter == null){
            throw new BadRequestException("参数错误！");
        }

        ColorEntity colorEntity = colorBizService.GetColorById(id);
        if(colorEntity.getUserId() != userInfo.getUserId()){
            throw new ForbiddenException(id);
        }

        colorEntity.setName(colorParameter.getName());
        colorEntity.setRgb(colorParameter.getRgb());
        ColorEntity newEntity = colorBizService.AddColor(colorEntity);

        logger.info("User {} updated color with id {}", userInfo.getUserId(), newEntity.getColorId());
        return newEntity;
    }

    @RequestMapping(value = "{id}/remove", method = RequestMethod.DELETE)
    public boolean RemoveColor(@PathVariable(name = "id")int id, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        ColorEntity colorEntity = colorBizService.GetColorById(id);
        if(colorEntity.getUserId() != userInfo.getUserId()){
            throw new ForbiddenException(id);
        }
        boolean result = colorBizService.RemoveColor(id);
        logger.info("User {} removed color with id {}", userInfo.getUserId(), colorEntity.getColorId());
        return result;
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ArrayList<ColorEntity> GetAllColors(@ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        ArrayList<ColorEntity> colorEntities = colorBizService.GetColorsByUserId(userInfo.getUserId());
        if(colorEntities == null ) {
            throw new DataNotFoundException();
        }

        return colorEntities;
    }
}
