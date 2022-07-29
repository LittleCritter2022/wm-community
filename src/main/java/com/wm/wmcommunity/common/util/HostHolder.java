package com.wm.wmcommunity.common.util;

import com.wm.wmcommunity.entity.UserEntity;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> HostHolder
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
public class HostHolder {

    /**
     * 定义一个本地线程池用来存储当前登录人的详细信息
     */
    public static final ThreadLocal<UserEntity> THREAD_LOCAL = new ThreadLocal<>();

    public static void setUser(UserEntity user) {
        THREAD_LOCAL.set(user);
    }

    public static UserEntity getUser() {
        return THREAD_LOCAL.get();
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }
}
