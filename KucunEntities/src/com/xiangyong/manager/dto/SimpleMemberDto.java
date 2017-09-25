package com.xiangyong.manager.dto;

import com.xiangyong.manager.BaseDto;

/**
 * 用户登录信息
 */
public class SimpleMemberDto extends BaseDto {

    private  int userId;

    private String cellPhone;

    private String userName;

    private String token;

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
