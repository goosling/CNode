package joe.com.cnode.ui.dialog;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import joe.com.cnode.R;
import joe.com.cnode.model.storage.SettingShared;

/**
 * Created by JOE on 2016/8/16.
 */
public final class AlertDialogUtils {

    private AlertDialogUtils() {}

    public static AlertDialog.Builder createBuilderWithAutoTheme(@NonNull Activity activity) {
        return new AlertDialog.Builder(activity,
                SettingShared.isEnableThemeDark(activity) ? R.style.AppDialogDark_Alert : R.style.AppDialogLight_Alert);
    }
}
