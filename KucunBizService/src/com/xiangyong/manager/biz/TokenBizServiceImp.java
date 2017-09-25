package com.xiangyong.manager.biz;

import com.xiangyong.manager.biz.interfaces.TokenBizService;
import com.xiangyong.manager.core.cache.CacheService;
import com.xiangyong.manager.core.util.WsConstants;
import com.xiangyong.manager.dto.SimpleMemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenBizServiceImp implements TokenBizService {

    @Autowired
    private CacheService cacheService;

    /**
     * 保存token 到Redis （更新当前token过期时间）
     * @param simpleMemberDto
     */
    @Override
    public void SaveToken(SimpleMemberDto simpleMemberDto) {
        String strUserIdKey = String.format("%s%s", WsConstants.ACCESS_TOKEN_USERID_PREFIX , String.valueOf(simpleMemberDto.getUserId()));
        String strTokenKey;
        //如果已经存在的token，重复利用,并加长过滤时间
        if(cacheService.exists(strUserIdKey)){
            String preToken = cacheService.get(strUserIdKey);
            strTokenKey = String.format("%s%s", WsConstants.ACCESS_TOKEN_PREFIX, preToken);
            boolean isUserKeySet = cacheService.expire(strUserIdKey, WsConstants.APP_SESSION_EXPIRE_TIME);
            boolean isTokenKeySet = cacheService.expire(strTokenKey, WsConstants.APP_SESSION_EXPIRE_TIME);
            if(isUserKeySet && isTokenKeySet) {
                simpleMemberDto.setToken(preToken);
                return;
            }
        }

        //新Token, 七天免登陆
        strTokenKey = String.format("%s%s", WsConstants.ACCESS_TOKEN_PREFIX, simpleMemberDto.getToken());
        cacheService.setObjectWithJsonFormat(strTokenKey, simpleMemberDto, WsConstants.APP_SESSION_EXPIRE_TIME);
        cacheService.set(strUserIdKey, simpleMemberDto.getToken(), WsConstants.APP_SESSION_EXPIRE_TIME);
    }

    @Override
    public SimpleMemberDto GetSimpleMemberDtoByToken(String token) {
        String strTokenKey = String.format("%s%s", WsConstants.ACCESS_TOKEN_PREFIX, token);;
        if(cacheService.exists(strTokenKey)) {
            SimpleMemberDto simpleMemberDto = cacheService.getObjectFromJsonFormat(strTokenKey, SimpleMemberDto.class);
            return simpleMemberDto;
        }
        return null;
    }

    @Override
    public boolean Clear(String token) {
        String strTokenKey = String.format("%s%s", WsConstants.ACCESS_TOKEN_PREFIX, token);
        //如果已经存在的token，重复利用,并加长过滤时间
        if(cacheService.exists(strTokenKey)){
            SimpleMemberDto simpleMemberDto = cacheService.getObjectFromJsonFormat(strTokenKey, SimpleMemberDto.class);
            String strUserIdKey = String.format("%s%s", WsConstants.ACCESS_TOKEN_USERID_PREFIX , String.valueOf(simpleMemberDto.getUserId()));
            cacheService.delete(strUserIdKey);
            cacheService.delete(strTokenKey);
            cacheService.delete("123");
            return true;
        }
        return false;
    }
}
