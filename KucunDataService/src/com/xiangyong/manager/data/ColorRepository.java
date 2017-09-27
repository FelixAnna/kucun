package com.xiangyong.manager.data;

import com.xiangyong.manager.entities.ColorEntity;

//@Repository //不需要
public interface ColorRepository extends BaseRepository<ColorEntity, Integer>{
        ColorEntity findByColorId(int id);

        Iterable<ColorEntity> findByUserId(int userId);
}
