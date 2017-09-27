package com.xiangyong.manager.biz;

import com.xiangyong.manager.biz.interfaces.ColorBizService;
import com.xiangyong.manager.core.exception.BadRequestException;
import com.xiangyong.manager.data.ColorRepository;
import com.xiangyong.manager.data.ProductDetailRepository;
import com.xiangyong.manager.entities.ColorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ColorBizServiceImp implements ColorBizService{

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Override
    public ColorEntity GetColorById(int Id) {
        ColorEntity colorEntity = this.colorRepository.findByColorId(Id);
        return  colorEntity;
    }

    @Override
    public ArrayList<ColorEntity> GetColors(Iterable<Integer> Ids) {
        Iterable<ColorEntity> colorEntities = this.colorRepository.findAll(Ids);
        ArrayList<ColorEntity> results= new ArrayList<>();
        colorEntities.forEach(x->results.add(x));
        return results;
    }

    /**
     * 颜色查询
     * @param userId
     * @return
     */
    public ArrayList<ColorEntity> GetColorsByUserId(Integer userId){
        Iterable<ColorEntity> colorEntities = this.colorRepository.findByUserId(userId);
        ArrayList<ColorEntity> results= new ArrayList<>();
        colorEntities.forEach(x->results.add(x));
        return results;
    }

    @Override
    public ColorEntity AddColor(ColorEntity colorEntity) {
        ColorEntity newColorEntity = this.colorRepository.saveAndFlush(colorEntity);
        return  newColorEntity;
    }

    @Override
    public boolean RemoveColor(int colorId){
        if(productDetailRepository.countByColorId(colorId)>0){
            throw new BadRequestException("一个或多个商品管理到此记录，请先取消关联。");
        }
        this.colorRepository.delete(colorId);
        return  true;
    }

    @Override
    public List<ColorEntity> GetAll() {
        return this.colorRepository.findAll();
    }
}
