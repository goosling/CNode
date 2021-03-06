package joe.com.cnode.ui.dialog;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import joe.com.cnode.R;
import joe.com.cnode.model.storage.SettingShared;

/**
 * Created by JOE on 2016/8/24.
 */
public class ProgressDialog extends AppCompatDialog {

    @BindView(R.id.tv_message)
    TextView tvMessage;

    public static ProgressDialog createWithAutoTheme(@NonNull Activity activity) {
        return new ProgressDialog(activity, SettingShared.isEnableThemeDark(activity) ?
            R.style.AppDialogDark_Alert : R.style.AppDialogLight_Alert);
    }

    private ProgressDialog(@NonNull Activity activity, int theme) {
        super(activity, theme);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_progress);
        ButterKnife.bind(this);
    }

    public void setMessage(CharSequence text) {
        tvMessage.setText(text);
        tvMessage.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
    }

    public void setMessage(@StringRes int resId) {
        tvMessage.setText(resId);
        tvMessage.setVisibility(resId == 0 ? View.GONE : View.VISIBLE);
    }
}
