package com.xiangyong.manager.biz;

import com.xiangyong.manager.biz.interfaces.ProductDetailBizService;
import com.xiangyong.manager.data.ProductDetailRepository;
import com.xiangyong.manager.entities.ProductDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDetailBizServiceImpl implements ProductDetailBizService {

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Override
    public ProductDetailEntity GetProductDetailById(int id) {
        ProductDetailEntity productDetailEntity = this.productDetailRepository.findOne(id);
        return productDetailEntity;
    }

    @Override
    public ArrayList<ProductDetailEntity> GetProductDetails(Iterable<Integer> ids) {
        Iterable<ProductDetailEntity> productDetailEntities = this.productDetailRepository.findAll(ids);
        ArrayList<ProductDetailEntity> results= new ArrayList<>();
        productDetailEntities.forEach(x->results.add(x));
        return results;
    }

    @Override
    public ArrayList<ProductDetailEntity> GetProductDetailsByProductBasicId(Integer productBasicId) {
        Iterable<ProductDetailEntity> productDetailEntities = this.productDetailRepository.findByProductBasicId(productBasicId);
        ArrayList<ProductDetailEntity> results= new ArrayList<>();
        productDetailEntities.forEach(x->results.add(x));
        return results;
    }

    @Override
    public ProductDetailEntity AddProductDetail(ProductDetailEntity productDetailEntity) {
        ProductDetailEntity newProductDetailEntity = this.productDetailRepository.saveAndFlush(productDetailEntity);
        return newProductDetailEntity;
    }

    @Override
    public List<ProductDetailEntity> GetAll() {
        return this.productDetailRepository.findAll();
    }

    @Override
    public boolean RemoveProductDetail(int productDetailId) {
        ProductDetailEntity productDetailEntity = productDetailRepository.findOne(productDetailId);
        if(productDetailEntity.getDeleted()) {
            productDetailEntity.setDeleted(true);
            productDetailEntity.setUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
            this.productDetailRepository.saveAndFlush(productDetailEntity);
            return  true;
        }

        return false;
    }
}
