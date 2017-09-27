package com.xiangyong.manager.api;

import com.google.common.base.Strings;
import com.xiangyong.manager.biz.interfaces.FileBizService;
import com.xiangyong.manager.biz.interfaces.TokenBizService;
import com.xiangyong.manager.biz.interfaces.UserBizService;
import com.xiangyong.manager.dto.SimpleMemberDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "用户信息接口")
@RestController
@RequestMapping("user")
public class UserController extends BaseController{
    @Autowired
    private TokenBizService tokenBizService;

    @Autowired
    private UserBizService userBizService;

    @Autowired
    private FileBizService fileBizService;

    @ApiOperation(value = "用户信息")
    @RequestMapping(value = "info", method = RequestMethod.GET)
    public SimpleMemberDto GetUserInfo(@ApiIgnore @RequestAttribute SimpleMemberDto userInfo) {
        return userInfo;
    }

    @ApiOperation(value = "退出登录")
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public boolean Logout(@ApiIgnore @RequestAttribute SimpleMemberDto userInfo) {
        boolean result = tokenBizService.Clear(userInfo.getToken());
        if(result){
            return true;
        }else {
            return false;
        }
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public String Upload(@RequestParam("file") MultipartFile file, @ApiIgnore @RequestAttribute SimpleMemberDto userInfo){
        String newFileName = fileBizService.Save(file);
        if(!Strings.isNullOrEmpty(newFileName)){
            userBizService.UpdateUserAvatar(userInfo.getUserId(),newFileName);
        }

        return newFileName;
    }
}
