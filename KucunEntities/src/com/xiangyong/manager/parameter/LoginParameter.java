package com.xiangyong.manager.parameter;

import com.xiangyong.manager.BaseDto;
import org.hibernate.validator.constraints.NotEmpty;

public class LoginParameter extends BaseDto{

    @NotEmpty(message = "手机号或用户名不能为空")
    private String userName;

    @NotEmpty(message = "密码不能为空")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
