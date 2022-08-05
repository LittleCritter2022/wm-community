package com.wm.wmcommunity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wm.wmcommunity.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> UserMapper
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
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
}
