package com.xiangyong.manager.biz;

import com.xiangyong.manager.biz.interfaces.ColorBizService;
import com.xiangyong.manager.data.ColorRepository;
import com.xiangyong.manager.entities.ColorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ColorBizServiceImp implements ColorBizService{

    @Autowired
    private ColorRepository colorRepository;

    @Override
    public ColorEntity GetColorById(int Id) {
        ColorEntity colorEntity = this.colorRepository.findByColorId(Id);
        return  colorEntity;
    }

    @Override
    public ColorEntity AddColor(ColorEntity colorEntity) {
        ColorEntity newColorEntity = this.colorRepository.saveAndFlush(colorEntity);
        return  newColorEntity;
    }

    @Override
    public List<ColorEntity> GetAll() {
        return this.colorRepository.findAll();
    }
}
