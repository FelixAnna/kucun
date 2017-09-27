package com.xiangyong.manager.data;

import com.xiangyong.manager.entities.ProductDetailEntity;

public interface ProductDetailRepository extends BaseRepository<ProductDetailEntity, Integer> {
    int countByColorId(int colorId);

    int countBySizeId(int sizeId);

    Iterable<ProductDetailEntity> findByProductBasicId(int productBasicId);
}
