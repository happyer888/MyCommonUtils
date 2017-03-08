import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * 字符串操作工具类
 * 
 * 包括:
 * 1.字符串转日期
 * 2.日期转字符串
 * 3.友好时间显示
 * 4.图片/邮件/网址地址判断
 * 5.字符串转换
 * 6.日期判断
 */
public class StringUtils {
    /**
     * Email地址的正则
     */
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    /**
     * 图片网址的正则
     */
    private final static Pattern IMG_URL = Pattern
            .compile(".*?(gif|jpeg|png|jpg|bmp)");

    /**
     * 网址的正则
     */
    private final static Pattern URL = Pattern
            .compile("^(https|http)://.*?$(net|com|.com.cn|org|me|)");

    /**
     * 日期格式:yyyy-MM-dd HH:mm:ss
     */
    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    /**
     * 日期格式:yyyy-MM-dd
     */
    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 日期格式:yyyy-MM-dd HH:mm
     */
    private final static ThreadLocal<SimpleDateFormat> dateFormat3 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
    };

    /**
     * 字符串转日期
     * 返回格式:yyyy-MM-dd HH:mm:ss
     *
     * @param sdate the sdate 日期字符串
     * @return date 日期
     */
    public static Date toDate(String sdate) {
        return toDate(sdate, dateFormater.get());
    }

    /**
     * 字符串转日期
     * 返回格式:自定义的日期格式
     *
     * @param sdate        the sdate
     * @param dateFormater the date formater
     * @return the date
     */
    public static Date toDate(String sdate, SimpleDateFormat dateFormater) {
        try {
            return dateFormater.parse(sdate);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 日期转字符串
     * 返回格式:yyyy-MM-dd HH:mm:ss
     *
     * @param date the date
     * @return the date string
     */
    public static String getDateString(Date date) {
        return dateFormater.get().format(date);
    }

    /**
     * 日期转字符串
     * 返回格式:yyyy-MM-dd HH:mm
     *
     * @param sdate the sdate
     * @return the date string
     */
    public static String getDateString(String sdate) {
        return dateFormat3.get().format(toDate(sdate));
    }


    /**
     * 友好的时间显示1
     * 返回格式: xxxx年xx月xx日
     *
     * @param sTime the s time  传入时间字符串,如 2088-11-1
     * @return the string   返回 2088年11月1日
     */
    public static String friendlyTime1(String sTime) {
        String tempTime = sTime.substring(0, sTime.indexOf(" "));
        String[] split = tempTime.split("-");
        StringBuilder builder = new StringBuilder();
        return builder.append(split[0]).append("年").append(split[1]).append("月").append(split[2]).append("日").toString();
    }

    /**
     * 友好的时间显示2
     * 返回格式: xx分钟前/xx小时前/昨天/前天/xx天前/1个月前/2个月前/3个月前/xx-xx-xx
     * 注:需配合TimeZoneUtils工具类使用
     *
     * @param sdate the sdate
     * @return string
     */
    public static String friendlyTime2(String sdate) {
        Date time = null;
        //判断时区
        if (TimeZoneUtils.isInEasternEightZones()) {
            time = toDate(sdate);
        } else {
            time = TimeZoneUtils.transformTime(toDate(sdate),
                    TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());
        }
        //未知时区
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            } else {
                ftime = hour + "小时前";
            }
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            } else {
                ftime = hour + "小时前";
            }
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天 ";
        } else if (days > 2 && days < 31) {
            ftime = days + "天前";
        } else if (days >= 31 && days <= 2 * 31) {
            ftime = "一个月前";
        } else if (days > 2 * 31 && days <= 3 * 31) {
            ftime = "2个月前";
        } else if (days > 3 * 31 && days <= 4 * 31) {
            ftime = "3个月前";
        } else {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    /**
     * 友好的时间显示3
     * 返回格式:今天 / 星期X
     *
     * @param sdate the sdate 传入日期字符串,如 2088-11-1
     * @return the string
     */
    public static String friendlyTime3(String sdate) {
        String res = "";
        if (isEmpty(sdate)) {
            return "";
        }

        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String currentData = StringUtils.getDataTime("MM-dd");
        int currentDay = toInt(currentData.substring(3));
        int currentMoth = toInt(currentData.substring(0, 2));

        int sMoth = toInt(sdate.substring(5, 7));
        int sDay = toInt(sdate.substring(8, 10));
        int sYear = toInt(sdate.substring(0, 4));
        Date dt = new Date(sYear, sMoth - 1, sDay - 1);

        if (sDay == currentDay && sMoth == currentMoth) {
            res = "今天 / " + weekDays[getWeekOfDate(new Date())];
        } else if (sDay == currentDay + 1 && sMoth == currentMoth) {
            res = "昨天 / " + weekDays[(getWeekOfDate(new Date()) + 6) % 7];
        } else {
            if (sMoth < 10) {
                res = "0";
            }
            res += sMoth + "/";
            if (sDay < 10) {
                res += "0";
            }
            res += sDay + " / " + weekDays[getWeekOfDate(dt)];
        }
        return res;
    }


    /**
     * 友好的时间显示4 智能格式化
     * 返回格式:(yyyy-MM-dd)(昨天) 上午(/下午) hh:mm
     *
     * @param sdate the sdate
     * @return the string
     */
    public static String friendlyTime4(String sdate) {
        String res = "";
        if (isEmpty(sdate)) {
            return "";
        }
        Date date = StringUtils.toDate(sdate);
        if (date == null) {
            return sdate;
        }
        SimpleDateFormat format = dateFormater2.get();

        if (isToday(date.getTime())) {
            format.applyPattern(isMorning(date.getTime()) ? "上午 hh:mm" : "下午 hh:mm");
            res = format.format(date);
        } else if (isYesterday(date.getTime())) {
            format.applyPattern(isMorning(date.getTime()) ? "昨天 上午 hh:mm" : "昨天 下午 hh:mm");
            res = format.format(date);
        } else if (isCurrentYear(date.getTime())) {
            format.applyPattern(isMorning(date.getTime()) ? "MM-dd 上午 hh:mm" : "MM-dd 下午 hh:mm");
            res = format.format(date);
        } else {
            format.applyPattern(isMorning(date.getTime()) ? "yyyy-MM-dd 上午 hh:mm" : "yyyy-MM-dd 下午 hh:mm");
            res = format.format(date);
        }
        return res;
    }

    /**
     * 判断一个时间是不是上午
     *
     * @param when the when 长整形时间表示
     * @return boolean 真/假
     */
    public static boolean isMorning(long when) {
        android.text.format.Time time = new android.text.format.Time();
        time.set(when);

        int hour = time.hour;
        return (hour >= 0) && (hour < 12);
    }

    /**
     * 判断一个时间是不是今天
     *
     * @param when the when 长整形时间表示
     * @return boolean 真/假
     */
    public static boolean isToday(long when) {
        android.text.format.Time time = new android.text.format.Time();
        time.set(when);

        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;

        time.set(System.currentTimeMillis());
        return (thenYear == time.year)
                && (thenMonth == time.month)
                && (thenMonthDay == time.monthDay);
    }

    /**
     * 判断一个时间是不是昨天
     *
     * @param when the when 长整形时间表示
     * @return boolean 真/假
     */
    public static boolean isYesterday(long when) {
        android.text.format.Time time = new android.text.format.Time();
        time.set(when);

        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;

        time.set(System.currentTimeMillis());
        return (thenYear == time.year)
                && (thenMonth == time.month)
                && (time.monthDay - thenMonthDay == 1);
    }

    /**
     * 判断一个时间是不是今年
     *
     * @param when the when 长整形时间表示
     * @return boolean 真/假
     */
    public static boolean isCurrentYear(long when) {
        android.text.format.Time time = new android.text.format.Time();
        time.set(when);

        int thenYear = time.year;

        time.set(System.currentTimeMillis());
        return (thenYear == time.year);
    }

    /**
     * 获取当前日期是星期几
     *
     * @param dt the dt 日期
     * @return week of date 星期几
     */
    public static int getWeekOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return w;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate the sdate   日期字符串
     * @return boolean boolean  真/假
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 判断是否是相同的一天
     *
     * @param sDate1 sDate1 日期1
     * @param sDate2 sDate2 日期2
     * @return boolean  真/假
     */
    public static boolean isSameDay(String sDate1, String sDate2) {
        if (TextUtils.isEmpty(sDate1) || TextUtils.isEmpty(sDate2)) {
            return false;
        }
        boolean b = false;
        Date date1 = toDate(sDate1);
        Date date2 = toDate(sDate2);
        if (date1 != null && date2 != null) {
            String d1 = dateFormater2.get().format(date1);
            String d2 = dateFormater2.get().format(date2);
            if (d1.equals(d2)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 返回long类型的今天的日期表示
     *
     * @return today
     */
    public static long getToday() {
        Calendar cal = Calendar.getInstance();
        String curDate = dateFormater2.get().format(cal.getTime());
        curDate = curDate.replace("-", "");
        return Long.parseLong(curDate);
    }

    /**
     * 获取表示当前时间的字符串
     *
     * @return the cur time str
     */
    public static String getCurTimeStr() {
        Calendar cal = Calendar.getInstance();
        String curDate = dateFormater.get().format(cal.getTime());
        return curDate;
    }

    /***
     * 计算两个时间差，单位为秒s
     *
     * @param date1 the date1 时间1
     * @param date2 the date2 时间2
     * @return long 时间差xx秒
     */
    public static long calDateDifferent(String date1, String date2) {
        long diff = 0;
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = dateFormater.get().parse(date1);
            d2 = dateFormater.get().parse(date2);
            //毫秒ms
            diff = d2.getTime() - d1.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff / 1000;
    }

    /**
     * 判断给定字符串是否空白串
     * 空白串是指由空格、制表符、回车符、换行符组成的字符串
     * 若输入字符串为null或空字符串，返回true
     *
     * @param input the input
     * @return boolean boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input)) {
            return true;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param emailUrl the emailUrl
     * @return boolean
     */
    public static boolean isEmail(String emailUrl) {
        if (emailUrl == null || emailUrl.trim().length() == 0) {
            return false;
        }
        return emailer.matcher(emailUrl).matches();
    }

    /**
     * 判断一个url是否为图片url
     *
     * @param url the url
     * @return boolean
     */
    public static boolean isImgUrl(String url) {
        if (url == null || url.trim().length() == 0) {
            return false;
        }
        return IMG_URL.matcher(url).matches();
    }

    /**
     * 判断是否为一个合法的url地址
     *
     * @param url the url
     * @return boolean
     */
    public static boolean isUrl(String url) {
        if (url == null || url.trim().length() == 0) {
            return false;
        }
        return URL.matcher(url).matches();
    }

    /**
     * 字符串转短整型Int
     *
     * @param str      the str
     * @param defValue the def value
     * @return int
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转短整型Int
     *
     * @param obj the obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null) {
            return 0;
        }
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转长整型Long
     *
     * @param obj the obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b the b 字符串
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 获取返回的字符串.
     *
     * @param s the s 字符串
     * @return the string
     */
    public static String getString(String s) {
        return s == null ? "" : s;
    }

    /**
     * 将一个InputStream流转换成字符串
     *
     * @param is the is IO输入流
     * @return string   返回字符串
     */
    public static String toConvertString(InputStream is) {
        StringBuffer res = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader read = new BufferedReader(isr);
        try {
            String line;
            line = read.readLine();
            while (line != null) {
                res.append(line + "<br>");
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != isr) {
                    isr.close();
                    isr.close();
                }
                if (null != read) {
                    read.close();
                    read = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
            }
        }
        return res.toString();
    }

    /***
     * 截取字符串
     *
     * @param start 从哪里开始，0算起 
     * @param num 截取多少个
     * @param str 截取的字符串
     * @return sub string 返回子字符串结果
     */
    public static String getSubString(int start, int num, String str) {
        if (str == null) {
            return "";
        }
        int leng = str.length();
        if (start < 0) {
            start = 0;
        }
        if (start > leng) {
            start = leng;
        }
        if (num < 0) {
            num = 1;
        }
        int end = start + num;
        if (end > leng) {
            end = leng;
        }
        return str.substring(start, end);
    }

    /**
     * 获取当前时间为每年第几周
     *
     * @return week of year
     */
    public static int getWeekOfYear() {
        return getWeekOfYear(new Date());
    }

    /**
     * 获取当前时间为每年第几周
     *
     * @param date the date 当前的日期
     * @return week of year
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        int week = c.get(Calendar.WEEK_OF_YEAR) - 1;
        week = week == 0 ? 52 : week;
        return week > 0 ? week : 1;
    }

    /**
     * 获取当前日期的int[].
     *
     * @return the int[]
     */
    public static int[] getCurrentDate() {
        int[] dateBundle = new int[3];
        String[] temp = getDataTime("yyyy-MM-dd").split("-");

        for (int i = 0; i < 3; i++) {
            try {
                dateBundle[i] = Integer.parseInt(temp[i]);
            } catch (Exception e) {
                dateBundle[i] = 0;
            }
        }
        return dateBundle;
    }

    /**
     * 返回当前系统时间
     *
     * @param format the format
     * @return the data time
     */
    public static String getDataTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }
}
