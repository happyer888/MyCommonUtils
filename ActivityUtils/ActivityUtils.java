

import android.app.Activity;
import java.util.ArrayList;

/**
 * 一键退出(BaseActivity+ActivityUtils)
 */
public class ActivityUtils {
    // 声明一个集合用于记录所有打开的活动  
    private static ArrayList<Activity> sList = new ArrayList<Activity>();

    /**
     * 加入活动对象--------->onCreate
     * @param activity Activity
     */
    public static void add(Activity activity) {
        sList.add(activity);
    }

    /**
     * 移除活动对象--------->onDestroy
     * @param activity Activity
     */
    public static void remove(Activity activity) {
        sList.remove(activity);
    }

    /**
     * 关闭所有的活动--------->close
     */
    public static void removeAll() {
        for (Activity activity : sList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}