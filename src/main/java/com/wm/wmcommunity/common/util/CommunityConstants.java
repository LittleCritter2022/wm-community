package com.wm.wmcommunity.common.util;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> CommunityConstants
 * Product:
 * Creator: Gerry(0120)
 * Date Created: 2022/7/20
 * Description:
 * -------------------------------------------------------------------------------
 * Modification History
 * DATE                       Name                  Description
 * -------------------------------------------------------------------------------
 * 2022/7/20                 Gerry(0120)                 Create
 * -------------------------------------------------------------------------------
 *
 * @author Gerry(0120)
 */
public interface CommunityConstants {

    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS = 0;

    /**
     * 重复激活
     */
    int ACTIVATION_REPEAT = 1;

    /**
     * 激活失败
     */
    int ACTIVATION_FAILURE = 2;

    /**
     * 普通用户
     */
    int COMMON_USER = 0;

    /**
     * 超级管理员
     */
    int ADMIN = 1;

    /**
     * 版主
     */
    int MODERATOR = 2;

    /**
     * 默认状态的登录凭证的超时时间 12小时
     * 1小时=3600秒 一天=24小时 1秒=1000毫秒
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12 * 1000;

    /**
     * 记住状态的登录凭证超时时间  30天
     * 1小时=3600秒 一天=24小时 1秒=1000毫秒
     */
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 1000 * 30;

}
