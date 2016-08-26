package joe.com.cnode.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import joe.com.cnode.R;
import joe.com.cnode.ui.base.StatusBarActivity;
import joe.com.cnode.ui.listener.NavigationFinishClickListener;
import joe.com.cnode.ui.util.ThemeUtils;
import joe.com.cnode.ui.util.ToastUtils;
import joe.com.cnode.util.ResUtils;

/**
 * Created by JOE on 2016/8/25.
 */
public class LicenseActivity extends StatusBarActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_license)
    TextView tvLicense;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight, R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        try{
            tvLicense.setText(ResUtils.getRawString(this, R.raw.open_source));
        } catch (IOException e) {
            tvLicense.setText(null);
            ToastUtils.with(this).show(R.string.fail_to_read_resource);
        }
    }
}
