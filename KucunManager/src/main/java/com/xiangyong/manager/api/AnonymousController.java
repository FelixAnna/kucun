package com.xiangyong.manager.api;

import com.xiangyong.manager.biz.interfaces.TokenBizService;
import com.xiangyong.manager.biz.interfaces.UserBizService;
import com.xiangyong.manager.core.client.enums.RequestSource;
import com.xiangyong.manager.core.util.MapperUtils;
import com.xiangyong.manager.dto.LoginMiddleDto;
import com.xiangyong.manager.dto.SimpleMemberDto;
import com.xiangyong.manager.parameter.LoginParameter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.xiangyong.manager.common.util.NetworkUtils.*;

@Api(tags = "可匿名访问接口")
@RestController
@RequestMapping("anonymous")
public class AnonymousController extends BaseController {

    @Autowired
    private UserBizService userBizService;

    @Autowired
    private TokenBizService tokenBizService;

    /**
     * 登录接口
     * @param source Android or IOS
     * @param parameter 手机号/用户名/邮箱 + 密码
     * @param br
     * @param request
     * @return
     */
    @ApiOperation(value="", notes="手机号/用户名/邮箱 + 密码，返回token+用户基本信息")
    @RequestMapping(value = "{source}/login",
            method = RequestMethod.POST,
            consumes = "application/json")
    public SimpleMemberDto Login(@PathVariable(name = "source") String source, @RequestBody @Valid  LoginParameter parameter, BindingResult br, HttpServletRequest request){
        validate(br);
        RequestSource requestSource = RequestSource.getByAppName(source);
        //查询用户
        LoginMiddleDto memberLoginDto = MapperUtils.map(parameter, LoginMiddleDto.class);
        memberLoginDto.setSource(requestSource.getValue());
        memberLoginDto.setIp(getClientIpAddr(request));
        SimpleMemberDto userDto = userBizService.Login(memberLoginDto);
        tokenBizService.SaveToken(userDto);
        return userDto;
    }
}
