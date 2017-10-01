package com.xiangyong.manager.biz;

import com.xiangyong.manager.SellStatus;
import com.xiangyong.manager.biz.interfaces.SellRecordBizService;
import com.xiangyong.manager.data.ProductDetailRepository;
import com.xiangyong.manager.data.SellRecordRepository;
import com.xiangyong.manager.entities.SellRecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SellRecordBizServiceImpl implements SellRecordBizService {
    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private SellRecordRepository sellRecordRepository;

    @Override
    public SellRecordEntity GetSellRecordById(int id) {
        SellRecordEntity sellRecordEntity = this.sellRecordRepository.findOne(id);
        return sellRecordEntity;
    }

    @Override
    public ArrayList<SellRecordEntity> GetSellRecords(Iterable<Integer> ids) {
        Iterable<SellRecordEntity> sellRecordEntities = this.sellRecordRepository.findAll(ids);
        ArrayList<SellRecordEntity> results= new ArrayList<>();
        sellRecordEntities.forEach(x->results.add(x));
        return results;
    }

    @Override
    public ArrayList<SellRecordEntity> GetSellRecordsByProductDetailId(Integer prductDetailId) {
        Iterable<SellRecordEntity> sellRecordEntities = this.sellRecordRepository.findByProductDetailId(prductDetailId);
        ArrayList<SellRecordEntity> results= new ArrayList<>();
        sellRecordEntities.forEach(x->results.add(x));
        return results;
    }

    @Override
    public SellRecordEntity AddSellRecord(SellRecordEntity sellRecordEntity) {
        SellRecordEntity newSellRecordEntity = this.sellRecordRepository.saveAndFlush(sellRecordEntity);
        return  newSellRecordEntity;
    }

    @Override
    public List<SellRecordEntity> GetAll() {
        return this.sellRecordRepository.findAll();
    }

    @Override
    public boolean RemoveSellRecord(int sellRecordId) {
        SellRecordEntity sellRecordEntity = sellRecordRepository.findOne(sellRecordId);
        if(sellRecordEntity.getDeleted()) {
            sellRecordEntity.setStatus(SellStatus.Cancelled.getValue());
            sellRecordEntity.setDeleted(true);
            //sellRecordEntity.setFinishedTime(Timestamp.valueOf(LocalDateTime.now()));
            this.sellRecordRepository.saveAndFlush(sellRecordEntity);
            return  true;
        }

        return false;
    }

    @Override
    public boolean CancelSellRecord(int sellRecordId) {
        SellRecordEntity sellRecordEntity = sellRecordRepository.findOne(sellRecordId);
        if(sellRecordEntity.getStatus() == SellStatus.Ready.getValue()) {
            sellRecordEntity.setStatus(SellStatus.Cancelled.getValue());
            sellRecordEntity.setFinishedTime(Timestamp.valueOf(LocalDateTime.now()));
            this.sellRecordRepository.saveAndFlush(sellRecordEntity);
            return  true;
        }

        return false;
    }

    @Override
    public boolean FinishSellRecord(int sellRecordId) {
        SellRecordEntity sellRecordEntity = sellRecordRepository.findOne(sellRecordId);
        if(sellRecordEntity.getStatus() == SellStatus.Ready.getValue()) {
            sellRecordEntity.setStatus(SellStatus.Deal.getValue());
            sellRecordEntity.setFinishedTime(Timestamp.valueOf(LocalDateTime.now()));
            this.sellRecordRepository.saveAndFlush(sellRecordEntity);
            return  true;
        }

        return false;
    }
}
