package joe.com.cnode.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import joe.com.cnode.R;
import joe.com.cnode.model.storage.LoginShared;
import joe.com.cnode.ui.base.FullLayoutActivity;
import joe.com.cnode.ui.dialog.AlertDialogUtils;
import joe.com.cnode.ui.util.ThemeUtils;
import joe.com.cnode.ui.view.ILoginView;
import joe.com.cnode.util.FormatUtils;

/**
 * Created by JOE on 2016/8/29.
 */
public class LoginActivity extends FullLayoutActivity implements ILoginView {

    public static final int REQUEST_LOGIN = FormatUtils.getAutoIncrementInteger();

    public static final String EXTRA_ACTION_CODE = "actionCode";

    public static void startForResult(@NonNull Activity activity, int actionCode) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra(EXTRA_ACTION_CODE, actionCode);
        activity.startActivityForResult(intent, REQUEST_LOGIN);
    }

    public static void startForResult(@NonNull Activity activity) {
        startForResult(activity, -1);
    }

    public static boolean startForResultWithLoginCheck(@NonNull final Activity activity,
                                                       final int actionMode) {
        if(TextUtils.isEmpty(LoginShared.getAccessToken(activity))) {
            AlertDialogUtils.createBuilderWithAutoTheme(activity)
                    .setMessage(R.string.need_login_tip)
                    .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startForResult(activity, actionMode);
                        }
                    })
                    .setNegativeButton(R.string.cancel, null)
                    .show();
            return false;
        } else {
            return true;
        }
    }

    public static boolean startForResultWithLoginCheck(@NonNull Activity activity) {
        return startForResultWithLoginCheck(activity, -1);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight_FitsStatusBar,
                R.style.AppThemeDark_FitsStatusBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
