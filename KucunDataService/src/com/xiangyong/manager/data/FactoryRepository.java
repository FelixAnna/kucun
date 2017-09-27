package com.xiangyong.manager.data;

import com.xiangyong.manager.entities.FactoryEntity;

public interface FactoryRepository extends BaseRepository<FactoryEntity, Integer> {
    Iterable<FactoryEntity> findByUserId(int userId);
}
