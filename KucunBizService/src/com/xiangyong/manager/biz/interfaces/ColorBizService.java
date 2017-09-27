package com.xiangyong.manager.biz.interfaces;

import com.xiangyong.manager.entities.ColorEntity;

import java.util.ArrayList;
import java.util.List;

public interface ColorBizService {

    /**
     * 颜色查询
     * @param id
     * @return
     */
    ColorEntity GetColorById(int id);

    /**
     * 颜色查询
     * @param ids
     * @return
     */
    ArrayList<ColorEntity> GetColors(Iterable<Integer> ids);

    /**
     * 颜色查询
     * @param userId
     * @return
     */
    ArrayList<ColorEntity> GetColorsByUserId(Integer userId);

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

    /**
     * 删除颜色
     * @return
     */
    boolean RemoveColor(int colorId);
}
