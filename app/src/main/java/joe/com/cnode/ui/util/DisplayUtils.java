package joe.com.cnode.ui.util;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import joe.com.cnode.util.ResUtils;

/**
 * Created by JOE on 2016/8/29.
 */
public final class DisplayUtils {

    private DisplayUtils() {}

    public static void adaptStatusBar(@NonNull Context context, @NonNull View statusBar) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup.LayoutParams params = statusBar.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ResUtils.getStatusBarHeight(context);
            statusBar.setLayoutParams(params);
        }
    }
}
