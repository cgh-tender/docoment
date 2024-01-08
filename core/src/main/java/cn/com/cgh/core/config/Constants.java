package cn.com.cgh.core.config;

import java.time.format.DateTimeFormatter;

public class Constants {

    public static final DateTimeFormatter yyyy_MM = DateTimeFormatter.ofPattern("yyyy-MM");
    public static final DateTimeFormatter yyyy_MM_dd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter yyyy_MM_dd_HH_mm_ss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter yyyy_MM_dd_T_HH_mm_ss = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss");
    public static final DateTimeFormatter yyyy_MM_dd_HH_mm_ss_SSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    public static final DateTimeFormatter yyyy_MM_dd_T_HH_mm_ss_SSS = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss.SSS");
    public static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter yyyyMMddHHmm = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    public static final DateTimeFormatter yyyyMMddHHmmss = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter yyyyMMddHHmmssSSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    public static final String REDIS_CACHE_MANAGER_NAME = "empRedisCacheManager";
}
