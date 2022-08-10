package com.wm.wmcommunity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing YUXiangRuanJian Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> DiscussPost
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
@TableName(value = "discuss_post")
@AllArgsConstructor
@NoArgsConstructor
public class DiscussPost {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "user_id")
    private String userId;

    @TableField(value = "title")
    private String title;

    @TableField(value = "content")
    private String content;

    @TableField(value = "type")
    private Integer type;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "comment_count")
    private Integer commentCount;

    @TableField(value = "score")
    private Double score;
}
