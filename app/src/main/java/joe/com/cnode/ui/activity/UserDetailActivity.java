package joe.com.cnode.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import joe.com.cnode.R;
import joe.com.cnode.model.api.ApiDefine;
import joe.com.cnode.model.entity.Topic;
import joe.com.cnode.model.entity.User;
import joe.com.cnode.presenter.contract.IUserDetailPresenter;
import joe.com.cnode.presenter.implement.UserDetailPresenter;
import joe.com.cnode.ui.adapter.UserDetailPagerAdapter;
import joe.com.cnode.ui.base.StatusBarActivity;
import joe.com.cnode.ui.listener.NavigationFinishClickListener;
import joe.com.cnode.ui.util.Navigator;
import joe.com.cnode.ui.util.ThemeUtils;
import joe.com.cnode.ui.util.ToastUtils;
import joe.com.cnode.ui.view.IUserDetailView;

/**
 * Created by JOE on 2016/8/22.
 */
public class UserDetailActivity extends StatusBarActivity implements IUserDetailView, Toolbar.OnMenuItemClickListener {

    private static final String EXTRA_LOGIN_NAME = "loginName";
    private static final String EXTRA_AVATAR_URL = "avatarUrl";
    private static final String NAME_IMG_AVATAR = "imgAvatar";

    @BindView(R.id.img_avatar)
    CircleImageView imgAvatar;
    @BindView(R.id.tv_login_name)
    TextView tvLoginName;
    @BindView(R.id.tv_github_username)
    TextView tvGithubUsername;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.layout_info)
    LinearLayout layoutInfo;
    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.progress_wheel)
    ProgressWheel progressWheel;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private UserDetailPagerAdapter adapter;

    private IUserDetailPresenter userDetailPresenter;

    private String loginName;
    private String githubUsername;

    public static void startWithTransitionAnimation(@NonNull Activity activity, String loginName,
                                                    @NonNull ImageView imgAvatar, String avatarUrl) {
        Intent intent = new Intent(activity, UserDetailActivity.class);
        intent.putExtra(EXTRA_LOGIN_NAME, loginName);
        intent.putExtra(EXTRA_AVATAR_URL, avatarUrl);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                imgAvatar, NAME_IMG_AVATAR);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public static void start(@NonNull Activity activity, String loginName) {
        Intent intent = new Intent(activity, UserDetailActivity.class);
        intent.putExtra(EXTRA_LOGIN_NAME, loginName);
        activity.startActivity(intent);
    }

    public static void start(@NonNull Context context, String loginName) {
        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_LOGIN_NAME, loginName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight, R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);

        ViewCompat.setTransitionName(imgAvatar, NAME_IMG_AVATAR);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.user_detail);
        toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_open_in_browser:
                        Navigator.openInBrowser(getApplicationContext(), ApiDefine.USER_LINK_URL_PREFIX + loginName);
                        return true;
                    default:
                        return false;
                }
            }
        });

        adapter = new UserDetailPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        tabLayout.setupWithViewPager(viewPager);

        loginName = getIntent().getStringExtra(EXTRA_LOGIN_NAME);
        tvLoginName.setText(loginName);

        String avatarUrl = getIntent().getStringExtra(EXTRA_AVATAR_URL);
        if(!TextUtils.isEmpty(avatarUrl)) {
            Glide.with(this).load(avatarUrl).placeholder(R.drawable.image_placeholder)
                    .dontAnimate().into(imgAvatar);
        }

        userDetailPresenter = new UserDetailPresenter(this, this);

        userDetailPresenter.getUserAsyncTask(loginName);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }


    @Override
    public void onGetUserOk(@NonNull User user) {
        Glide.with(this).load(user.getAvatarUrl()).placeholder(R.drawable.image_placeholder)
                .dontAnimate().into(imgAvatar);
        tvLoginName.setText(user.getLoginName());
        if(TextUtils.isEmpty(user.getGithubUsername())) {
            tvGithubUsername.setVisibility(View.INVISIBLE);
            tvGithubUsername.setText(null);
        } else {
            tvGithubUsername.setVisibility(View.VISIBLE);
            tvGithubUsername.setText(Html.fromHtml("<u>" + user.getGithubUsername()
                    + "@github.com" + "</u>"));
        }

        tvCreateTime.setText("注册时间: "+  user.getCreateAt().toString("yyyy-MM-dd"));
        tvScore.setText("积分：" + user.getScore());
        adapter.update(user);
        githubUsername = user.getGithubUsername();
    }

    @Override
    public void onGetCollectTopicListOk(@NonNull List<Topic> topicList) {
        adapter.update(topicList);
    }

    @Override
    public void onGetUserError(@NonNull String message) {
        ToastUtils.with(this).show(message);
    }

    @Override
    public void onGetUserStart() {
        progressWheel.spin();
    }

    @Override
    public void onGetUserFinish() {
        progressWheel.stopSpinning();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    @OnClick({R.id.img_avatar, R.id.tv_github_username})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_avatar:
                userDetailPresenter.getUserAsyncTask(loginName);
                break;
            case R.id.tv_github_username:
                if(!TextUtils.isEmpty(githubUsername)) {
                    Navigator.openInBrowser(this, "https://github.com/" + githubUsername);
                }
                break;
        }
    }
}
