package com.wm.wmcommunity.common.util;

import com.wm.wmcommunity.common.exceptions.FtpException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> FtpUtil
 * Product:
 * Creator: Gerry(0120)
 * Date Created: 2022/7/28
 * Description:
 * -------------------------------------------------------------------------------
 * Modification History
 * DATE                       Name                  Description
 * -------------------------------------------------------------------------------
 * 2022/7/28                 Gerry(0120)                 FTP 工具类
 * -------------------------------------------------------------------------------
 *
 * @author Gerry(0120)
 */
public class FtpUtil {

    private static final int BATCH_READ_SIZE = 4 * 1024 * 1024;
    private static final String SLASH = "/";

    /**
     * 构造方法
     */
    private FtpUtil() {
    }

    /**
     * 创建 FTPClient，在连接成功后返回
     *
     * @param hostname       IP 或域名
     * @param port           端口
     * @param username       用户名
     * @param password       密码
     * @param connectTimeout 连接超时时间（毫秒）
     * @return FTPClient
     * @throws IOException on error
     */
    public static FTPClient buildFtpClient(String hostname, int port, String username, String password,
                                           int connectTimeout) throws IOException {
        FTPClient client = new FTPClient();
        client.setConnectTimeout(connectTimeout);
        client.setControlEncoding("UTF-8");
        client.connect(hostname, port);
        client.login(username, password);
        int replyCode = client.getReplyCode();
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            client.disconnect();
            throw new FtpException("[buildFtpClient]失败，用户名和密码可能错误，[replyCode]=" + replyCode);
        }
        client.setFileType(FTP.BINARY_FILE_TYPE);
        client.enterLocalPassiveMode();
        return client;
    }

    /**
     * 关闭 FTP 连接
     *
     * @param client 待关闭的 FTPClient
     * @throws IOException on error
     */
    public static void disconnectFtp(FTPClient client) throws IOException {
        if (client.isConnected()) {
            try {
                client.logout();
            } finally {
                client.disconnect();
            }
        }
    }

    /**
     * 获取目录内所有的文件名
     *
     * @param client FTPClient
     * @param path   目录
     * @return 文件名列表
     * @throws IOException on error
     */
    public static List<String> getFileNameList(FTPClient client, String path) throws IOException {
        FTPFile[] files = client.listFiles(path, FTPFile::isFile);
        return Arrays.stream(files).map(FTPFile::getName).collect(Collectors.toList());
    }

    /**
     * 获取文件内容
     *
     * @param client   FTPClient
     * @param path     目录
     * @param fileName 文件名
     * @return byte[]
     * @throws IOException on error
     */
    public static byte[] getFileContent(FTPClient client, String path, String fileName) throws IOException {
        String filePath = Objects.isNull(path) ? SLASH : path;
        if (!filePath.endsWith(SLASH)) {
            filePath += SLASH;
        }
        filePath += fileName;

        InputStream inputStream = null;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            inputStream = client.retrieveFileStream(filePath);
            if (Objects.isNull(inputStream)) {
                throw new FtpException("[getFileContent]方法失败，无法获取文件，[filePath]=" + filePath);
            }
            byte[] buffer = new byte[BATCH_READ_SIZE];
            int len;
            while ((len = inputStream.read(buffer, 0, BATCH_READ_SIZE)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            return byteArrayOutputStream.toByteArray();
        } finally {
            // 在 FTPClient 的方法有流返回的情况下，当流关闭之后需要调用 completePendingCommand 关闭传输过程
            // 若不关闭则下次调用会返回空值，在没有获取到流的情况下调用该方法会一直等待返回，造成程序卡死
            if (Objects.nonNull(inputStream)) {
                inputStream.close();
                client.completePendingCommand();
            }
        }
    }
}
