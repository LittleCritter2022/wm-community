package com.wm.wmcommunity.common.constant;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> CodeEnum
 * Product:
 * Creator: Gerry(0120)
 * Date Created: 2022/7/28
 * Description:
 * -------------------------------------------------------------------------------
 * Modification History
 * DATE                       Name                  Description
 * -------------------------------------------------------------------------------
 * 2022/7/28                 Gerry(0120)                 状态码 枚举类
 * -------------------------------------------------------------------------------
 *
 * @author Gerry(0120)
 */
public enum CodeEnum {

    // 成功
    SUCCESS(20000, "success"),
    // 错误
    FAILED(500, "failed"),
    // 200xx 通用错误
    ERROR_DATA_TRANS_EXCEPTION(20001, "数据转换错误"),
    ERROR_DATABASE_FAIL(20002, "数据库操作失败"),
    ERROR_JSON_PARSER(20003, "json结果解析错误"),
    ERROR_JSON_PARAM(20004, "json参数解析错误"),
    ERROR_OPERATION_FAILED(20005, "操作失败"),
    ERROR_FILE_PARSER(20006, "文件解析错误"),
    ERROR_FILE_NOT_EXSIT(20007, "文件不存在"),
    ERROR_PARAM(20008, "参数错误"),
    ERROR_HTTP(20009, "HTTP通信异常"),
    ERROR_FTP(20010, "FTP异常"),
    ERROR_SFTP(20011, "SFTP异常"),

    // 201xx 用户管理
    ERROR_USER_GET_FAILED(20101, "获取用户信息异常"),
    ERROR_ORG_NOTEXIT(20102, "部门不存在"),
    ERROR_LOCAL_USER_CREATE(20103, "本地用户创建失败"),
    ERROR_LOCAL_USER_DELETE(20104, "本地用户删除失败"),
    ERROR_DEPT_USER_EXIST(20105, "用户已经存在"),
    ERROR_USER_ROLE_NULL(20106, "当前用户角色为空"),
    ERROR_LOGIN_NOT_EXIT(20107, "用户不存在"),
    ERROR_USER_AUTH_FAILED(20108, "用户认证错误"),
    ERROR_CREATE_LOGIN_STATE(20109, "创建临时状态失败"),
    ERROR_LOGIN_STATE(20110, "登录回调状态不合法"),

    // 204xx 特殊
    ERROR_AUTHENTICATION(20401, "没有权限"),
    ;

    /**
     * 状态码
     */
    private int code;

    /**
     * 信息
     */
    private String message;

    /**
     * 枚举值
     */
    private static Map<Integer, String> values = new HashMap<>();

    static {
        for (CodeEnum codeEnum : CodeEnum.values()) {
            values.put(codeEnum.getCode(), codeEnum.getMessage());
        }
    }

    /**
     * 构造方法
     *
     * @param code    状态码
     * @param message 信息
     */
    CodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取错误代码
     *
     * @return 错误代码
     */
    public int getCode() {
        return this.code;
    }

    /**
     * 获取描述
     *
     * @return 描述信息
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * 根据状态码获取信息
     *
     * @param code 状态码
     * @return 信息
     */
    public static String getMessageByCode(int code) {
        return StringUtils.isEmpty(values.get(code)) ? "未知异常" : values.get(code);
    }
}
