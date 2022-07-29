package com.wm.wmcommunity.common.util;

import com.jcraft.jsch.*;
import com.wm.wmcommunity.common.exceptions.RuntimeSftpException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> SftpUtil
 * Product:
 * Creator: Gerry(0120)
 * Date Created: 2022/7/28
 * Description:
 * -------------------------------------------------------------------------------
 * Modification History
 * DATE                       Name                  Description
 * -------------------------------------------------------------------------------
 * 2022/7/28                 Gerry(0120)                 SFTP 工具类
 * -------------------------------------------------------------------------------
 *
 * @author Gerry(0120)
 */
public class SftpUtil {

    private static final int BATCH_READ_SIZE = 4 * 1024 * 1024;
    private static final String SLASH = "/";

    /**
     * 构造方法
     */
    private SftpUtil() {
    }

    /**
     * 创建ChannelSftp，在连接成功后返回
     *
     * @param host          IP 或域名
     * @param port          端口
     * @param username      用户名
     * @param password      密码
     * @param socketTimeout 超时时间（毫秒）
     * @return ChannelSftp
     * @throws JSchException on error
     */
    public static ChannelSftp buildChannel(String host, int port, String username, String password,
                                           int socketTimeout) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setPassword(password);
        session.setTimeout(socketTimeout);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshConfig.put("PreferredAuthentications", "password,gssapi-with-mic,publickey,keyboard-interactive");
        session.setConfig(sshConfig);
        session.connect();
        Channel channel = session.openChannel("sftp");
        channel.connect();
        return (ChannelSftp) channel;
    }

    /**
     * 关闭 SFTP 连接
     *
     * @param channel 待关闭的 ChannelSftp
     */
    public static void disconnectChannel(ChannelSftp channel) {
        if (channel.isConnected()) {
            channel.disconnect();
        }
    }

    /**
     * 获取目录内所有的文件名
     *
     * @param channel ChannelSftp
     * @param path    目录
     * @return 文件名列表
     * @throws SftpException on error
     */
    public static List<String> getFileNameList(ChannelSftp channel, String path) throws SftpException {
        List<String> list = new ArrayList<>();
        ChannelSftp.LsEntrySelector selector = entry -> {
            if (entry.getAttrs().isReg()) {
                list.add(entry.getFilename());
            }
            return ChannelSftp.LsEntrySelector.CONTINUE;
        };
        channel.ls(path, selector);
        return list;
    }

    /**
     * 获取文件内容
     * 可以直接调用 channel.get(String) 方法获取输入流进行操作，例如：try (in = channel.get(myPath)) {process(in);}
     *
     * @param channel  ChannelSftp
     * @param path     目录
     * @param fileName 文件名
     * @return byte[]
     */
    public static byte[] getFileContent(ChannelSftp channel, String path, String fileName) {
        String filePath = Objects.isNull(path) ? SLASH : path;
        if (!filePath.endsWith(SLASH)) {
            filePath += SLASH;
        }
        filePath += fileName;

        try (InputStream input = channel.get(filePath);
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[BATCH_READ_SIZE];
            int len;
            while ((len = input.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
            return output.toByteArray();
        } catch (SftpException | IOException e) {
            throw new RuntimeSftpException("[getFileContent]方法发生异常，无法获取文件，[filePath]=" + filePath, e);
        }
    }

}
