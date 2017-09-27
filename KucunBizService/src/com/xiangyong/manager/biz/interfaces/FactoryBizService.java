package com.xiangyong.manager.biz.interfaces;

import com.xiangyong.manager.entities.FactoryEntity;

import java.util.ArrayList;
import java.util.List;

public interface FactoryBizService {
    /**
     * 厂家/供应商查询
     * @param id
     * @return
     */
    FactoryEntity GetFactoryById(int id);

    /**
     * 厂家/供应商查询
     * @param ids
     * @return
     */
    ArrayList<FactoryEntity> GetFactories(Iterable<Integer> ids);

    /**
     * 厂家/供应商查询
     * @param userId
     * @return
     */
    ArrayList<FactoryEntity> GetFactoriesByUserId(Integer userId);

    /**
     * 新增厂家/供应商
     * @param factoryEntity
     * @return
     */
    FactoryEntity AddFactory(FactoryEntity factoryEntity);

    /**
     * 返回所有厂家/供应商信息
     * @return
     */
    List<FactoryEntity> GetAll();

    /**
     * 删除厂家/供应商
     * @return
     */
    boolean RemoveFactory(int factoryId);
}
