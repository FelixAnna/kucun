package com.xiangyong.manager.biz.interfaces;

import com.xiangyong.manager.dto.SimpleMemberDto;

public interface TokenBizService {
    /**
     * 保存token 到Redis （更新当前token过期时间）
     * @param simpleMemberDto
     */
    void SaveToken(SimpleMemberDto simpleMemberDto);

    /**
     * 根据token查找用户先信息
     * @param token
     * @return
     */
    SimpleMemberDto GetSimpleMemberDtoByToken(String token);

    /**
     * token 清除
     * @param token
     * @return
     */
    boolean Clear(String token);
}
