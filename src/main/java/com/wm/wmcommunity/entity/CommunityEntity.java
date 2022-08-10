package com.wm.wmcommunity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> CommunityEntity
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
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "comment")
public class CommunityEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "user_id")
    private Integer userId;

    @TableField(value = "entity_type")
    private Integer entityType;

    @TableField(value = "entity_id")
    private Integer entityId;

    @TableField(value = "target_id")
    private Integer targetId;

    @TableField(value = "content")
    private String content;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
