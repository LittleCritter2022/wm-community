package com.wm.wmcommunity.common.annotations;

import java.lang.annotation.*;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> LoginAnnotation
 * Product:WMing
 * Creator: Gerry(0120)
 * Date Created: 2022/7/29
 * Description:登录拦截自定义注解
 * -------------------------------------------------------------------------------
 * Modification History
 * DATE                       Name                  Description
 * -------------------------------------------------------------------------------
 * 2022/7/29                 Gerry(0120)                 Create
 * -------------------------------------------------------------------------------
 *
 * @author Gerry(0120)
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginAnnotation {

    /**
     * 自定义注解的内容语法格式： 数据类型 属性名() default 默认值  如 String name() default "";
     * 默认值： 字符串一般为 ""   数组一般为 {}
     */

    String module() default "";

    String operator() default "";

}
