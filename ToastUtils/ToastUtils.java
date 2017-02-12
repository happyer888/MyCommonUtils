

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    private static Toast sToast;

    /**
     * Shows a short toast message while be called.
     * @param context Context
     * @param msg     Message
     */
    public static void showToast(Context context, String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        }
        sToast.setText(msg);
        sToast.show();
    }

    /**
     * Shows a long toast message while be called.
     * @param context Context
     * @param msg     Message
     */
    public static void showLongToast(Context context, String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
        }
        sToast.setText(msg);
        sToast.show();
    }
}