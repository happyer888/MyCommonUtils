import android.content.Context;
import android.widget.Toast;

import com.framework.ui.keithly.uiframework.global.AppApplication;

/**
 * Toast工具类
 * 
 * 包括:
 * 1.一个参数,主线程弹吐司
 * 2.两个参数,通用弹吐司
 */
public class ToastUtils {

    private static Toast sToast;

    /**
     * 主线程中显示短Toast(一个参数)
     * 注:此方法使用需配合其他工具类共同使用
     *
     * @param msg 显示的消息内容
     */
    public static void showToast(final String msg) {
        Utils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (sToast == null) {
                    sToast = Toast.makeText(AppApplication.context, msg, Toast.LENGTH_SHORT);
                } else {
                    sToast.setText(msg);
                }
                sToast.show();
            }
        });
    }

    /**
     * 主线程中显示长Toast(一个参数)
     * 注:此方法使用需配合其他工具类共同使用
     *
     * @param msg 显示的消息内容
     */
    public static void showToastLong(final String msg) {
        Utils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (sToast == null) {
                    sToast = Toast.makeText(AppApplication.context, msg, Toast.LENGTH_SHORT);
                } else {
                    sToast.setText(msg);
                }
                sToast.show();
            }
        });
    }

    /**
     * Shows a toast message while be called.
     * 显示短Toast信息(两个参数)
     *
     * @param context 上下文
     * @param msg     显示的消息内容
     */
    public static void showToast(Context context, String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        }
        sToast.setText(msg);
        sToast.show();
    }

    /**
     * Shows a toast message for a long time while be called.
     * 显示长Toast信息(两个参数)
     *
     * @param context 上下文
     * @param msg     显示的消息内容
     */
    public static void showToastLong(Context context, String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
        }
        sToast.setText(msg);
        sToast.show();
    }
}
