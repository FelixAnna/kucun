package com.xiangyong.manager.api;

import com.xiangyong.manager.biz.interfaces.ColorBizService;
import com.xiangyong.manager.biz.interfaces.ProductBasicBizService;
import com.xiangyong.manager.biz.interfaces.ProductDetailBizService;
import com.xiangyong.manager.biz.interfaces.SizeBizService;
import com.xiangyong.manager.core.exception.BadRequestException;
import com.xiangyong.manager.core.exception.DataNotFoundException;
import com.xiangyong.manager.core.exception.ForbiddenException;
import com.xiangyong.manager.core.util.MapperUtils;
import com.xiangyong.manager.dto.ProductBasicAndDetailsDto;
import com.xiangyong.manager.dto.ProductDetailDTO;
import com.xiangyong.manager.dto.SimpleMemberDto;
import com.xiangyong.manager.entities.ColorEntity;
import com.xiangyong.manager.entities.ProductBasicEntity;
import com.xiangyong.manager.entities.ProductDetailEntity;
import com.xiangyong.manager.entities.SizeEntity;
import com.xiangyong.manager.parameter.ProductBasicParameter;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "基础商品信息")
@RestController
@RequestMapping("product/basic")
public class ProductBasicController extends BaseController {

    @Autowired
    private ProductBasicBizService productBasicBizService;

    @Autowired
    private ProductDetailBizService productDetailBizService;

    @Autowired
    private ColorBizService colorBizService;

    @Autowired
    private SizeBizService sizeBizService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ProductBasicEntity GetProductBasicById(@PathVariable(name = "id")int id, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        ProductBasicEntity productBasicEntity = this.productBasicBizService.GetProductBasicById(id);

        entityCheck(productBasicEntity, id, userInfo.getUserId());

        logger.info("User {} queried productBasic with id {}", userInfo.getUserId(), id);

        return productBasicEntity;
    }

    @RequestMapping(value = "{id}/details", method = RequestMethod.GET)
    public ProductBasicAndDetailsDto GetProductBasicAndDetailsById(@PathVariable(name = "id")int id, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        ProductBasicEntity productBasicEntity = this.productBasicBizService.GetProductBasicById(id);
        entityCheck(productBasicEntity, id, userInfo.getUserId());

        ArrayList<ProductDetailEntity> productDetailEntities = this.productDetailBizService.GetProductDetailsByProductBasicId(id);
        List<Integer> colorIds = productDetailEntities.stream().map(x->x.getColorId()).distinct().collect(Collectors.toList());
        ArrayList<ColorEntity> colorEntities = colorBizService.GetColors(colorIds);
        List<Integer> sizeIds = productDetailEntities.stream().map(x->x.getSizeId()).distinct().collect(Collectors.toList());
        ArrayList<SizeEntity> sizeEntities = sizeBizService.GetSizes(sizeIds);

        ArrayList<ProductDetailDTO> productDetailDTOS = new ArrayList<>();
        for(ProductDetailEntity detailEntity : productDetailEntities){
            ProductDetailDTO productDetailDTO = MapperUtils.map(detailEntity, ProductDetailDTO.class);

            ColorEntity colorEntity = colorEntities.stream().filter(x->x.getColorId() == detailEntity.getColorId()).findFirst().get();
            productDetailDTO.setColorEntity(colorEntity);
            SizeEntity sizeEntity = sizeEntities.stream().filter(x->x.getSizeId() == detailEntity.getSizeId()).findFirst().get();
            productDetailDTO.setSizeEntity(sizeEntity);

            productDetailDTOS.add(productDetailDTO);
        }

        ProductBasicAndDetailsDto dto = MapperUtils.map(productBasicEntity, ProductBasicAndDetailsDto.class);
        dto.setProductDetailDTOS(productDetailDTOS);
        dto.setUserName(userInfo.getUserName());

        logger.info("User {} queried productBasic and details with id {}", userInfo.getUserId(), id);

        return dto;
    }



    @RequestMapping(value = "{productBasicId}/details/list", method = RequestMethod.GET)
    public ArrayList<ProductDetailEntity> GetProductDetailByProductBasicId(@PathVariable(name = "productBasicId")int id, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        ProductBasicEntity productBasicEntity = this.productBasicBizService.GetProductBasicById(id);
        entityCheck(productBasicEntity, id, userInfo.getUserId());
        ArrayList<ProductDetailEntity> productDetailEntities = this.productDetailBizService.GetProductDetailsByProductBasicId(id);

        logger.info("User {} queried productDetail with productBasicId {}", userInfo.getUserId(), id);

        return productDetailEntities;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ProductBasicEntity AddProductBasic(@RequestBody ProductBasicParameter productBasicParameter, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        if(productBasicParameter == null){
            throw new BadRequestException("参数错误！");
        }

        ProductBasicEntity productBasicEntity = new ProductBasicEntity();
        productBasicEntity.setProductName(productBasicParameter.getProductName());
        productBasicEntity.setLink(productBasicParameter.getLink());
        productBasicEntity.setUserId(userInfo.getUserId());
        productBasicEntity.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));
        ProductBasicEntity newEntity = productBasicBizService.AddProductBasic(productBasicEntity);

        logger.info("User {} created productBasic with id {}", userInfo.getUserId(), newEntity.getProductBasicId());
        return newEntity;
    }

    @RequestMapping(value = "{id}/update", method =RequestMethod.POST )
    public ProductBasicEntity UpdateProductBasic(@PathVariable(name = "id")int id, @RequestBody ProductBasicParameter productBasicParameter, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        if(productBasicParameter == null){
            throw new BadRequestException("参数错误！");
        }

        ProductBasicEntity productBasicEntity = productBasicBizService.GetProductBasicById(id);
        entityCheck(productBasicEntity, id, userInfo.getUserId());

        productBasicEntity.setProductName(productBasicParameter.getProductName());
        productBasicEntity.setLink(productBasicParameter.getLink());
        productBasicEntity.setUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
        ProductBasicEntity newEntity = productBasicBizService.AddProductBasic(productBasicEntity);

        logger.info("User {} updated productBasic with id {}", userInfo.getUserId(), newEntity.getProductBasicId());
        return newEntity;
    }

    @RequestMapping(value = "{id}/remove", method = RequestMethod.DELETE)
    public boolean RemoveProductBasic(@PathVariable(name = "id")int id, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        ProductBasicEntity productBasicEntity = productBasicBizService.GetProductBasicById(id);
        entityCheck(productBasicEntity, id, userInfo.getUserId());
        boolean result = productBasicBizService.RemoveProductBasic(id);
        logger.info("User {} removed productBasic with id {}", userInfo.getUserId(), productBasicEntity.getProductBasicId());
        return result;
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ArrayList<ProductBasicEntity> GetAllProductBasics(@ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        ArrayList<ProductBasicEntity> productBasicEntities = productBasicBizService.GetProductBasicsByUserId(userInfo.getUserId());
        if(productBasicEntities == null ) {
            throw new DataNotFoundException();
        }

        return productBasicEntities;
    }

    private void entityCheck(ProductBasicEntity productBasicEntity, int id, int userId){
        if(productBasicEntity == null ) {
            throw new DataNotFoundException(id);
        }

        if(productBasicEntity.getUserId() != userId){
            throw new ForbiddenException(id);
        }
    }
}
