package com.wm.wmcommunity.common.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.util.validation.metadata.DatabaseException;
import org.springframework.http.HttpMethod;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Map;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> HttpUtil
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
@Slf4j
public class HttpUtil {
    private static final int PORT = 52010;
    private static final boolean PROXY = false;
    private static final String HTTPS = "https";
    private static final String HOSTNAME = "127.0.0.1";
    private static final Proxy.Type PROXY_TYPE = Proxy.Type.HTTP;

    /**
     * 发送GET请求
     * 可指定覆盖请求头
     *
     * @param protocol 请求协议
     * @param host     请求ip
     * @param port     请求端口
     * @param url      请求地址
     * @param param    请求参数
     * @param headers  请求头
     * @return
     */
    public static String sendGet(String protocol,
                                 String host,
                                 Integer port,
                                 String url,
                                 JSONObject param,
                                 JSONObject headers) throws DatabaseException {

        String stringParam = "";
        if (null == param) {
            param = new JSONObject();
        }
        for (Map.Entry<String, Object> e : param.entrySet()) {
            try {
                stringParam += (e.getKey() + "=" + URLEncoder.encode(e.getValue().toString(), StandardCharsets.UTF_8.name()) + "&");
            } catch (UnsupportedEncodingException ue) {
                log.error(ue.getMessage(), ue);
            }
        }

        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        String urlNameString =
                String.format("%s://%s:%s%s%s", protocol, host, port, url,
                        stringParam.length() > 0 ? ("?" + stringParam) : stringParam);
        try {
            if (HTTPS.equals(protocol)) {
                setHttps();
            }
            URL realUrl = new URL(urlNameString);
            URLConnection connection = createService(realUrl);
            connection.setRequestProperty("Content-type", "application/json;charset=utf-8");
            if (null != headers && !headers.isEmpty()) {
                headers.forEach((k, v) -> connection.setRequestProperty(k, v.toString()));
            }

            connection.setDoInput(true);
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DatabaseException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return formatRespStr(urlNameString, result.toString());
    }


    /**
     * 发送POST请求
     * 可指定覆盖请求头，可同时传递params与body
     *
     * @param protocol 请求协议
     * @param host     请求ip
     * @param port     请求端口
     * @param url      请求地址
     * @param params   请求参数
     * @param body     请求体
     * @param headers  请求头
     * @return
     */
    public static String sendPost(String protocol,
                                  String host,
                                  Integer port,
                                  String url,
                                  JSONObject params,
                                  JSONObject body,
                                  JSONObject headers) throws DatabaseException {
        // 用来接受返回值
        StringBuilder result = new StringBuilder();
        StringBuilder urlStr = new StringBuilder();
        try {
            urlStr.append(protocol).append("://").append(host).append(":").append(port).append(url);
            // 处理params
            StringBuilder paramsStr = new StringBuilder();
            if (null == params) {
                params = new JSONObject();
            }
            params.forEach((k, v) -> {
                try {
                    paramsStr.append(k).append("=").append(URLEncoder.encode(v.toString(), "utf-8")).append("&");
                } catch (UnsupportedEncodingException ue) {
                    log.error(ue.getMessage(), ue);
                }
            });
            if (paramsStr.length() > 0) {
                urlStr.append("?").append(paramsStr);
            }

            if (HTTPS.equals(protocol)) {
                setHttps();
            }
            URL uri = new URL(urlStr.toString());
            // 创建的http连接
            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            conn.setRequestMethod(HttpMethod.POST.name());
            conn.setRequestProperty("Content-type", "application/json;charset=utf-8");
            // 处理headers
            if (null != headers && !headers.isEmpty()) {
                headers.forEach((k, v) -> conn.setRequestProperty(k, v.toString()));
            }
            // post发送数据时必须设置为true，否则对方无法接收到数据
            conn.setDoOutput(true);

            conn.connect();
            conn.getOutputStream().write(JSONObject.toJSONString(body, SerializerFeature.WriteMapNullValue).getBytes(StandardCharsets.UTF_8));
            conn.getOutputStream().flush();
            conn.getOutputStream().close();
            InputStream in;
            if (conn.getResponseCode() == 200) {
                in = conn.getInputStream();
            } else {
                in = conn.getErrorStream();
                log.error("[POST][{}]返回异常code={}", urlStr, conn.getResponseCode());
            }
            // 接受连接收到的参数
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            bufferedReader.close();
            conn.disconnect();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DatabaseException(e);
        }

        return formatRespStr(urlStr.toString(), result.toString());
    }


    private static HttpURLConnection createService(URL url) throws IOException {
        HttpURLConnection connection;
        if (PROXY) {
            // 创建代理服务器
            InetSocketAddress addr = new InetSocketAddress(HOSTNAME, PORT);
            // http 代理
            Proxy proxy = new Proxy(PROXY_TYPE, addr);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection(proxy);
        } else {
            connection = (HttpURLConnection) url.openConnection();
        }
        return connection;
    }


    private static void setHttps() throws KeyManagementException, NoSuchProviderException, NoSuchAlgorithmException {
        // 第一个参数为协议,第二个参数为提供者(可以缺省)
        SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
        TrustManager[] tm = {new MyX509TrustManager()};
        sslcontext.init(null, tm, new SecureRandom());
        HostnameVerifier ignoreHostnameVerifier = (s, sslsession) -> true;
        HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
    }


    /**
     * 格式化返回值
     * 只放行JSONObject格式数据
     *
     * @param url
     * @param responseStr
     * @return
     */
    private static String formatRespStr(String url, String responseStr) {
        try {
            JSONObject.parseObject(responseStr);
        } catch (Exception e) {
            // 此时可能是报错返回值，例如：500页面、401页面
            log.error("接口：{}，返回值：{}，报错：{}", url, responseStr, e.getMessage());
            return "接口返回值解析异常，非Json格式";
        }
        return responseStr;
    }

}
