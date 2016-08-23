package joe.com.cnode.ui.util;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

import joe.com.cnode.model.storage.SettingShared;

/**
 * Created by JOE on 2016/8/22.
 */
public final class ThemeUtils {

    private ThemeUtils() {}

    public static boolean configThemeBeforeOnCreate(@NonNull Activity activity, @StyleRes int light,
                                                    @StyleRes int dark) {
        boolean enable = SettingShared.isEnableThemeDark(activity);
        activity.setTheme(enable? dark : light);
        return enable;
    }

    public static void notifyThemeApply(@NonNull Activity activity, boolean delay) {
        if(delay) {
            ActivityUtils.recreateDelayed(activity);
        } else {
            ActivityUtils.recreate(activity);
        }
    }
}
