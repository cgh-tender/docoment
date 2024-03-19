package cn.com.cgh.romantic.util;

import jakarta.validation.constraints.NotNull;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * parserInstant 将 LocalDateTime 转换为 Instant。
 * dateFormat 根据指定的日期格式将LocalDateTime格式化为字符串。
 * addSecond 向指定的LocalDateTime日期时间添加秒数。
 * addMinute 向指定的LocalDateTime日期时间添加分钟数。
 * addHour 向指定的LocalDateTime日期时间添加小时数。
 * addDay 向指定的LocalDateTime日期时间添加天数。
 * addMonth 向指定的LocalDateTime日期时间添加月
 * addYear 向指定的LocalDateTime日期时间添加年数。
 * dateToWeek 将指定的LocalDateTime日期时间转换为星期几。
 * getStartTimeOfDay 获取指定日期时间的开始时间，即该日期时间的0时0分0秒。
 * getEndTimeOfDay 获取指定日期时间的结束时间，即该日期时间的23时59分59秒。
 * betweenStartAndEnd 判断指定的LocalDateTime日期时间是否在两个指定的LocalDateTime日期时间之间。
 * @author cgh
 */
public class DateUtil {
    /**
     * 日期格式 yyyy-MM
     */
    public static final DateTimeFormatter LOCAL_DATE_YYYY_MM = DateTimeFormatter.ofPattern("yyyy-MM");
    /**
     * 日期格式 yyyy-MM-dd
     */
    public static final DateTimeFormatter LOCAL_DATE_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**
     * 日期格式 yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormatter LOCAL_DATE_TIME_YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * 日期格式 yyyy-MM-ddTHH:mm:ss
     */
    public static final DateTimeFormatter LOCAL_DATE_TIME_YYYY_MM_DD_T_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss");
    /**
     * 日期格式 yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final DateTimeFormatter LOCAL_DATE_TIME_YYYY_MM_DD_HH_MM_SS_SSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    /**
     * 日期格式 yyyy-MM-ddTHH:mm:ss.SSS
     */
    public static final DateTimeFormatter LOCAL_DATE_TIME_YYYY_MM_DD_T_HH_MM_SS_SSS = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss.SSS");
    /**
     * 日期格式 yyyyMMdd
     */
    public static final DateTimeFormatter LOCAL_DATE_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
    /**
     * 日期格式 yyyyMMddHHmm
     */
    public static final DateTimeFormatter LOCAL_DATE_TIME_YYYYMMDDHHMM = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    /**
     * 日期格式 yyyyMMddHHmmss
     */
    public static final DateTimeFormatter LOCAL_DATE_TIME_YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    /**
     * 日期格式 yyyyMMddHHmmssSSS
     */
    public static final DateTimeFormatter LOCAL_DATE_TIME_YYYYMMDDHHMMSSSSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");


    /**
     * 将 LocalDateTime 转换为 Instant。
     *
     * @param date 需要转换的 LocalDateTime 对象，不能为空。
     * @return 转换后的 Instant 对象，表示在 UTC 时间基准上的绝对时间。
     */
    public static Instant parserInstant(@NotNull LocalDateTime date) {
        // 将 LocalDateTime 转换为 Instant，使用北京时区（GMT+8）作为转换基准
        return parserInstant(date.atZone(ZoneId.systemDefault()));
    }

    /**
     * 将 ZonedDateTime 对象转换为 Instant 对象。
     *
     * @param date 需要转换的 ZonedDateTime 对象，不能为空。
     * @return 转换后的 Instant 对象，表示 UTC 时间线上的一个不可变的时刻。
     */
    public static Instant parserInstant(@NotNull ZonedDateTime date) {
        // 将 ZonedDateTime 转换为 Instant
        return date.toInstant();
    }


    /**
     * 根据指定的日期格式将LocalDateTime格式化为字符串。
     *
     * @param date       需要格式化的LocalDateTime日期时间对象。
     * @param dateFormat 指定的日期格式字符串，例如"yyyy-MM-dd HH:mm:ss"。
     * @return 格式化后的日期时间字符串。
     */
    public static String dateFormat(@NotNull LocalDateTime date, String dateFormat) {
        // 使用指定的日期格式创建DateTimeFormatter对象
        return dateFormat(date,DateTimeFormatter.ofPattern(dateFormat));
    }
    public static String dateFormat(@NotNull LocalDateTime date, DateTimeFormatter dateTimeFormatter) {
        // 使用指定的日期格式创建DateTimeFormatter对象
        return date.format(dateTimeFormatter);
    }

