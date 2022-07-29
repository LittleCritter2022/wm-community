package com.wm.wmcommunity.common.util;

import com.csvreader.CsvReader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> CsvUtil
 * Product:
 * Creator: Gerry(0120)
 * Date Created: 2022/7/28
 * Description:
 * -------------------------------------------------------------------------------
 * Modification History
 * DATE                       Name                  Description
 * -------------------------------------------------------------------------------
 * 2022/7/28                 Gerry(0120)                 CSV 工具类
 * -------------------------------------------------------------------------------
 *
 * @author Gerry(0120)
 */
public class CsvUtil {
    /**
     * 构造方法
     */
    private CsvUtil() {
    }

    /**
     * 读取 .csv 文件，默认编码 UTF-8
     *
     * @param inputStream 输入流
     */
    public static List<List<String>> read(InputStream inputStream) throws IOException {
        return read(inputStream, StandardCharsets.UTF_8);
    }

    /**
     * 以指定的字符编码读取 .csv 文件
     *
     * @param inputStream 输入流
     * @param charset     编码格式
     */
    public static List<List<String>> read(InputStream inputStream, Charset charset) throws IOException {
        CsvReader csvReader = new CsvReader(inputStream, charset);
        List<List<String>> data = new ArrayList<>();
        try {
            while (csvReader.readRecord()) {
                String[] column = csvReader.getValues();
                data.add(Arrays.asList(column));
            }
        } finally {
            csvReader.close();
        }
        return data;
    }
}
