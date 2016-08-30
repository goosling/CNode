package joe.com.cnode.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import joe.com.cnode.R;
import joe.com.cnode.ui.base.StatusBarActivity;
import joe.com.cnode.ui.dialog.AlertDialogUtils;
import joe.com.cnode.ui.listener.NavigationFinishClickListener;
import joe.com.cnode.util.FormatUtils;

/**
 * Created by JOE on 2016/8/29.
 */
public class QRCodeActivity extends StatusBarActivity implements QRCodeReaderView.OnQRCodeReadListener {

    private static final String[] PERMISSIONS = {Manifest.permission.CAMERA};

    public static final int PERMISSIONS_REQUEST_QRCODE = FormatUtils.getAutoIncrementInteger();

    public static final int REQUEST_QRCODE = FormatUtils.getAutoIncrementInteger();

    public static final String EXTRA_QRCODE = "qrcode";

    @BindView(R.id.qr_view)
    QRCodeReaderView qrView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.icon_line)
    ImageView iconLine;

    public static void startForResultWithPermissionCheck(@NonNull final Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                AlertDialogUtils.createBuilderWithAutoTheme(activity)
                        .setMessage(R.string.qrcode_request_permission_rationale_tip)
                        .setPositiveButton(R.string.open_permissions_request, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSIONS_REQUEST_QRCODE);
                            }
                        })
                        .show();
            } else {
                ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSIONS_REQUEST_QRCODE);
            }
        } else {
            startForResult(activity);
        }
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    private static void startForResult(@NonNull Activity activity) {
        activity.startActivityForResult(new Intent(activity, QRCodeActivity.class), REQUEST_QRCODE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        qrView.setOnQRCodeReadListener(this);

        iconLine.startAnimation(AnimationUtils.loadAnimation(this, R.anim.qrcode_line_anim));
    }

    @Override
    protected void onResume() {
        super.onResume();
        qrView.getCameraManager().startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrView.getCameraManager().stopPreview();
    }

    @Override
    public void cameraNotFound() {
        AlertDialogUtils.createBuilderWithAutoTheme(this)
                .setMessage(R.string.can_not_open_camera)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        setResult(RESULT_CANCELED);
                        finish();
                    }

                })
                .show();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_QRCODE, text);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void QRCodeNotFoundOnCamImage() {

    }
}
