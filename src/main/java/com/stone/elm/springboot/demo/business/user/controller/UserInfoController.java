package com.stone.elm.springboot.demo.business.user.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户管理/用户信息")
@RestController("userInfoController")
@RequestMapping("/user/userInfo")
public class UserInfoController {

}
