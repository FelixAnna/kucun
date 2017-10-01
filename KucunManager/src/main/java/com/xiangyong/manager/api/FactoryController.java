package com.xiangyong.manager.api;

import com.xiangyong.manager.biz.interfaces.FactoryBizService;
import com.xiangyong.manager.core.exception.BadRequestException;
import com.xiangyong.manager.core.exception.DataNotFoundException;
import com.xiangyong.manager.core.exception.ForbiddenException;
import com.xiangyong.manager.dto.SimpleMemberDto;
import com.xiangyong.manager.entities.FactoryEntity;
import com.xiangyong.manager.parameter.FactoryParameter;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Api(tags = "厂家信息接口")
@RestController
@RequestMapping("factory")
public class FactoryController extends BaseController{

    @Autowired
    private FactoryBizService factoryBizService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public FactoryEntity GetFactoryById(@PathVariable(name = "id")int id, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        FactoryEntity factoryEntity = this.factoryBizService.GetFactoryById(id);
        entityCheck(factoryEntity, id, userInfo.getUserId());

        logger.info("User {} queried factory with id {}", userInfo.getUserId(), id);

        return factoryEntity;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public FactoryEntity AddFactory(@RequestBody FactoryParameter factoryParameter, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        if(factoryParameter == null){
            throw new BadRequestException("参数错误！");
        }

        FactoryEntity factoryEntity = new FactoryEntity();
        factoryEntity.setFactoryName(factoryParameter.getFactoryName());
        factoryEntity.setLink(factoryParameter.getLink());
        factoryEntity.setUserId(userInfo.getUserId());
        factoryEntity.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));
        FactoryEntity newEntity = factoryBizService.AddFactory(factoryEntity);

        logger.info("User {} created factory with id {}", userInfo.getUserId(), newEntity.getFactoryId());
        return newEntity;
    }

    @RequestMapping(value = "{id}/update", method =RequestMethod.POST )
    public FactoryEntity UpdateFactory(@PathVariable(name = "id")int id, @RequestBody FactoryParameter factoryParameter, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        if(factoryParameter == null){
            throw new BadRequestException("参数错误！");
        }

        FactoryEntity factoryEntity = factoryBizService.GetFactoryById(id);
        entityCheck(factoryEntity, id, userInfo.getUserId());

        factoryEntity.setFactoryName(factoryParameter.getFactoryName());
        factoryEntity.setLink(factoryParameter.getLink());
        factoryEntity.setUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
        FactoryEntity newEntity = factoryBizService.AddFactory(factoryEntity);

        logger.info("User {} updated factory with id {}", userInfo.getUserId(), newEntity.getFactoryId());
        return newEntity;
    }

    @RequestMapping(value = "{id}/remove", method = RequestMethod.DELETE)
    public boolean RemoveFactory(@PathVariable(name = "id")int id, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        FactoryEntity factoryEntity = factoryBizService.GetFactoryById(id);
        entityCheck(factoryEntity, id, userInfo.getUserId());
        boolean result = factoryBizService.RemoveFactory(id);
        logger.info("User {} removed factory with id {}", userInfo.getUserId(), factoryEntity.getFactoryId());
        return result;
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ArrayList<FactoryEntity> GetAllFactories(@ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        ArrayList<FactoryEntity> factoryEntities = factoryBizService.GetFactoriesByUserId(userInfo.getUserId());
        if(factoryEntities == null ) {
            throw new DataNotFoundException();
        }

        return factoryEntities;
    }

    private void entityCheck(FactoryEntity factoryEntity, int id, int userId){
        if(factoryEntity == null ) {
            throw new DataNotFoundException(id);
        }

        if(factoryEntity.getUserId() != userId){
            throw new ForbiddenException(id);
        }
    }
}
