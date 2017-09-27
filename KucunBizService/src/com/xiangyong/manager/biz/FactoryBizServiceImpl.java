package com.xiangyong.manager.biz;

import com.xiangyong.manager.biz.interfaces.FactoryBizService;
import com.xiangyong.manager.data.FactoryRepository;
import com.xiangyong.manager.entities.FactoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class FactoryBizServiceImpl implements FactoryBizService {
    @Autowired
    private FactoryRepository factoryRepository;

    @Override
    public FactoryEntity GetFactoryById(int id) {
        FactoryEntity factoryEntity = factoryRepository.findOne(id);
        return factoryEntity;
    }

    @Override
    public ArrayList<FactoryEntity> GetFactories(Iterable<Integer> ids) {
        Iterable<FactoryEntity> factoryEntities = this.factoryRepository.findAll(ids);
        ArrayList<FactoryEntity> results= new ArrayList<>();
        factoryEntities.forEach(x->results.add(x));
        return results;
    }

    @Override
    public ArrayList<FactoryEntity> GetFactoriesByUserId(Integer userId) {
        Iterable<FactoryEntity> factoryEntities = this.factoryRepository.findByUserId(userId);
        ArrayList<FactoryEntity> results= new ArrayList<>();
        factoryEntities.forEach(x->results.add(x));
        return results;
    }

    @Override
    public FactoryEntity AddFactory(FactoryEntity factoryEntity) {
        FactoryEntity newFactoryEntity = this.factoryRepository.saveAndFlush(factoryEntity);
        return  newFactoryEntity;
    }

    @Override
    public List<FactoryEntity> GetAll() {
        return this.factoryRepository.findAll();
    }

    @Override
    public boolean RemoveFactory(int factoryId) {
        FactoryEntity factoryEntity = factoryRepository.findOne(factoryId);
        if(factoryEntity.isDeleted()) {
            factoryEntity.setDeleted(true);
            factoryEntity.setUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
            this.factoryRepository.saveAndFlush(factoryEntity);
            return  true;
        }

        return false;
    }
}
