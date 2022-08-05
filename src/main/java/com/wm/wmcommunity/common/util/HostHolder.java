package com.wm.wmcommunity.common.util;

import com.wm.wmcommunity.entity.UserEntity;
import org.springframework.stereotype.Component;

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
@Component
public class HostHolder {

    /**
     * 定义一个本地线程池用来存储当前登录人的详细信息
     */
    public final ThreadLocal<UserEntity> THREAD_LOCAL = new ThreadLocal<>();

    public void setUser(UserEntity user) {
        THREAD_LOCAL.set(user);
    }

    public UserEntity getUser() {
        return THREAD_LOCAL.get();
    }

    public void clear() {
        THREAD_LOCAL.remove();
    }
}
