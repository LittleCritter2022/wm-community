package com.wm.wmcommunity.common.exceptions;

import com.wm.wmcommunity.common.constant.CodeEnum;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> FtpException
 * Product:
 * Creator: Gerry(0120)
 * Date Created: 2022/7/28
 * Description:
 * -------------------------------------------------------------------------------
 * Modification History
 * DATE                       Name                  Description
 * -------------------------------------------------------------------------------
 * 2022/7/28                 Gerry(0120)                 ftp 异常类
 * -------------------------------------------------------------------------------
 *
 * @author Gerry(0120)
 */
public class FtpException extends BaseException {
    /**
     * 构造方法
     *
     * @param msg 信息
     */
    public FtpException(String msg) {
        super(CodeEnum.ERROR_FTP.getCode(), msg);
    }

    /**
     * 构造方法
     *
     * @param msg       信息
     * @param throwable 异常
     */
    public FtpException(String msg, Throwable throwable) {
        super(CodeEnum.ERROR_FTP.getCode(), msg, throwable);
    }
}
