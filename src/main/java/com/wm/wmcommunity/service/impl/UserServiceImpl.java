package com.wm.wmcommunity.service.impl;

import com.wm.wmcommunity.common.util.Response;
import com.wm.wmcommunity.entity.dto.LoginQuery;
import com.wm.wmcommunity.entity.dto.RegisterDto;
import com.wm.wmcommunity.mapper.UserMapper;
import com.wm.wmcommunity.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> UserServiceImpl
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
@Service(value = "userServiceImpl")
public class UserServiceImpl implements UserService {

    @Resource(name = "")
    private UserMapper userMapper;

    @Override
    public Response login(LoginQuery loginQuery) {
        return null;
    }

    @Override
    public Response register(RegisterDto registerDto) {
        return null;
    }
}
