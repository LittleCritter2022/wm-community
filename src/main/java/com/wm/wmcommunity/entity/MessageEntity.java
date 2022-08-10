package com.wm.wmcommunity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing YUXiangRuanJian Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> MessageEntity
 * Product:
 * Creator: Jerry(0120)
 * Date Created: 2022/8/9
 * Description:
 * -------------------------------------------------------------------------------
 * Modification History
 * DATE                       Name                  Description
 * -------------------------------------------------------------------------------
 * 2022/8/9                 Jerry(0120)                 Create
 * -------------------------------------------------------------------------------
 *
 * @author Jerry(0120)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "from_id")
    private Integer fromId;

    @TableField(value = "conversation_id")
    private String conversationId;

    @TableField(value = "content")
    private String content;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "create_time")
    private Date createTime;
}
