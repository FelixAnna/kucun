package com.xiangyong.manager.api;

import com.xiangyong.manager.SellStatus;
import com.xiangyong.manager.biz.interfaces.ProductBasicBizService;
import com.xiangyong.manager.biz.interfaces.ProductDetailBizService;
import com.xiangyong.manager.biz.interfaces.SellRecordBizService;
import com.xiangyong.manager.core.exception.BadRequestException;
import com.xiangyong.manager.core.exception.DataNotFoundException;
import com.xiangyong.manager.core.exception.ForbiddenException;
import com.xiangyong.manager.dto.SimpleMemberDto;
import com.xiangyong.manager.entities.ProductBasicEntity;
import com.xiangyong.manager.entities.ProductDetailEntity;
import com.xiangyong.manager.entities.SellRecordEntity;
import com.xiangyong.manager.parameter.SellRecordParameter;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Api(tags = "商品销售记录")
@RestController
@RequestMapping("product/sell/record")
public class SellRecordController extends BaseController {
    @Autowired
    private SellRecordBizService sellRecordBizService;
    @Autowired
    private ProductDetailBizService productDetailBizService;
    @Autowired
    private ProductBasicBizService productBasicBizService;

    @RequestMapping(value = "id", method = RequestMethod.GET)
    public SellRecordEntity GetSellRecordById(@PathVariable(name = "id")int id, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        SellRecordEntity sellRecordEntity = this.sellRecordBizService.GetSellRecordById(id);
        entityCheck(sellRecordEntity, id, userInfo.getUserId());

        logger.info("User {} queried sell record with id {}", userInfo.getUserId(), id);

        return sellRecordEntity;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public SellRecordEntity AddSellRecord(@RequestBody SellRecordParameter sellRecordParameter, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        if(sellRecordParameter == null){
            throw new BadRequestException("参数错误！");
        }

        SellRecordEntity sellRecordEntity = new SellRecordEntity();
        sellRecordEntity.setProductDetailId(sellRecordParameter.getProductDetailId());
        sellRecordEntity.setTotalNumber(sellRecordParameter.getTotalNumber());
        sellRecordEntity.setTotalPrice(sellRecordParameter.getTotalPrice());
        sellRecordEntity.setStatus(SellStatus.Ready.getValue());
        sellRecordEntity.setRemark(sellRecordParameter.getRemark());
        sellRecordEntity.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));
        SellRecordEntity newEntity = sellRecordBizService.AddSellRecord(sellRecordEntity);

        logger.info("User {} created sell record with id {}", userInfo.getUserId(), newEntity.getSellRecordId());
        return newEntity;
    }

    @RequestMapping(value = "{id}/update", method =RequestMethod.POST )
    public SellRecordEntity UpdateSellRecord(@PathVariable(name = "id")int id, @RequestBody SellRecordParameter sellRecordParameter, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        if(sellRecordParameter == null){
            throw new BadRequestException("参数错误！");
        }

        SellRecordEntity sellRecordEntity = sellRecordBizService.GetSellRecordById(id);
        entityCheck(sellRecordEntity, id, userInfo.getUserId());

        sellRecordEntity.setProductDetailId(sellRecordParameter.getProductDetailId());
        sellRecordEntity.setTotalNumber(sellRecordParameter.getTotalNumber());
        sellRecordEntity.setTotalPrice(sellRecordParameter.getTotalPrice());
        sellRecordEntity.setStatus(SellStatus.Ready.getValue());
        sellRecordEntity.setRemark(sellRecordParameter.getRemark());
        sellRecordEntity.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));
        SellRecordEntity newEntity = sellRecordBizService.AddSellRecord(sellRecordEntity);

        logger.info("User {} updated sell record with id {}", userInfo.getUserId(), newEntity.getSellRecordId());
        return newEntity;
    }

    @RequestMapping(value = "{id}/remove", method = RequestMethod.DELETE)
    public boolean RemoveSellRecord(@PathVariable(name = "id")int id, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        SellRecordEntity factoryEntity = sellRecordBizService.GetSellRecordById(id);
        entityCheck(factoryEntity, id, userInfo.getUserId());
        boolean result = sellRecordBizService.RemoveSellRecord(id);
        logger.info("User {} removed sell record with id {}", userInfo.getUserId(), factoryEntity.getSellRecordId());
        return result;
    }

    @RequestMapping(value = "{id}/cancel", method = RequestMethod.DELETE)
    public boolean CancelSellRecord(@PathVariable(name = "id")int id, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        SellRecordEntity factoryEntity = sellRecordBizService.GetSellRecordById(id);
        entityCheck(factoryEntity, id, userInfo.getUserId());
        boolean result = sellRecordBizService.CancelSellRecord(id);
        logger.info("User {} cancelled sell record with id {}", userInfo.getUserId(), factoryEntity.getSellRecordId());
        return result;
    }

    @RequestMapping(value = "{id}/deal", method = RequestMethod.DELETE)
    public boolean DealSellRecord(@PathVariable(name = "id")int id, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        SellRecordEntity factoryEntity = sellRecordBizService.GetSellRecordById(id);
        entityCheck(factoryEntity, id, userInfo.getUserId());
        boolean result = sellRecordBizService.FinishSellRecord(id);
        logger.info("User {} finished sell record with id {}", userInfo.getUserId(), factoryEntity.getSellRecordId());
        return result;
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ArrayList<SellRecordEntity> GetAllSellRecords(@ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        ArrayList<SellRecordEntity> sellRecordEntities = new ArrayList<>();
        ArrayList<ProductBasicEntity> productBasicEntities = productBasicBizService.GetProductBasicsByUserId(userInfo.getUserId());
        ArrayList<ProductDetailEntity> productDetailEntities = new ArrayList<>();
        productBasicEntities.forEach(x->{
            productDetailEntities.addAll(productDetailBizService.GetProductDetailsByProductBasicId(x.getProductBasicId()));
        });
        sellRecordEntities.forEach(x->{
            sellRecordEntities.addAll(sellRecordBizService.GetSellRecordsByProductDetailId(x.getProductDetailId()));
        });

        if(sellRecordEntities == null ) {
            throw new DataNotFoundException();
        }

        return sellRecordEntities;
    }

    @RequestMapping(value = "{productDetailId}/list", method = RequestMethod.GET)
    public ArrayList<SellRecordEntity> GetAllSellRecords(@PathVariable(name = "productDetailId")int productDetailId, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        ArrayList<SellRecordEntity> sellRecordEntities = sellRecordBizService.GetSellRecordsByProductDetailId(productDetailId);
        if(sellRecordEntities == null ) {
            throw new DataNotFoundException();
        }

        return sellRecordEntities;
    }

    private void entityCheck(SellRecordEntity sellRecordEntity, int id, int userId){
        if(sellRecordEntity == null ) {
            throw new DataNotFoundException(id);
        }

        ProductDetailEntity productDetailEntity = this.productDetailBizService.GetProductDetailById(sellRecordEntity.getProductDetailId());
        ProductBasicEntity productBasicEntity = this.productBasicBizService.GetProductBasicById(productDetailEntity.getProductBasicId());
        if(productBasicEntity.getUserId() != userId){
            throw new ForbiddenException(id);
        }
    }

}
