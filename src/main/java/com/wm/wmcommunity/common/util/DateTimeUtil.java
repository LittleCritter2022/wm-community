package com.wm.wmcommunity.common.util;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Objects;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> DateTimeUtil
 * Product:
 * Creator: Gerry(0120)
 * Date Created: 2022/7/28
 * Description:
 * -------------------------------------------------------------------------------
 * Modification History
 * DATE                       Name                  Description
 * -------------------------------------------------------------------------------
 * 2022/7/28                 Gerry(0120)                 日期时间工具类
 * -------------------------------------------------------------------------------
 *
 * @author Gerry(0120)
 */
public class DateTimeUtil {
    private static final ZoneId DEFAULT_ZONE = ZoneId.of("Asia/Shanghai");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            .withZone(DEFAULT_ZONE);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(DEFAULT_ZONE);

    /**
     * 构造方法
     */
    private DateTimeUtil() {
    }

    /**
     * 获取当前的日期和时间，以 {@code Date} 类型返回，精确至毫秒
     *
     * @return 以 {@code Date} 类型表示的日期和时间
     */
    public static Date nowDateTime() {
        return Date.from(getInstant());
    }

    /**
     * 获取当前的日期和时间，以 {@code String} 类型返回，格式：yyyy-MM-dd HH:mm:ss
     *
     * @return 以 {@code String} 类型表示的日期和时间
     */
    public static String nowDateTimeStr() {
        return DATE_TIME_FORMATTER.format(getInstant());
    }

    /**
     * 获取当前的日期，以 {@code Date} 类型返回，截取至日
     *
     * @return 以 {@code Date} 类型表示的日期
     */
    public static Date nowDate() {
        return Date.from(getInstant().truncatedTo(ChronoUnit.DAYS).minus(8, ChronoUnit.HOURS));
    }

    /**
     * 获取当前的日期，以 {@code String} 类型返回，格式：yyyy-MM-dd
     *
     * @return 以 {@code String} 类型表示的日期
     */
    public static String nowDateStr() {
        return DATE_FORMATTER.format(getInstant().truncatedTo(ChronoUnit.DAYS).minus(8, ChronoUnit.HOURS));
    }

    /**
     * 获取当前毫秒级时间戳
     *
     * @return {@code long} 类型的毫秒级时间戳
     */
    public static long nowMilli() {
        return getInstant().toEpochMilli();
    }

    /**
     * 获取当前秒级时间戳
     *
     * @return {@code long} 类型的秒级时间戳
     */
    public static long nowSecond() {
        return getInstant().getEpochSecond();
    }

    /**
     * 毫秒级时间戳转换为字符串，格式：yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp 待转换的时间戳
     * @return yyyy-MM-dd HH:mm:ss 格式的日期时间字符串
     */
    public static String milliToString(long timestamp) {
        return DATE_TIME_FORMATTER.format(Instant.ofEpochMilli(timestamp));
    }

    /**
     * 秒级时间戳转换为字符串，格式：yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp 待转换的时间戳
     * @return yyyy-MM-dd HH:mm:ss 格式的日期时间字符串
     */
    public static String secondToString(long timestamp) {
        return DATE_TIME_FORMATTER.format(Instant.ofEpochSecond(timestamp));
    }

    /**
     * {@code Date} 转换为字符串，格式：yyyy-MM-dd HH:mm:ss
     *
     * @param date 待转换的 {@code Date}
     * @return yyyy-MM-dd HH:mm:ss 格式的日期时间字符串
     */
    public static String dateToString(Date date) {
        Objects.requireNonNull(date, "param [date] must not be null");
        return DATE_TIME_FORMATTER.format(date.toInstant());
    }

    /**
     * 字符串转换为 {@code Date}，格式：yyyy-MM-dd HH:mm:ss
     *
     * @param str 待转换的字符串
     * @return 由参数转换得到的 {@code Date}
     */
    public static Date stringToDate(String str) {
        Objects.requireNonNull(str, "param [str] must not be null");
        LocalDateTime localDateTime = LocalDateTime.parse(str, DATE_FORMATTER);
        return Date.from(localDateTime.atZone(DEFAULT_ZONE).toInstant());
    }

    /**
     * 字符串转换为 {@code Date}，格式：yyyy-MM-dd HH:mm:ss
     *
     * @param str 待转换的字符串
     * @return 由参数转换得到的 {@code Date}
     */
    public static Date stringToDateTime(String str) {
        Objects.requireNonNull(str, "param [str] must not be null");
        LocalDateTime localDateTime = LocalDateTime.parse(str, DATE_TIME_FORMATTER);
        return Date.from(localDateTime.atZone(DEFAULT_ZONE).toInstant());
    }

    /**
     * 获取 {@code date} 所在月份的第一天
     * <p>
     * 示例：{@code getFirstDayOfMonth('Tue Nov 16 09:25:20 CST 2021')} == {@code 'Mon Nov 01 00:00:00 CST 2021'}
     * </p>
     *
     * @return {@code date} 所在月份的第一天
     */
    public static Date getFirstDayOfMonth(Date date) {
        LocalDateTime localDateTime = date.toInstant().atZone(DEFAULT_ZONE).toLocalDateTime();
        localDateTime = localDateTime.with(TemporalAdjusters.firstDayOfMonth())
                .withHour(0).withMinute(0).withSecond(0);
        return Date.from(localDateTime.atZone(DEFAULT_ZONE).toInstant());
    }

    /**
     * 获取 {@code date} 所在月份的最后一天
     * <p>
     * 示例：{@code getLastDayOfMonth('Tue Nov 16 09:25:20 CST 2021')} == {@code 'Tue Nov 30 23:59:59 CST 2021'}
     * </p>
     *
     * @return {@code date} 所在月份的最后一天
     */
    public static Date getLastDayOfMonth(Date date) {
        LocalDateTime localDateTime = date.toInstant().atZone(DEFAULT_ZONE).toLocalDateTime()
                .withHour(23).withMinute(59).withSecond(59);
        localDateTime = localDateTime.with(TemporalAdjusters.lastDayOfMonth());
        return Date.from(localDateTime.atZone(DEFAULT_ZONE).toInstant());
    }

    private static Instant getInstant() {
        return Clock.system(DEFAULT_ZONE).instant();
    }
}
