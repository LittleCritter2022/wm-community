package com.wm.wmcommunity.common.exceptions;

import com.wm.wmcommunity.common.constant.CodeEnum;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> RuntimeSftpException
 * Product:
 * Creator: Gerry(0120)
 * Date Created: 2022/7/28
 * Description:
 * -------------------------------------------------------------------------------
 * Modification History
 * DATE                       Name                  Description
 * -------------------------------------------------------------------------------
 * 2022/7/28                 Gerry(0120)                 SFTP 异常类
 * -------------------------------------------------------------------------------
 *
 * @author Gerry(0120)
 */
public class RuntimeSftpException extends BaseException {

    /**
     * 构造方法
     *
     * @param msg 信息
     */
    public RuntimeSftpException(String msg) {
        super(CodeEnum.ERROR_SFTP.getCode(), msg);
    }

    /**
     * 构造方法
     *
     * @param msg       信息
     * @param throwable 异常
     */
    public RuntimeSftpException(String msg, Throwable throwable) {
        super(CodeEnum.ERROR_SFTP.getCode(), msg, throwable);
    }
}
