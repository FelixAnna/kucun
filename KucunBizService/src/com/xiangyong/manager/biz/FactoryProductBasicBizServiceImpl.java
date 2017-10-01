package com.xiangyong.manager.biz;

import com.xiangyong.manager.biz.interfaces.FactoryProductBasicBizService;
import com.xiangyong.manager.data.FactoryProductBasicRepository;
import com.xiangyong.manager.entities.FactoryProductBasicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class FactoryProductBasicBizServiceImpl implements FactoryProductBasicBizService {

    @Autowired
    private FactoryProductBasicRepository factoryProductBasicRepository;

    @Override
    public FactoryProductBasicEntity GetFactoryProductBasicById(int id) {
        FactoryProductBasicEntity factoryProductBasicEntity = factoryProductBasicRepository.findOne(id);
        return factoryProductBasicEntity;
    }

    @Override
    public ArrayList<FactoryProductBasicEntity> GetFactoryProductBasics(Iterable<Integer> ids) {
        Iterable<FactoryProductBasicEntity> factoryProductBasicEntities = this.factoryProductBasicRepository.findAll(ids);
        ArrayList<FactoryProductBasicEntity> results= new ArrayList<>();
        factoryProductBasicEntities.forEach(x->results.add(x));
        return results;
    }

    @Override
    public ArrayList<FactoryProductBasicEntity> GetFactoryProductBasicsByFactoryId(Integer factoryId) {
        Iterable<FactoryProductBasicEntity> factoryProductBasicEntities = this.factoryProductBasicRepository.findByFactoryId(factoryId);
        ArrayList<FactoryProductBasicEntity> results= new ArrayList<>();
        factoryProductBasicEntities.forEach(x->results.add(x));
        return results;
    }

    @Override
    public ArrayList<FactoryProductBasicEntity> GetFactoryProductBasicsByProductBasicId(Integer productBasicId) {
        Iterable<FactoryProductBasicEntity> factoryProductBasicEntities = this.factoryProductBasicRepository.findByProductBasicId(productBasicId);
        ArrayList<FactoryProductBasicEntity> results= new ArrayList<>();
        factoryProductBasicEntities.forEach(x->results.add(x));
        return results;
    }

    @Override
    public FactoryProductBasicEntity AddFactoryProductBasic(FactoryProductBasicEntity factoryProductBasicEntity) {
        FactoryProductBasicEntity newFactoryProductBasicEntity = this.factoryProductBasicRepository.saveAndFlush(factoryProductBasicEntity);
        return  newFactoryProductBasicEntity;
    }

    @Override
    public List<FactoryProductBasicEntity> GetAll() {
        return this.factoryProductBasicRepository.findAll();
    }

    @Override
    public boolean RemoveFactoryProductBasic(int factoryProductBasicId) {
        FactoryProductBasicEntity factoryProductBasicEntity = factoryProductBasicRepository.findOne(factoryProductBasicId);
        if(factoryProductBasicEntity.isDeleted()) {
            factoryProductBasicEntity.setDeleted(true);
            factoryProductBasicEntity.setUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
            this.factoryProductBasicRepository.saveAndFlush(factoryProductBasicEntity);
            return  true;
        }

        return false;
    }
}
