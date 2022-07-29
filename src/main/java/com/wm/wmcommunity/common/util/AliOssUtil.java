package com.wm.wmcommunity.common.util;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectRequest;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> AliOssUtil
 * Product:
 * Creator: Gerry(0120)
 * Date Created: 2022/7/28
 * Description:
 * -------------------------------------------------------------------------------
 * Modification History
 * DATE                       Name                  Description
 * -------------------------------------------------------------------------------
 * 2022/7/28                 Gerry(0120)                 阿里云 - 对象存储 OSS 操作类
 * -------------------------------------------------------------------------------
 *
 * @author Gerry(0120)
 */
public class AliOssUtil {

    /**
     * 构造方法
     */
    private AliOssUtil() {
    }

    /**
     * 创建 OSSClient
     *
     * @param endpoint        endpoint
     * @param accessKeyId     accessKeyId
     * @param accessKeySecret accessKeySecret
     * @return OSSClient
     */
    public static OSSClient buildOssClient(String endpoint, String accessKeyId, String accessKeySecret) {
        return buildOssClient(endpoint, accessKeyId, accessKeySecret, new ClientBuilderConfiguration());
    }

    /**
     * 创建 OSSClient
     *
     * @param endpoint            endpoint
     * @param accessKeyId         accessKeyId
     * @param accessKeySecret     accessKeySecret
     * @param clientConfiguration OSSClient 配置信息，可配置超时时间、最大连接数、代理等配置项
     * @return OSSClient
     */
    public static OSSClient buildOssClient(String endpoint, String accessKeyId, String accessKeySecret,
                                           ClientConfiguration clientConfiguration) {
        return new OSSClient(endpoint, new DefaultCredentialProvider(accessKeyId, accessKeySecret),
                clientConfiguration);
    }

    /**
     * 以文件的形式上传文件，若 fileName 中指定的目录不存在则自动创建
     *
     * @param client     OSSClient
     * @param bucketName bucket
     * @param fileName   文件名，可以包含目录信息，例：newFile、folder/newFile 都是合法文件名
     *                   newFile：表示在根目录下的名为 newFile 的文件
     *                   folder/newFile：表示在根目录下的 folder 文件夹中的名为 newFile 的文件
     * @param file       File
     * @return 文件 URL
     */
    public static String uploadFile(OSSClient client, String bucketName, String fileName, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
        client.putObject(putObjectRequest);
        return getUrl(client, bucketName, fileName);
    }

    /**
     * 以流的形式上传文件，若 fileName 中指定的目录不存在则自动创建
     *
     * @param client      OSSClient
     * @param bucketName  bucket
     * @param fileName    文件名，可以包含目录信息，例：newFile、folder/newFile 都是合法文件名
     *                    newFile：表示在根目录下的名为 newFile 的文件
     *                    folder/newFile：表示在根目录下的 folder 文件夹中的名为 newFile 的文件
     * @param inputStream InputStream
     * @return 文件 URL
     */
    public static String uploadFile(OSSClient client, String bucketName, String fileName, InputStream inputStream) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream);
        client.putObject(putObjectRequest);
        return getUrl(client, bucketName, fileName);
    }

    /**
     * 获取 bucket 所有文件
     *
     * @param client     OSSClient
     * @param bucketName bucket
     * @return 文件列表
     */
    public static List<String> listObjects(OSSClient client, String bucketName) {
        return listObjects(client, bucketName, null);
    }

    /**
     * 获取 bucket 匹配指定前缀的文件
     *
     * @param client     OSSClient
     * @param bucketName bucket
     * @param prefix     前缀
     * @return 文件列表
     */
    public static List<String> listObjects(OSSClient client, String bucketName, String prefix) {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        listObjectsRequest.setPrefix(prefix);
        ObjectListing objectListing = client.listObjects(listObjectsRequest);
        return objectListing.getObjectSummaries().stream().map(OSSObjectSummary::getKey).collect(Collectors.toList());
    }

    /**
     * 获取文件 URL
     *
     * @param client     OSSClient
     * @param bucketName bucket
     * @param fileName   文件名
     * @return 文件 URL
     */
    public static String getUrl(OSSClient client, String bucketName, String fileName) {
        URI uri = client.getEndpoint();
        return uri.getScheme() + "://" + bucketName + "." + uri.getAuthority() + "/" + fileName;
    }
}
