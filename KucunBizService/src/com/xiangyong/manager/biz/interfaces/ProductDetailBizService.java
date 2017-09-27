package com.xiangyong.manager.biz.interfaces;

import com.xiangyong.manager.entities.ProductDetailEntity;

import java.util.ArrayList;
import java.util.List;

public interface ProductDetailBizService {
    /**
     * 商品详情查询
     * @param id
     * @return
     */
    ProductDetailEntity GetProductDetailById(int id);

    /**
     * 商品详情查询
     * @param ids
     * @return
     */
    ArrayList<ProductDetailEntity> GetProductDetails(Iterable<Integer> ids);

    /**
     * 商品详情查询
     * @param productBasicId
     * @return
     */
    ArrayList<ProductDetailEntity> GetProductDetailsByProductBasicId(Integer productBasicId);

    /**
     * 新增商品详情
     * @param productBasicEntity
     * @return
     */
    ProductDetailEntity AddProductDetail(ProductDetailEntity productBasicEntity);

    /**
     * 返回所有商品详情信息
     * @return
     */
    List<ProductDetailEntity> GetAll();

    /**
     * 删除商品详情
     * @return
     */
    boolean RemoveProductDetail(int productBasicId);
}
