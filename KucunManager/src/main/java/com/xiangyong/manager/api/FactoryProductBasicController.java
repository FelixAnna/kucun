package com.xiangyong.manager.api;

import com.xiangyong.manager.biz.interfaces.FactoryBizService;
import com.xiangyong.manager.biz.interfaces.FactoryProductBasicBizService;
import com.xiangyong.manager.core.exception.BadRequestException;
import com.xiangyong.manager.core.exception.DataNotFoundException;
import com.xiangyong.manager.core.exception.ForbiddenException;
import com.xiangyong.manager.dto.SimpleMemberDto;
import com.xiangyong.manager.entities.FactoryEntity;
import com.xiangyong.manager.entities.FactoryProductBasicEntity;
import com.xiangyong.manager.parameter.FactoryProductBasicParameter;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Api(tags = "供应商商品报价接口")
@RestController
@RequestMapping("factory/product/basic")
public class FactoryProductBasicController extends BaseController {
    @Autowired
    private FactoryProductBasicBizService factoryProductBasicBizService;

    @Autowired
    private FactoryBizService factoryBizService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public FactoryProductBasicEntity GetFactoryProductBasicById(@PathVariable(name = "id")int id, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        FactoryProductBasicEntity factoryProductBasicEntity = this.factoryProductBasicBizService.GetFactoryProductBasicById(id);
        entityCheck(factoryProductBasicEntity, id , userInfo.getUserId());

        logger.info("User {} queried factory product basic with id {}", userInfo.getUserId(), id);

        return factoryProductBasicEntity;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public FactoryProductBasicEntity AddFactoryProductBasic(@RequestBody FactoryProductBasicParameter factoryProductBasicParameter, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        if(factoryProductBasicParameter == null){
            throw new BadRequestException("参数错误！");
        }

        FactoryProductBasicEntity factoryProductBasicEntity = new FactoryProductBasicEntity();
        factoryProductBasicEntity.setFactoryId(factoryProductBasicParameter.getFactoryId());
        factoryProductBasicEntity.setProductBasicId(factoryProductBasicParameter.getProductBasicId());
        factoryProductBasicEntity.setLink(factoryProductBasicParameter.getLink());
        factoryProductBasicEntity.setMarketPrice(factoryProductBasicParameter.getMarketPrice());
        factoryProductBasicEntity.setSinglePrice(factoryProductBasicParameter.getSinglePrice());
        factoryProductBasicEntity.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));
        FactoryProductBasicEntity newEntity = factoryProductBasicBizService.AddFactoryProductBasic(factoryProductBasicEntity);

        logger.info("User {} created factory product basic with id {}", userInfo.getUserId(), newEntity.getFactoryId());
        return newEntity;
    }

    @RequestMapping(value = "{id}/update", method =RequestMethod.POST )
    public FactoryProductBasicEntity UpdateFactoryProductBasic(@PathVariable(name = "id")int id, @RequestBody FactoryProductBasicParameter factoryProductBasicParameter, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        if(factoryProductBasicParameter == null){
            throw new BadRequestException("参数错误！");
        }

        FactoryProductBasicEntity factoryProductBasicEntity = factoryProductBasicBizService.GetFactoryProductBasicById(id);
        entityCheck(factoryProductBasicEntity, id, userInfo.getUserId());

        factoryProductBasicEntity.setFactoryId(factoryProductBasicParameter.getFactoryId());
        factoryProductBasicEntity.setProductBasicId(factoryProductBasicParameter.getProductBasicId());
        factoryProductBasicEntity.setLink(factoryProductBasicParameter.getLink());
        factoryProductBasicEntity.setMarketPrice(factoryProductBasicParameter.getMarketPrice());
        factoryProductBasicEntity.setSinglePrice(factoryProductBasicParameter.getSinglePrice());
        factoryProductBasicEntity.setUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
        FactoryProductBasicEntity newEntity = factoryProductBasicBizService.AddFactoryProductBasic(factoryProductBasicEntity);

        logger.info("User {} updated factory product basic with id {}", userInfo.getUserId(), newEntity.getFactoryId());
        return newEntity;
    }

    @RequestMapping(value = "{id}/remove", method = RequestMethod.DELETE)
    public boolean RemoveFactoryProductBasic(@PathVariable(name = "id")int id, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        FactoryProductBasicEntity factoryProductBasicEntity = factoryProductBasicBizService.GetFactoryProductBasicById(id);
        entityCheck(factoryProductBasicEntity, id, userInfo.getUserId());

        boolean result = factoryProductBasicBizService.RemoveFactoryProductBasic(id);
        logger.info("User {} removed factory product basic with id {}", userInfo.getUserId(), factoryProductBasicEntity.getFactoryId());
        return result;
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ArrayList<FactoryProductBasicEntity> GetAllFactoryProductBasics(@ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        ArrayList<FactoryEntity> factoryEntities = factoryBizService.GetFactoriesByUserId(userInfo.getUserId());
        ArrayList<FactoryProductBasicEntity> factoryProductBasicEntities = new ArrayList<>();
        factoryEntities.forEach(x->{
            factoryProductBasicEntities.addAll(factoryProductBasicBizService.GetFactoryProductBasicsByFactoryId(x.getFactoryId()));
        });
        if(factoryProductBasicEntities == null ) {
            throw new DataNotFoundException();
        }

        return factoryProductBasicEntities;
    }

    private void entityCheck(FactoryProductBasicEntity factoryProductBasicEntity, int id, int userId){
        if(factoryProductBasicEntity == null ) {
            throw new DataNotFoundException(id);
        }

        FactoryEntity factoryEntity = this.factoryBizService.GetFactoryById(factoryProductBasicEntity.getFactoryId());
        if(factoryEntity.getUserId() != userId){
            throw new ForbiddenException(id);
        }
    }
}
