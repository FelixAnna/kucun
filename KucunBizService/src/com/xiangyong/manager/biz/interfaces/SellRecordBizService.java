package com.xiangyong.manager.biz.interfaces;

import com.xiangyong.manager.entities.SellRecordEntity;

import java.util.ArrayList;
import java.util.List;

public interface SellRecordBizService {
    /**
     * 销售记录查询
     * @param id
     * @return
     */
    SellRecordEntity GetSellRecordById(int id);

    /**
     * 销售记录查询
     * @param ids
     * @return
     */
    ArrayList<SellRecordEntity> GetSellRecords(Iterable<Integer> ids);

    /**
     * 销售记录查询
     * @param prductDetailId
     * @return
     */
    ArrayList<SellRecordEntity> GetSellRecordsByProductDetailId(Integer prductDetailId);

    /**
     * 新增销售记录
     * @param sellRecordEntity
     * @return
     */
    SellRecordEntity AddSellRecord(SellRecordEntity sellRecordEntity);

    /**
     * 返回所有销售记录信息
     * @return
     */
    List<SellRecordEntity> GetAll();

    /**
     * 删除销售记录
     * @return
     */
    boolean RemoveSellRecord(int sellRecordId);

    /**
     * 取消销售记录
     * @return
     */
    boolean CancelSellRecord(int sellRecordId);

    /**
     * 确认完成销售记录
     * @return
     */
    boolean FinishSellRecord(int sellRecordId);
}
