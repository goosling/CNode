package joe.com.cnode.util;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by JOE on 2016/8/9.
 */
public final class HandlerUtils {

    private HandlerUtils() {}

    private static final Handler handler = new Handler(Looper.getMainLooper());

    public static boolean post(Runnable r) {
        return handler.post(r);
    }

    public static boolean postDelayed(Runnable r, long delayMills) {
        return handler.postDelayed(r, delayMills);
    }
}
