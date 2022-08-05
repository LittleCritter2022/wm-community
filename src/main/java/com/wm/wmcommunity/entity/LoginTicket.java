package com.wm.wmcommunity.entity;

import lombok.*;

import java.util.Date;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing YUXiangRuanJian Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> LoginTicket
 * Product:
 * Creator: Jerry(0120)
 * Date Created: 2022/8/5
 * Description:
 * -------------------------------------------------------------------------------
 * Modification History
 * DATE                       Name                  Description
 * -------------------------------------------------------------------------------
 * 2022/8/5                 Jerry(0120)                 登录凭证
 * -------------------------------------------------------------------------------
 *
 * @author Jerry(0120)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LoginTicket {

    private int id;

    private int userId;

    private String ticket;

    private int status;

    private Date expired;
}