    /**
     * 向指定的LocalDateTime日期时间添加秒数。
     *
     * @param date   指定的日期时间，不可为null。
     * @param second 要添加的秒数，可以为负数以表示减去秒数。
     * @return 返回添加秒数后的LocalDateTime对象。
     */
    public static LocalDateTime addSecond(@NotNull LocalDateTime date, int second) {
        // 使用plusSeconds方法添加秒数
        return date.plusSeconds(second);
    }

    /**
     * 向指定的LocalDateTime添加分钟。
     *
     * @param date   需要添加分钟的LocalDateTime日期时间对象，不能为空。
     * @param minute 需要添加的分钟数。
     * @return 返回添加分钟后的新LocalDateTime对象。
     */
    public static LocalDateTime addMinute(@NotNull LocalDateTime date, int minute) {
        // 使用plusMinutes方法添加指定的分钟数
        return date.plusMinutes(minute);
    }

    /**
     * 向指定的 LocalDateTime 对象增加指定小时数。
     *
     * @param date 需要增加小时数的 LocalDateTime 对象，不能为空。
     * @param hour 需要增加的小时数。
     * @return 增加指定小时数后的 LocalDateTime 对象。
     */
    public static LocalDateTime addHour(@NotNull LocalDateTime date, int hour) {
        // 使用 plusHours 方法增加指定的小时数
        return date.plusHours(hour);
    }

    /**
     * 向指定的LocalDateTime日期添加天数。
     *
     * @param date 需要进行操作的日期时间，不能为空。
     * @param day  需要添加的天数，可以为负数表示减去天数。
     * @return 返回添加或减去天数后的LocalDateTime对象。
     */
    public static LocalDateTime addDay(@NotNull LocalDateTime date, int day) {
        // 使用plusDays方法添加天数
        return date.plusDays(day);
    }

    /**
     * 向指定的LocalDateTime日期添加指定的月数。
     *
     * @param date  需要进行操作的LocalDateTime日期，不能为空。
     * @param month 需要添加的月数，可以为负数以表示减去月数。
     * @return 返回操作后的LocalDateTime日期对象。
     */
    public static LocalDateTime addMonth(@NotNull LocalDateTime date, int month) {
        // 使用plusMonths方法添加月数
        return date.plusMonths(month);
    }

    /**
     * 向指定的LocalDateTime日期添加年份。
     *
     * @param date 需要进行操作的日期时间，不能为空。
     * @param year 需要添加的年份数量。
     * @return 返回添加年份后的LocalDateTime对象。
     */
    public static LocalDateTime addYear(@NotNull LocalDateTime date, int year) {
        // 使用plusYears方法添加年份
        return date.plusYears(year);
    }

    /**
     * 周日到周六的中文星期表示数组
     */
    public static final String[] WEEK_DAY_OF_CHINESE = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    /**
     * 将给定的日期转换为对应的中文星期几。
     *
     * @param date 输入的日期，类型为LocalDate。
     * @return 对应的中文星期几，返回一个字符串。
     */
    public static String dateToWeek(LocalDate date) {
        // 获取日期对应的星期几
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        // 根据星期几的值（1-7），返回对应的中文星期几
        return WEEK_DAY_OF_CHINESE[dayOfWeek.getValue() % 7];
    }

    /**
     * 获取指定日期的当日开始时间。
     *
     * @param date 指
     * @return 返回指定日期的当日开始时间，即00:00:00。
     */
    public static LocalDateTime getStartTimeOfDay(@NotNull LocalDateTime date) {
        // 将日期时间转换为日期，并获取该日期的开始时间
        return date.toLocalDate().atStartOfDay();
    }

    /**
     * 获取指定日期的当天结束时间。
     *
     * @param date 不能为空，表示需要获取结束时间的日期时间信息。
     * @return 返回指定日期的当天23:59:59.999999999，即一天的结束时间。
     */
    public static LocalDateTime getEndTimeOfDay(@NotNull LocalDateTime date) {
        // 将日期部分提取出来，然后与一天中的最大时间（23:59:59.999999999）相结合，形成当天的结束时间。
        return date.toLocalDate().atTime(LocalTime.MAX);
    }

    /**
     * 判断当前时间是否在指定的开始时间和结束时间之间。
     *
     * @param nowTime   当前时间，类型为Instant。
     * @param beginTime 开始时间，类型为Instant。
     * @param endTime   结束时间，类型为Instant。
     * @return 返回一个Boolean值，如果当前时间在开始时间和结束时间之间（不包括开始时间和结束时间本身），则返回true；否则返回false。
     */
    public static Boolean betweenStartAndEnd(@NotNull Instant nowTime, @NotNull Instant beginTime, @NotNull Instant endTime) {
        // 判断当前时间是否在开始时间之后且在结束时间之前
        return nowTime.isAfter(beginTime) && nowTime.isBefore(endTime);
    }
}
