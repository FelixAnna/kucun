package com.xiangyong.manager.biz.interfaces;

import com.xiangyong.manager.entities.SizeEntity;

import java.util.ArrayList;
import java.util.List;

public interface SizeBizService {
    /**
     * 尺码查询
     * @param id
     * @return
     */
    SizeEntity GetSizeById(int id);

    /**
     * 尺码查询
     * @param ids
     * @return
     */
    ArrayList<SizeEntity> GetSizes(Iterable<Integer> ids);

    /**
     * 尺码查询
     * @param userId
     * @return
     */
    ArrayList<SizeEntity> GetSizesByUserId(Integer userId);

    /**
     * 新增尺码
     * @param sizeEntity
     * @return
     */
    SizeEntity AddSize(SizeEntity sizeEntity);

    /**
     * 返回所有尺码信息
     * @return
     */
    List<SizeEntity> GetAll();

    /**
     * 删除尺码
     * @return
     */
    boolean RemoveSize(int sizeId);
}
