package joe.com.cnode.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import joe.com.cnode.R;
import joe.com.cnode.model.entity.Topic;
import joe.com.cnode.model.storage.SettingShared;
import joe.com.cnode.model.util.EntityUtils;
import joe.com.cnode.presenter.contract.ITopicPresenter;
import joe.com.cnode.ui.base.StatusBarActivity;
import joe.com.cnode.ui.dialog.AlertDialogUtils;
import joe.com.cnode.ui.dialog.CreateReplyDialog;
import joe.com.cnode.ui.listener.DoubleClickBackToContentTopListener;
import joe.com.cnode.ui.listener.NavigationFinishClickListener;
import joe.com.cnode.ui.util.Navigator;
import joe.com.cnode.ui.util.ThemeUtils;
import joe.com.cnode.ui.view.IBackToContentTopView;
import joe.com.cnode.ui.view.ICreateReplyView;
import joe.com.cnode.ui.view.ITopicView;
import joe.com.cnode.ui.viewholder.TopicHeader;

/**
 * Created by JOE on 2016/8/31.
 */
public class TopicActivity extends StatusBarActivity implements ITopicView, IBackToContentTopView,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.icon_no_data)
    TextView iconNoData;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.fab_reply)
    FloatingActionButton fabReply;

    private String topicId;

    private Topic topic;

    private ICreateReplyView createReplyView;

    private TopicHeader header;

    private ReplyListAdapter adapter;

    private ITopicPresenter topicPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight, R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ButterKnife.bind(this);

        if(SettingShared.isShowTopicRenderCompatTip(this)) {
            SettingShared.markShowTopicRenderCompatTip(this);
            AlertDialogUtils.createBuilderWithAutoTheme(this)
                    .setMessage(R.string.topic_render_compat_tip)
                    .setPositiveButton(R.string.ok, null)
                    .show();
        }

        topicId = getIntent().getStringExtra(Navigator.TopicWithAutoCompat.EXTRA_TOPIC_ID);

        if(!TextUtils.isEmpty(getIntent().getStringExtra(Navigator.TopicWithAutoCompat.EXTRA_TOPIC))) {
            topic = EntityUtils.gson.fromJson(getIntent().getStringExtra(Navigator.TopicWithAutoCompat.EXTRA_TOPIC),
                    Topic.class);
        }

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.topic);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

        toolbar.setOnClickListener(new DoubleClickBackToContentTopListener(this));

        createReplyView = CreateReplyDialog.createWithAutoTheme(this, topicId, this);
        header = new TopicHeader(this, listView);
        header.updateViews(topic, false, 0);

    }
}
