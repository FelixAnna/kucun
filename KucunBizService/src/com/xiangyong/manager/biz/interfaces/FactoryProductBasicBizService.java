package com.xiangyong.manager.biz.interfaces;

import com.xiangyong.manager.entities.FactoryProductBasicEntity;

import java.util.ArrayList;
import java.util.List;

public interface FactoryProductBasicBizService {
    /**
     * 供应商商品查询
     * @param id
     * @return
     */
    FactoryProductBasicEntity GetFactoryProductBasicById(int id);

    /**
     * 供应商商品查询
     * @param ids
     * @return
     */
    ArrayList<FactoryProductBasicEntity> GetFactoryProductBasics(Iterable<Integer> ids);

    /**
     * 供应商商品查询
     * @param factoryId
     * @return
     */
    ArrayList<FactoryProductBasicEntity> GetFactoryProductBasicsByFactoryId(Integer factoryId);

    /**
     * 供应商商品查询
     * @param productBasicId
     * @return
     */
    ArrayList<FactoryProductBasicEntity> GetFactoryProductBasicsByProductBasicId(Integer productBasicId);

    /**
     * 新增供应商商品
     * @param factoryProductBasicEntity
     * @return
     */
    FactoryProductBasicEntity AddFactoryProductBasic(FactoryProductBasicEntity factoryProductBasicEntity);

    /**
     * 返回所有供应商商品信息
     * @return
     */
    List<FactoryProductBasicEntity> GetAll();

    /**
     * 删除供应商商品
     * @param factoryProductBasicId
     * @return
     */
    boolean RemoveFactoryProductBasic(int factoryProductBasicId);
}
