package com.wm.wmcommunity.common.util;

import com.alibaba.fastjson.JSONObject;
import com.wm.wmcommunity.common.constant.SymbolConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> CommunityUtil
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
public class CommunityUtil {

    /**
     * 生成随机字符串
     *
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * MD5加密
     *
     * @param key 需要加密的key
     * @return 加密后的key
     */
    public static String md5(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 获取map的合理的初始化大小
     *
     * @param size 数据大小
     * @return map的初始化大小
     */
    public static int getMapInitialCapacity(int size) {
        return (int) (size / 0.75F + 1.0F);
    }

    /**
     * 创建新地址
     *
     * @param url  地址
     * @param path 路径
     * @return 新地址
     */
    public static String createUrl(String url, String path) {
        if (!SymbolConst.SLASH.equals(url.substring(url.length() - 1))) {
            url += SymbolConst.SLASH;
        }
        if (SymbolConst.SLASH.equals(path.substring(0, 1))) {
            path = path.substring(1);
        }
        return url + path;
    }

    /**
     * 转化成json类型数据
     *
     * @param code 状态码
     * @param msg  信息
     * @param map  其他参数map
     * @return json
     */
    public static String getJsonString(int code, String msg, Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        if (null == map) {
            for (String key : map.keySet()) {
                jsonObject.put(key, map.get(key));
            }
        }
        return jsonObject.toJSONString();
    }

    public static String getJsonString(int code, String msg) {
        return getJsonString(code, msg, null);
    }

    public static String getJsonString(int code) {
        return getJsonString(code, null, null);
    }

    //
}
