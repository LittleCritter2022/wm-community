package com.wm.wmcommunity.common.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> JsonConfig
 * Product:
 * Creator: Gerry(0120)
 * Date Created: 2022/7/22
 * Description:
 * -------------------------------------------------------------------------------
 * Modification History
 * DATE                       Name                  Description
 * -------------------------------------------------------------------------------
 * 2022/7/22                 Gerry(0120)                 Create
 * -------------------------------------------------------------------------------
 *
 * @author Gerry(0120)
 */
@ConditionalOnExpression(value = "${spring.http.fastjson.enable:true}")
@Order(1)
@Slf4j
@EnableWebMvc
@Configuration
public class JsonConfig implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonConfig.class);

    /**
     * 消息内容转换配置
     * 配置fastjson返回json转换
     *
     * @param converters 转换器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        LOGGER.info("use jsonConfig controller");
        //创建fastjson消息转换器
        FastJsonHttpMessageConverter fastConerter = new FastJsonHttpMessageConverter();
        //创建配置类
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        //修改配置返回内容的过滤
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty
        );
        fastConerter.setFastJsonConfig(fastJsonConfig);
        //将fasteJson添加到视图消息转换器列表内
        converters.add(fastConerter);
    }
}
