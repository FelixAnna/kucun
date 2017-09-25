package com.xiangyong.manager.biz.interfaces;

import com.xiangyong.manager.dto.LoginMiddleDto;
import com.xiangyong.manager.dto.SimpleMemberDto;

public interface UserBizService {

    /**
     * 处理用户登录
     * @param loginMiddleDto
     * @return 登录信息 + token
     */
    SimpleMemberDto Login(LoginMiddleDto loginMiddleDto);

    /**
     * 更新用户头像
     * @param userId
     * @param icon
     * @return
     */
    Boolean UpdateUserAvatar(int userId, String icon);
}
