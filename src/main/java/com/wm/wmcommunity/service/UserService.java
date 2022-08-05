package com.wm.wmcommunity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wm.wmcommunity.common.util.Response;
import com.wm.wmcommunity.entity.UserEntity;
import com.wm.wmcommunity.entity.dto.LoginQuery;
import com.wm.wmcommunity.entity.dto.RegisterDto;
import com.wm.wmcommunity.entity.dto.UpdateDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> UserService
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
public interface UserService extends IService<UserEntity> {

    /**
     * 登录
     *
     * @param loginQuery
     * @param request
     * @param response
     * @return
     */
    Response login(LoginQuery loginQuery, HttpServletRequest request, HttpServletResponse response);

    /**
     * 注册
     *
     * @param registerDto
     * @return
     */
    Response register(RegisterDto registerDto);

    /**
     * 修改用户信息
     *
     * @param updateDto
     * @return
     */
    Response updateUser(UpdateDto updateDto);

    /**
     * 注销账号
     *
     * @param id
     * @return
     */
    Response deleteById(Integer id);

    /**
     * 激活账号
     *
     * @param id
     * @param code
     * @return
     */
    Response activation(String id, String code);

    /**
     * 验证码
     *
     * @param response
     */
    void kaptcha(HttpServletResponse response);
}
