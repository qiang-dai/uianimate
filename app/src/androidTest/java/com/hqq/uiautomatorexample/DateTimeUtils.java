package com.hqq.uiautomatorexample;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by liuchaoran on 16/11/24.
 */
public class DateTimeUtils {
    private static Logger logger = Logger.getLogger("DateTimeUtils");

    public static Long getMilliseconds() {
        return System.currentTimeMillis();
    }
    public static String getFullDateText() {
        Clock clock = Clock.systemDefaultZone();
        Instant instant = clock.instant();
        Date date = Date.from(instant);
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        String d = dt.format(date);
        return d;
    }

//    public static Boolean insideTime(File file, long millisecond) {
//        Long lastModified = file.lastModified();
//        Long cur = getMilliseconds();
//        logger.info("insideTime lastModified:{}, cur:{}, file:{}", lastModified, cur, file);
//        if (cur - lastModified <= millisecond) {
//            return true;
//        }
//        return false;
//    }
    public static LocalDateTime getDateTimeFromTimestamp(long timestamp) {
        if (timestamp == 0)
            return null;
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone
                .getDefault().toZoneId());
    }

    public static List<String> getSomeDay(Integer beg, Integer end, String format) {
        List<String> days = new ArrayList<>();
        for(Integer i = beg; i < end; i++) {
            String day = getDay(i, format);
            days.add(day);
        }
        return days;
    }
    public static Date getDate(Integer diffDays) {
//        LocalDate today = LocalDate.now();
//        LocalDate newDay = today.plusDays(diffDays);
//        return LocalDateToUdate(newDay);

//        过时写法
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, diffDays);
        date = calendar.getTime();
        return date;

//        错误写法
//        Date tmp = new Date(System.currentTimeMillis());
//        Date date = new Date(System.currentTimeMillis()+diffDays*1000*60*60*24);
//        return date;
    }

    public static String getDay(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String formatDateTime = localDateTime.format(formatter);
        return formatDateTime;
    }

    public static String formatDotTimeStamp(Long val) {
        String ret = String.valueOf(val / 1000);
        ret += ".";
        ret += val % 1000;
        return ret;
    }

    public static String getCurrentDay(String format) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat(format);//"yyyy-MM-dd";//HH:mm:ss.SSS
        return df.format(date);
    }
    public static String getDay(Integer diffDays, String format) {
        //Date date = new Date(System.currentTimeMillis()+diffDays*1000*60*60*24);
        Date date = getDate(diffDays);
        SimpleDateFormat df = new SimpleDateFormat(format);//"yyyy-MM-dd";//HH:mm:ss.SSS
        return df.format(date);
    }

    //根据 [beg, end)的规则取中间的日期, dayFormat : "yyyy-MM-dd"
    public static List<String> getDataRange(List<String> dayList, String dayFormat) {
        if (dayList.size() <= 1) {
            return dayList;
        }
        if (dayList.size() != 2) {
            logger.info("bad day range");
            return null;
        }
        if (dayFormat == null) {
            dayFormat = "yyyy-MM-dd";
        }
        //遍历100天
        List<String> dayRangeList = new ArrayList<>();
        String begDay = dayList.get(0);
        String endDay = dayList.get(1);

        int i = 200;
        for (;i >= -200; i--) {
            String curDay = getDay(i, dayFormat);
            if (begDay.equals(curDay)
                    || dayRangeList.size() > 0
                    || endDay.equals(curDay)) {
                dayRangeList.add(curDay);
            }

            //终止:从后往前,也就是从未来到过去进行遍历
            if (begDay.equals(curDay)) {
                return dayRangeList;
            }
        }
        return dayRangeList;
    }
    public static String getPreDay(String day) {
        int i = 30;
        for (;i >= -100; i--) {
            String curDay = getDay(i, "yyyy-MM-dd");
            if (day.equals(curDay)) {
                break;
            }
        }
        i = i-1;
        return getDay(i, "yyyy-MM-dd");
    }

    public static Boolean euqalDay(Date date, String day) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (df.format(date).equals(day)) {
            return true;
        }
        return false;
    }

//    public static List<File> getValidFiles(List<File> fileList, Integer begDiff, Integer endDiff) {
//        //[begDiff, endDiff)
//        Set<String> daySet = new HashSet<>();
//
//        for (int i = begDiff; i < endDiff; i++) {
//            String day = getDay(i, "yyyy-MM-dd");
//            daySet.add(day);
//        }
//
//        List<File> dstFileList = new ArrayList<>();
//        for (File file : fileList) {
//            for (String day: daySet) {
//                if (file.toString().contains(day)) {
//                    dstFileList.add(file);
//                }
//            }
//        }
//        dstFileList = dstFileList.stream().distinct().collect(Collectors.toList());
//        return dstFileList;
//    }

    /**
     * 返回时间戳, 单位: 毫秒
     * @param timeStr 格式: 2015-08-04T03:13:39Z
     * @return
     */
    public static long timestampFromStr(String timeStr){
        return Instant.parse(timeStr).toEpochMilli();
    }

    /**
     * UTC str to timestamp
     * @param timeStr 2017-06-20 08:53:06
     * @return
     */
    public static long timestampFromUDateTime(String timeStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"));
        LocalDateTime localDateTime = LocalDateTime.parse(timeStr, formatter);
        return localDateTime.toEpochSecond(ZoneOffset.UTC) * 1000;
    }

    public static void UDateToLocalDateTime() {
        Date date = new Date();
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
    }

    // 02. java.util.Date --> java.time.LocalDate
    public static void UDateToLocalDate() {
        Date date = new Date();
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDate localDate = localDateTime.toLocalDate();
    }

    // 03. java.util.Date --> java.time.LocalTime
    public static void UDateToLocalTime() {
        Date date = new Date();
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalTime localTime = localDateTime.toLocalTime();
    }


    // 04. java.time.LocalDateTime --> java.util.Date
    public static Date LocalDateTimeToUdate(LocalDateTime localDateTime) {
        //LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }


    // 05. java.time.LocalDate --> java.util.Date
    public static Date LocalDateToUdate(LocalDate localDate) {
        //LocalDate localDate = LocalDate.now();
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    // 06. java.time.LocalTime --> java.util.Date
    public static void LocalTimeToUdate() {
        LocalTime localTime = LocalTime.now();
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
    }


    public static void goMilliSleep(Integer milliSecond) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliSecond);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getCurrentEpochTimeStamp(String timeStamp) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.0Z'");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date= new Date(1000L*Long.valueOf(timeStamp));
        String ret = sdf.format(date) + "000";
        return ret;
        //Date date = sdf.parse(timeStamp);
        //return date.getTime();

    }

    public static Boolean sleep(Long msec) {
        try {
            Thread.sleep(msec);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
