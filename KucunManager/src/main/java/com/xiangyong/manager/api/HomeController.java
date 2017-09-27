package com.xiangyong.manager.api;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "首页接口")
@RestController
@RequestMapping("home")
public class HomeController extends BaseController{

}
