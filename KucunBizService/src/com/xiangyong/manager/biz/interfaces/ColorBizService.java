package com.xiangyong.manager.biz.interfaces;

import com.xiangyong.manager.entities.ColorEntity;

import java.util.List;

public interface ColorBizService {

    /**
     * 颜色查询
     * @param Id
     * @return
     */
    ColorEntity GetColorById(int Id);

    /**
     * 新增颜色
     * @param colorEntity
     * @return
     */
    ColorEntity AddColor(ColorEntity colorEntity);

    /**
     * 返回所有颜色信息
     * @return
     */
    List<ColorEntity> GetAll();
}
