import android.content.res.Resources;
import android.graphics.Color;

import java.util.Random;

/**
 * 常用功能工具类
 * 使用方法:新建MyApplication.java类,内容如下:
    public class MyApplication extends Application {
        public static Handler sMainHandler;
        public static Context context;
        @Override
        public void onCreate() {
            super.onCreate();
            sMainHandler = new Handler();
            context = this;
        }
    }
 */
public class Utils {
    /**
     * 主线程更新UI,适配没有上下文的环境
     *
     * @param runnable Runnable
     */
    public static void runOnUIThread(Runnable runnable) {
        MyApplication.sMainHandler.post(runnable);
    }

    /**
     * 获取字符串数组信息
     *
     * @param resId 资源的ID
     * @return 返回字符串数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取资源管理类
     *
     * @return Resources
     */
    public static Resources getResources() {
        return MyApplication.context.getResources();
    }

    /**
     * 屏幕适配中实现px转dp的方法
     *
     * @param resId 资源的ID
     * @return int
     */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取所需资源的颜色值信息
     *
     * @param resId 资源的ID
     * @return 返回指定资源ID的颜色值
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 随机生成单一一种颜色
     * 颜色值范围[0,180]
     *
     * @return 单一颜色值
     */
    public static int createRandomColor() {
        Random random = new Random();
        return random.nextInt(180);
    }

    /**
     * 随机生成一组RGB混合颜色
     *
     * @return RGB混合色彩值
     */
    // 创建一个随机的颜色
    public static int randomColor() {
        Random random = new Random();
        int red = random.nextInt(180);
        int blue = random.nextInt(180);
        int green = random.nextInt(180);
        return Color.rgb(red, green, blue);
    }
}
