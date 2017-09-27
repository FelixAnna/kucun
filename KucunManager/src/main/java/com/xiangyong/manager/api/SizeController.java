package com.xiangyong.manager.api;

import com.xiangyong.manager.biz.interfaces.SizeBizService;
import com.xiangyong.manager.core.exception.BadRequestException;
import com.xiangyong.manager.core.exception.DataNotFoundException;
import com.xiangyong.manager.core.exception.ForbiddenException;
import com.xiangyong.manager.dto.SimpleMemberDto;
import com.xiangyong.manager.entities.SizeEntity;
import com.xiangyong.manager.parameter.SizeParameter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Api(tags = "尺码信息接口")
@RestController
@RequestMapping("size")
public class SizeController extends BaseController {
    @Autowired
    private SizeBizService sizeBizService;

    @ApiOperation(value = "尺码查询")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public SizeEntity GetSize(@PathVariable(name = "id")int id, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        SizeEntity sizeEntity= sizeBizService.GetSizeById(id);

        if(sizeEntity == null ) {
            throw new DataNotFoundException(id);
        }

        if(sizeEntity.getUserId() != userInfo.getUserId()){
            throw new ForbiddenException(id);
        }

        logger.info("User {} queried size with id {}", userInfo.getUserId(), id);

        return sizeEntity;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public SizeEntity AddSize(@RequestBody SizeParameter sizeParameter, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        if(sizeParameter == null){
            throw new BadRequestException("参数错误！");
        }

        SizeEntity sizeEntity = new SizeEntity();
        sizeEntity.setName(sizeParameter.getName());
        sizeEntity.setSize(sizeParameter.getSize());
        sizeEntity.setUserId(userInfo.getUserId());
        sizeEntity.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));
        SizeEntity newEntity = sizeBizService.AddSize(sizeEntity);

        logger.info("User {} created size with id {}", userInfo.getUserId(), newEntity.getSizeId());
        return newEntity;
    }

    @RequestMapping(value = "{id}/update", method =RequestMethod.POST )
    public SizeEntity UpdateSize(@PathVariable(name = "id")int id, @RequestBody SizeParameter sizeParameter, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        if(sizeParameter == null){
            throw new BadRequestException("参数错误！");
        }

        SizeEntity sizeEntity = sizeBizService.GetSizeById(id);
        if(sizeEntity.getUserId() != userInfo.getUserId()){
            throw new ForbiddenException(id);
        }

        sizeEntity.setName(sizeParameter.getName());
        sizeEntity.setSize(sizeParameter.getSize());
        SizeEntity newEntity = sizeBizService.AddSize(sizeEntity);

        logger.info("User {} updated size with id {}", userInfo.getUserId(), newEntity.getSizeId());
        return newEntity;
    }

    @RequestMapping(value = "{id}/remove", method = RequestMethod.DELETE)
    public boolean RemoveSize(@PathVariable(name = "id")int id, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        SizeEntity sizeEntity= sizeBizService.GetSizeById(id);
        if(sizeEntity.getUserId() != userInfo.getUserId()){
            throw new ForbiddenException(id);
        }

        boolean result = sizeBizService.RemoveSize(id);
        logger.info("User {} removed size with id {}", userInfo.getUserId(), sizeEntity.getSizeId());
        return result;
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ArrayList<SizeEntity> GetAllSizes(@ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        ArrayList<SizeEntity> sizeEntities = sizeBizService.GetSizesByUserId(userInfo.getUserId());
        if(sizeEntities == null ) {
            throw new DataNotFoundException();
        }

        return sizeEntities;
    }
}
