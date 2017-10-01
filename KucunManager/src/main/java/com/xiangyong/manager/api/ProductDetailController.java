package com.xiangyong.manager.api;

import com.xiangyong.manager.biz.interfaces.ProductBasicBizService;
import com.xiangyong.manager.biz.interfaces.ProductDetailBizService;
import com.xiangyong.manager.core.exception.BadRequestException;
import com.xiangyong.manager.core.exception.DataNotFoundException;
import com.xiangyong.manager.core.exception.ForbiddenException;
import com.xiangyong.manager.dto.SimpleMemberDto;
import com.xiangyong.manager.entities.ProductBasicEntity;
import com.xiangyong.manager.entities.ProductDetailEntity;
import com.xiangyong.manager.parameter.ProductDetailParameter;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Api(tags = "商品详情信息接口")
@RestController
@RequestMapping("product/detail")
public class ProductDetailController extends BaseController {

    @Autowired
    private ProductDetailBizService productDetailBizService;

    @Autowired
    private ProductBasicBizService productBasicBizService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ProductDetailEntity GetProductDetailById(@PathVariable(name = "id")int id, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        ProductDetailEntity productDetailEntity = this.productDetailBizService.GetProductDetailById(id);
        entityCheck(productDetailEntity, id, userInfo.getUserId());

        logger.info("User {} queried productDetail with id {}", userInfo.getUserId(), id);

        return productDetailEntity;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ProductDetailEntity AddProductDetail(@RequestBody ProductDetailParameter productDetailParameter, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        if(productDetailParameter == null){
            throw new BadRequestException("参数错误！");
        }

        ProductDetailEntity productDetailEntity = new ProductDetailEntity();
        productDetailEntity.setProductBasicId(productDetailParameter.getProductBasicId());
        productDetailEntity.setColorId(productDetailParameter.getColorId());
        productDetailEntity.setSizeId(productDetailParameter.getSizeId());
        productDetailEntity.setTotal(productDetailParameter.getTotal());
        productDetailEntity.setSold(productDetailParameter.getSold());
        productDetailEntity.setPrice(productDetailParameter.getPrice());
        productDetailEntity.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));
        ProductDetailEntity newEntity = productDetailBizService.AddProductDetail(productDetailEntity);

        logger.info("User {} created productDetail with id {}", userInfo.getUserId(), newEntity.getProductDetailId());
        return newEntity;
    }

    @RequestMapping(value = "{id}/update", method =RequestMethod.POST )
    public ProductDetailEntity UpdateProductDetail(@PathVariable(name = "id")int id, @RequestBody ProductDetailParameter productDetailParameter, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        if(productDetailParameter == null){
            throw new BadRequestException("参数错误！");
        }

        ProductDetailEntity productDetailEntity = productDetailBizService.GetProductDetailById(id);
        entityCheck(productDetailEntity, id, userInfo.getUserId());

        productDetailEntity.setColorId(productDetailParameter.getColorId());
        productDetailEntity.setSizeId(productDetailParameter.getSizeId());
        productDetailEntity.setTotal(productDetailParameter.getTotal());
        productDetailEntity.setSold(productDetailParameter.getSold());
        productDetailEntity.setPrice(productDetailParameter.getPrice());
        productDetailEntity.setUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
        ProductDetailEntity newEntity = productDetailBizService.AddProductDetail(productDetailEntity);

        logger.info("User {} updated productDetail with id {}", userInfo.getUserId(), newEntity.getProductDetailId());
        return newEntity;
    }

    @RequestMapping(value = "{id}/remove", method = RequestMethod.DELETE)
    public boolean RemoveProductDetail(@PathVariable(name = "id")int id, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        ProductDetailEntity productDetailEntity = productDetailBizService.GetProductDetailById(id);
        entityCheck(productDetailEntity, id, userInfo.getUserId());

        boolean result = productDetailBizService.RemoveProductDetail(id);
        logger.info("User {} removed productDetail with id {}", userInfo.getUserId(), productDetailEntity.getProductDetailId());
        return result;
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ArrayList<ProductDetailEntity> GetAllProductDetails(@ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        ArrayList<ProductBasicEntity> productBasicEntities = productBasicBizService.GetProductBasicsByUserId(userInfo.getUserId());
        ArrayList<ProductDetailEntity> productDetailEntities = new ArrayList<>();
        productBasicEntities.forEach(x->{
            productDetailEntities.addAll(productDetailBizService.GetProductDetailsByProductBasicId(x.getProductBasicId()));
        });

        if(productDetailEntities == null ) {
            throw new DataNotFoundException();
        }

        return productDetailEntities;
    }

    private void entityCheck(ProductDetailEntity productDetailEntity, int id, int userId){
        if(productDetailEntity == null ) {
            throw new DataNotFoundException(id);
        }

        ProductBasicEntity productBasicEntity = this.productBasicBizService.GetProductBasicById(productDetailEntity.getProductBasicId());
        if(productBasicEntity.getUserId() != userId){
            throw new ForbiddenException(id);
        }
    }
}
