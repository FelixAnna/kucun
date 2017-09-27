package com.xiangyong.manager.data;

import com.xiangyong.manager.entities.ProductBasicEntity;

public interface ProductBasicRepository extends BaseRepository<ProductBasicEntity, Integer> {
    Iterable<ProductBasicEntity> findByUserId(int userId);
}
