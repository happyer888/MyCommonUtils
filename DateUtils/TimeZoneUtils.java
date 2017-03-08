import java.util.Date;
import java.util.TimeZone;

/**
 * 时区判断转换工具类
 * 
 * 包括:
 * 1.判断是否为东八区
 * 2.根据时区转换日期
 */
public class TimeZoneUtils {

    /**
     * 判断用户的设备时区是否为东八区（中国）
     *
     * @return 布尔值
     */
    public static boolean isInEasternEightZones() {
        boolean defaultVaule = true;
        if (TimeZone.getDefault() == TimeZone.getTimeZone("GMT+08"))
            defaultVaule = true;
        else
            defaultVaule = false;
        return defaultVaule;
    }

    /**
     * 根据不同时区，转换日期时间
     *
     * @param date    日期
     * @param oldZone 旧时区
     * @param newZone 新时区
     * @return 返回 2088年8月31日 格式的日期
     */
    public static Date transformTime(Date date, TimeZone oldZone, TimeZone newZone) {
        Date finalDate = null;
        if (date != null) {
            int timeOffset = oldZone.getOffset(date.getTime())
                    - newZone.getOffset(date.getTime());
            finalDate = new Date(date.getTime() - timeOffset);
        }
        return finalDate;
    }
}
