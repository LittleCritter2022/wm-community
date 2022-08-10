package com.wm.wmcommunity.controller;

import com.wm.wmcommunity.common.annotations.LoginAnnotation;
import com.wm.wmcommunity.common.util.Response;
import com.wm.wmcommunity.entity.dto.LoginQuery;
import com.wm.wmcommunity.entity.dto.RegisterDto;
import com.wm.wmcommunity.entity.dto.UpdateDto;
import com.wm.wmcommunity.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    @LoginAnnotation(module = "login", operator = "登录")
    @ApiOperation(value = "登录")
    public Response login(@RequestBody LoginQuery loginQuery, HttpServletRequest request,
                          HttpServletResponse response) {
        return userServiceImpl.login(loginQuery, request, response);
    }

    @PostMapping("/register")
    @LoginAnnotation(module = "register", operator = "注册")
    @ApiOperation("注册")
    public Response register(@RequestBody RegisterDto registerDto) {
        return userServiceImpl.register(registerDto);
    }

    @PostMapping("/update")
    @LoginAnnotation(module = "update", operator = "修改用户信息")
    @ApiOperation(value = "修改用户信息")
    public Response update(@RequestBody UpdateDto updateDto) {
        return userServiceImpl.updateUser(updateDto);
    }

    @GetMapping("/delete")
    @LoginAnnotation(module = "delete", operator = "注销账号")
    @ApiOperation(value = "注销账号")
    public Response deleteById(@RequestParam Integer id) {
        return userServiceImpl.deleteById(id);
    }

    @GetMapping("/activation")
    @LoginAnnotation(module = "activation", operator = "激活")
    @ApiOperation(value = "激活")
    public Response activation(@RequestParam("id") String id, @RequestParam("code") String code) {
        return userServiceImpl.activation(id, code);
    }

    @GetMapping("/kaptCha")
    @LoginAnnotation(module = "kaptCha", operator = "验证码")
    @ApiOperation("验证码")
    public void kaptCha(HttpServletResponse response) {
        userServiceImpl.kaptCha(response);
    }
}
