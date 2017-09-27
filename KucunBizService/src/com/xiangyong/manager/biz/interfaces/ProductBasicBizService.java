package com.xiangyong.manager.biz.interfaces;

import com.xiangyong.manager.entities.ProductBasicEntity;

import java.util.ArrayList;
import java.util.List;

public interface ProductBasicBizService {

    /**
     * 基础商品查询
     * @param id
     * @return
     */
    ProductBasicEntity GetProductBasicById(int id);

    /**
     * 基础商品查询
     * @param ids
     * @return
     */
    ArrayList<ProductBasicEntity> GetProductBasics(Iterable<Integer> ids);

    /**
     * 基础商品查询
     * @param userId
     * @return
     */
    ArrayList<ProductBasicEntity> GetProductBasicsByUserId(Integer userId);

    /**
     * 新增基础商品
     * @param productBasicEntity
     * @return
     */
    ProductBasicEntity AddProductBasic(ProductBasicEntity productBasicEntity);

    /**
     * 返回所有基础商品信息
     * @return
     */
    List<ProductBasicEntity> GetAll();

    /**
     * 删除基础商品
     * @return
     */
    boolean RemoveProductBasic(int productBasicId);
}
