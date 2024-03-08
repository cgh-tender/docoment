package cn.com.cgh.romantic.constant;


import java.time.format.DateTimeFormatter;

/**
 * @author cgh
 */
public class RomanticConstant {
    public static final String ONE_RULE = "ONE";
    public static final String X_REAL_IP = "X-Real-IP";
    public static final String THREAD_LOCAL_LOG_ID = "THREAD-LOCAL-LOG-ID";
    public static final String USER_AGENT = "User-Agent";
    public static final String JWT_TOKEN_HEADER = "Authorization";
    public static final String REDIS_CACHE_MANAGER_NAME = "empRedisCacheManager";

    public static final String MASTER = "master";
    public static final String SLAVE1 = "slave1";
    public static final String SLAVE2 = "slave2";
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

    public static final String UUID = "uuid";
}
