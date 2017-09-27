package com.xiangyong.manager.biz;

import com.xiangyong.manager.biz.interfaces.SizeBizService;
import com.xiangyong.manager.core.exception.BadRequestException;
import com.xiangyong.manager.data.ProductDetailRepository;
import com.xiangyong.manager.data.SizeRepository;
import com.xiangyong.manager.entities.SizeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SizeBizServiceImpl implements SizeBizService {

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Override
    public SizeEntity GetSizeById(int Id) {
        SizeEntity sizeEntity = this.sizeRepository.findOne(Id);
        return  sizeEntity;
    }

    @Override
    public ArrayList<SizeEntity> GetSizes(Iterable<Integer> Ids) {
        Iterable<SizeEntity> sizeEntities = this.sizeRepository.findAll(Ids);
        ArrayList<SizeEntity> results= new ArrayList<>();
        sizeEntities.forEach(x->results.add(x));
        return results;
    }

    @Override
    public ArrayList<SizeEntity> GetSizesByUserId(Integer userId) {
        Iterable<SizeEntity> sizeEntities = this.sizeRepository.findByUserId(userId);
        ArrayList<SizeEntity> results= new ArrayList<>();
        sizeEntities.forEach(x->results.add(x));
        return results;
    }

    @Override
    public SizeEntity AddSize(SizeEntity sizeEntity) {
        SizeEntity newSizeEntity = this.sizeRepository.saveAndFlush(sizeEntity);
        return  newSizeEntity;
    }

    @Override
    public List<SizeEntity> GetAll() {
        return this.sizeRepository.findAll();
    }

    @Override
    public boolean RemoveSize(int sizeId) {
        if(productDetailRepository.countBySizeId(sizeId)>0){
            throw new BadRequestException("一个或多个商品管理到此记录，请先取消关联。");
        }
        this.sizeRepository.delete(sizeId);
        return  true;
    }
}
