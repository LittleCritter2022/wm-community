package com.wm.wmcommunity.common.util;

import com.wm.wmcommunity.common.constant.CodeEnum;
import com.wm.wmcommunity.common.exceptions.BaseException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> Response
 * Product:
 * Creator: Gerry(0120)
 * Date Created: 2022/7/29
 * Description:
 * -------------------------------------------------------------------------------
 * Modification History
 * DATE                       Name                  Description
 * -------------------------------------------------------------------------------
 * 2022/7/29                 Gerry(0120)                 响应 模型类
 * -------------------------------------------------------------------------------
 *
 * @author Gerry(0120)
 */
@Data
public class Response<T> {

    /**
     * 返回码
     */
    @ApiModelProperty(value = "返回码")
    private int code;

    /**
     * 信息
     */
    @ApiModelProperty(value = "信息")
    private String message;

    /**
     * 数据
     */
    @ApiModelProperty(value = "数据")
    private T data;

    /**
     * 构造方法
     */
    public Response() {
        this.code = CodeEnum.SUCCESS.getCode();
        this.message = CodeEnum.SUCCESS.getMessage();
    }

    /**
     * 构造方法
     *
     * @param data 数据
     */
    public Response(T data) {
        this.code = CodeEnum.SUCCESS.getCode();
        this.data = data;
        this.message = CodeEnum.SUCCESS.getMessage();
    }

    /**
     * 构造方法
     *
     * @param code    状态码
     * @param message 信息
     * @param data    数据
     */
    public Response(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 判断请求是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return this.code == CodeEnum.SUCCESS.getCode();
    }

    /**
     * 设置状态
     *
     * @param code    状态码
     * @param message 格式化信息
     * @param params  参数
     */
    public void setStatus(int code, String message, Object... params) {
        this.code = code;
        this.message = String.format(message, params);
    }

    /**
     * 设置状态
     *
     * @param codeEnum 状态码-枚举
     */
    public void setStatus(CodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }

    /**
     * 设置状态
     *
     * @param exceptionType 基础异常
     */
    public void setStatus(BaseException exceptionType) {
        this.code = exceptionType.getCode();
        this.message = exceptionType.getMessage();
    }

    /**
     * 设置状态
     *
     * @param exceptionType 基础异常
     * @param params        格式化信息参数
     */
    public void setStatus(BaseException exceptionType, Object... params) {
        this.code = exceptionType.getCode();
        this.message = String.format(exceptionType.getMessage(), params);
    }

    /**
     * 错误
     *
     * @param code    状态码
     * @param message 信息
     * @return 相应模型
     */
    public static<T> Response<T> error(int code, String message) {
        return new Response<>(code, message, null);
    }

    /**
     * 错误
     *
     * @param codeEnum 状态枚举类
     * @return 相应模型
     */
    public static<T> Response<T> error(CodeEnum codeEnum, Object... params) {
        return error(codeEnum.getCode(), String.format(codeEnum.getMessage(), params));
    }

    /**
     * 错误
     *
     * @param exceptionType 基础异常
     * @param params        格式化信息参数
     */
    public static<T> Response<T>  error(BaseException exceptionType, Object... params) {
        return error(exceptionType.getCode(), String.format(exceptionType.getMessage(), params));
    }

    /**
     * 成功
     *
     * @return 相应模型
     */
    public static<T> Response<T> success() {
        return success(null);
    }

    /**
     * 成功
     *
     * @param data 数据
     * @return 相应模型
     */
    public static<T> Response<T> success(T data) {
        return new Response<>(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMessage(), data);
    }

}
