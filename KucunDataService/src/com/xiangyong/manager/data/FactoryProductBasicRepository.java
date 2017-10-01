package com.xiangyong.manager.data;

import com.xiangyong.manager.entities.FactoryProductBasicEntity;

public interface FactoryProductBasicRepository extends BaseRepository<FactoryProductBasicEntity, Integer> {

    Iterable<FactoryProductBasicEntity> findByFactoryId(int factoryId);

    Iterable<FactoryProductBasicEntity> findByProductBasicId(int productBasicId);
}
