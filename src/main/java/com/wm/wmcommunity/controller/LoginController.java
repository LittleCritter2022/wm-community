package com.wm.wmcommunity.controller;

import com.wm.wmcommunity.common.annotations.LoginAnnotation;
import com.wm.wmcommunity.common.util.Response;
import com.wm.wmcommunity.entity.dto.LoginQuery;
import com.wm.wmcommunity.entity.dto.RegisterDto;
import com.wm.wmcommunity.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> LoginController
 * Product:
 * Creator: Gerry(0120)
 * Date Created: 2022/7/29
 * Description:
 * -------------------------------------------------------------------------------
 * Modification History
 * DATE                       Name                  Description
 * -------------------------------------------------------------------------------
 * 2022/7/29                 Gerry(0120)                 Create
 * -------------------------------------------------------------------------------
 *
 * @author Gerry(0120)
 */
@Api(value = "/index", description = "首页")
@RestController
@RequestMapping("/index")
public class LoginController {

    @Resource(name = "userServiceImpl")
    private UserService userServiceImpl;

    @PostMapping("/login")
    @LoginAnnotation
    @ApiOperation(value = "登录")
    public Response login(@RequestBody LoginQuery loginQuery) {
        return userServiceImpl.login(loginQuery);
    }

    @PostMapping("/register")
    @LoginAnnotation
    @ApiOperation("注册")
    public Response register(@RequestBody RegisterDto registerDto) {
        return userServiceImpl.register(registerDto);
    }

}
