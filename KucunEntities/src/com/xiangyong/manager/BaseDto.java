package com.xiangyong.manager;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;

public class BaseDto implements Serializable{

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
