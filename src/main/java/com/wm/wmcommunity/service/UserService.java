package com.wm.wmcommunity.service;

import com.wm.wmcommunity.common.util.Response;
import com.wm.wmcommunity.entity.dto.LoginQuery;
import com.wm.wmcommunity.entity.dto.RegisterDto;

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
public interface UserService {

    /**
     * 登录
     *
     * @param loginQuery
     * @return
     */
    Response login(LoginQuery loginQuery);

    /**
     * 注册
     *
     * @param registerDto
     * @return
     */
    Response register(RegisterDto registerDto);
}
