package com.xiangyong.manager.data;

import com.xiangyong.manager.entities.SellRecordEntity;

public interface SellRecordRepository extends BaseRepository<SellRecordEntity, Integer> {
    Iterable<SellRecordEntity> findByProductDetailId(int productDetailId);
}
