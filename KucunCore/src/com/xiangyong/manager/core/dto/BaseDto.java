package com.xiangyong.manager.core.dto;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by Rex.Lei on 2017/8/31.
 */
public class BaseDto implements Serializable{

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
