package joe.com.cnode.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import joe.com.cnode.R;
import joe.com.cnode.model.entity.Result;
import joe.com.cnode.model.storage.LoginShared;
import joe.com.cnode.presenter.contract.ILoginPresenter;
import joe.com.cnode.presenter.implement.LoginPresenter;
import joe.com.cnode.ui.base.FullLayoutActivity;
import joe.com.cnode.ui.dialog.AlertDialogUtils;
import joe.com.cnode.ui.dialog.ProgressDialog;
import joe.com.cnode.ui.listener.DialogCancelCallListener;
import joe.com.cnode.ui.listener.NavigationFinishClickListener;
import joe.com.cnode.ui.util.DisplayUtils;
import joe.com.cnode.ui.util.ThemeUtils;
import joe.com.cnode.ui.util.ToastUtils;
import joe.com.cnode.ui.view.ILoginView;
import joe.com.cnode.util.FormatUtils;
import retrofit2.Call;

/**
 * Created by JOE on 2016/8/29.
 */
public class LoginActivity extends FullLayoutActivity implements ILoginView {

    @BindView(R.id.adapt_status_bar)
    protected View adaptStatusBar;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.et_access_token)
    protected MaterialEditText etAccessToken;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private int actionCode;

    private ProgressDialog progressDialog;

    private ILoginPresenter loginPresenter;

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
        if (TextUtils.isEmpty(LoginShared.getAccessToken(activity))) {
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
        ButterKnife.bind(this);

        actionCode = getIntent().getIntExtra(EXTRA_ACTION_CODE, -1);

        DisplayUtils.adaptStatusBar(this, adaptStatusBar);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        progressDialog = ProgressDialog.createWithAutoTheme(this);
        progressDialog.setMessage(R.string.logging_in_$_);

        loginPresenter = new LoginPresenter(this, this);
    }

    @OnClick({R.id.btn_qrcode, R.id.btn_login, R.id.btn_login_tip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_qrcode:
                QRCodeActivity.startForResultWithPermissionCheck(this);
                break;
            case R.id.btn_login:
                loginPresenter.loginAsyncTask(etAccessToken.getText().toString().trim());
                break;
            case R.id.btn_login_tip:
                AlertDialogUtils.createBuilderWithAutoTheme(this)
                        .setMessage(R.string.how_to_get_access_token_tip_content)
                        .setPositiveButton(R.string.confirm, null)
                        .show();
                break;
        }
    }

    @Override
    public void onAccessTokenError(@NonNull String message) {
        etAccessToken.setError(message);
        etAccessToken.requestFocus();
    }

    @Override
    public void onLoginOk(@NonNull String accessToken, @NonNull Result.Login loginInfo) {
        LoginShared.login(this, accessToken, loginInfo);
        ToastUtils.with(this).show(R.string.login_success);
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ACTION_CODE, actionCode);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onLoginStart(@NonNull Call<Result.Login> call) {
        progressDialog.setOnCancelListener(new DialogCancelCallListener(call));
        progressDialog.show();
    }

    @Override
    public void onLoginFinish() {
        progressDialog.setOnCancelListener(null);
        progressDialog.dismiss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == QRCodeActivity.PERMISSIONS_REQUEST_QRCODE) {
            QRCodeActivity.startForResultWithPermissionCheck(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QRCodeActivity.REQUEST_QRCODE && resultCode == RESULT_OK) {
            etAccessToken.setText(data.getStringExtra(QRCodeActivity.EXTRA_QRCODE));
            etAccessToken.setSelection(etAccessToken.length());
            onClick(btnLogin);
        }
    }
}
