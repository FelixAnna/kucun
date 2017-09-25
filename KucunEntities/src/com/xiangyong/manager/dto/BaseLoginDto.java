package com.xiangyong.manager.dto;
import com.xiangyong.manager.BaseDto;

import javax.validation.constraints.NotNull;

public class BaseLoginDto extends BaseDto {

    @NotNull(message = "来源不能为空")
    private Integer source;

    private String ip;

    /**
     * 设备信息
     */
    private String deviceInfo;

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}