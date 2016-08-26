package joe.com.cnode.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import joe.com.cnode.BuildConfig;
import joe.com.cnode.R;
import joe.com.cnode.ui.base.StatusBarActivity;
import joe.com.cnode.ui.util.Navigator;
import joe.com.cnode.ui.util.ThemeUtils;

/**
 * Created by JOE on 2016/8/25.
 */
public class AboutUsActivity extends StatusBarActivity {

    public static final String VERSION_TEXT = BuildConfig.VERSION_NAME + "-build-" + BuildConfig.VERSION_CODE;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.btn_version)
    LinearLayout btnVersion;
    @BindView(R.id.btn_open_source_url)
    LinearLayout btnOpenSourceUrl;
    @BindView(R.id.btn_about_cnode)
    LinearLayout btnAboutCnode;
    @BindView(R.id.btn_about_author)
    LinearLayout btnAboutAuthor;
    @BindView(R.id.btn_open_in_market)
    LinearLayout btnOpenInMarket;
    @BindView(R.id.btn_advice_feedback)
    LinearLayout btnAdviceFeedback;
    @BindView(R.id.btn_open_source_license)
    TextView btnOpenSourceLicense;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight_FitsStatusBar,
                R.style.AppThemeDark_FitsStatusBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_version, R.id.btn_open_source_url, R.id.btn_about_cnode, R.id.btn_about_author, R.id.btn_open_in_market, R.id.btn_advice_feedback, R.id.btn_open_source_license})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_version:
                break;
            case R.id.btn_open_source_url:
                Navigator.openInBrowser(this, getString(R.string.open_source_url_content));
                break;
            case R.id.btn_about_cnode:
                Navigator.openInBrowser(this, getString(R.string.about_cnode_content));
                break;
            case R.id.btn_about_author:
                Navigator.openInBrowser(this, getString(R.string.about_author_content));
                break;
            case R.id.btn_open_in_market:
                Navigator.openInMarket(this);
                break;
            case R.id.btn_advice_feedback:
                Navigator.openEmail(this,
                        "joehuang920@gmail.com",
                        "来自CNodeMD-" + VERSION_TEXT + "的客户端反馈",
                        "设备信息：Android " + Build.VERSION.RELEASE + " - " +
                        Build.MANUFACTURER + " - " + Build.MODEL +
                        "\n（如果涉及隐私请手动删除这个内容）\n\n");
                break;
            case R.id.btn_open_source_license:
                startActivity(new Intent(this, LicenseActivity.class));
                break;
        }
    }
}
