package com.wm.wmcommunity.common.config;

import com.wm.wmcommunity.common.annotations.NotIncludeSwagger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> SwaggerConfig
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
@Order(1)
@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Value("${swagger.enable}")
    private Boolean enable;

    @Value("${swagger.title}")
    private String title;

    @Value("${swagger.description}")
    private String description;

    @Value("${swagger.version}")
    private String version;

    @Value("${swagger.controller-locations}")
    private String controllerLocation;

    /**
     * ??????API??????
     * apiInfo() ??????API????????????
     * ??????select()??????????????????ApiSelectorBuilder??????,?????????????????????????????????Swagger????????????
     * ????????????????????????????????????????????????????????????API????????????
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(this.enable)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(this.controllerLocation))
//                .apis(RequestHandlerSelectors.withMethodAnnotation(NotIncludeSwagger.class))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * ?????????API??????????????????????????????????????????????????????????????????
     * ???????????????http://??????????????????/swagger-ui.html
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(this.title)
                .description(this.description)
                .version(this.version)
                .build();
    }

    /**
     * ?????????????????????????????????
     * addResourceHandler??????????????????????????????
     * addResourceLocations???????????????????????????
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[]{"swagger-ui.html"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/"});
        registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"});
    }
}
