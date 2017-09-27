package com.xiangyong.manager.biz;

import com.xiangyong.manager.biz.interfaces.ProductBasicBizService;
import com.xiangyong.manager.data.ProductBasicRepository;
import com.xiangyong.manager.entities.ProductBasicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductBasicBizServiceImpl implements ProductBasicBizService {

    @Autowired
    private ProductBasicRepository productBasicRepository;

    @Override
    public ProductBasicEntity GetProductBasicById(int id) {
        ProductBasicEntity productBasicEntity = this.productBasicRepository.findOne(id);
        return productBasicEntity;
    }

    @Override
    public ArrayList<ProductBasicEntity> GetProductBasics(Iterable<Integer> ids) {
        Iterable<ProductBasicEntity> productBasicEntities = this.productBasicRepository.findAll(ids);
        ArrayList<ProductBasicEntity> results= new ArrayList<>();
        productBasicEntities.forEach(x->results.add(x));
        return results;
    }

    @Override
    public ArrayList<ProductBasicEntity> GetProductBasicsByUserId(Integer userId) {
        Iterable<ProductBasicEntity> productBasicEntities = this.productBasicRepository.findByUserId(userId);
        ArrayList<ProductBasicEntity> results= new ArrayList<>();
        productBasicEntities.forEach(x->results.add(x));
        return results;
    }

    @Override
    public ProductBasicEntity AddProductBasic(ProductBasicEntity productBasicEntity) {
        ProductBasicEntity newProductBasicEntity = this.productBasicRepository.saveAndFlush(productBasicEntity);
        return newProductBasicEntity;
    }

    @Override
    public List<ProductBasicEntity> GetAll() {
        return this.productBasicRepository.findAll();
    }

    @Override
    public boolean RemoveProductBasic(int productBasicId) {
        ProductBasicEntity productBasicEntity = productBasicRepository.findOne(productBasicId);
        if(productBasicEntity.isDeleted()) {
            productBasicEntity.setDeleted(true);
            productBasicEntity.setUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
            this.productBasicRepository.saveAndFlush(productBasicEntity);
            return  true;
        }

        return false;
    }
}
