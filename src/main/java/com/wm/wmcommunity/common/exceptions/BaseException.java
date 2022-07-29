package com.wm.wmcommunity.common.exceptions;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> BaseException
 * Product:
 * Creator: Gerry(0120)
 * Date Created: 2022/7/28
 * Description:
 * -------------------------------------------------------------------------------
 * Modification History
 * DATE                       Name                  Description
 * -------------------------------------------------------------------------------
 * 2022/7/28                 Gerry(0120)                 基础 异常类
 * -------------------------------------------------------------------------------
 *
 * @author Gerry(0120)
 */
public class BaseException extends RuntimeException {

    /**
     * 状态码
     */
    private final int code;

    /**
     * 构造方法
     *
     * @param code 状态码
     * @param msg  信息
     */
    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    /**
     * 构造方法
     *
     * @param code  状态码
     * @param msg   信息
     * @param cause 异常
     */
    public BaseException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

    /**
     * 返回状态码
     *
     * @return 状态码
     */
    public int getCode() {
        return code;
    }

}
