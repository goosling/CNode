package joe.com.cnode.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import joe.com.cnode.R;
import joe.com.cnode.model.api.ApiDefine;
import joe.com.cnode.model.entity.Reply;
import joe.com.cnode.model.entity.Topic;
import joe.com.cnode.model.entity.TopicWithReply;
import joe.com.cnode.model.storage.SettingShared;
import joe.com.cnode.model.util.EntityUtils;
import joe.com.cnode.presenter.contract.ITopicPresenter;
import joe.com.cnode.presenter.implement.TopicPresenter;
import joe.com.cnode.ui.adapter.ReplyListAdapter;
import joe.com.cnode.ui.base.StatusBarActivity;
import joe.com.cnode.ui.dialog.AlertDialogUtils;
import joe.com.cnode.ui.dialog.CreateReplyDialog;
import joe.com.cnode.ui.listener.DoubleClickBackToContentTopListener;
import joe.com.cnode.ui.listener.NavigationFinishClickListener;
import joe.com.cnode.ui.util.Navigator;
import joe.com.cnode.ui.util.RefreshUtils;
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
                switch(item.getItemId()) {
                    case R.id.action_share:
                        if(topic != null) {
                            Navigator.openShare(getApplicationContext(), "《" + topic.getTitle() + "》\n" +
                                    ApiDefine.TOPIC_LINK_URL_PREFIX + topicId + "\n -来自CNode社区");
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });

        toolbar.setOnClickListener(new DoubleClickBackToContentTopListener(this));

        createReplyView = CreateReplyDialog.createWithAutoTheme(this, topicId, this);
        header = new TopicHeader(this, listView);
        header.updateViews(topic, false, 0);

        adapter = new ReplyListAdapter(this, createReplyView);
        listView.setAdapter(adapter);

        iconNoData.setVisibility(topic == null ? View.VISIBLE : View.GONE);

        fabReply.attachToListView(listView);

        topicPresenter = new TopicPresenter(this, this);

        RefreshUtils.init(refreshLayout, this);
        RefreshUtils.refresh(refreshLayout, this);

    }

    @Override
    public void onRefresh() {
        topicPresenter.getTopicAsyncTask(topicId);
    }

    @OnClick(R.id.fab_reply)
    protected void onBtnReplyClick() {
        if (topic != null && LoginActivity.startForResultWithLoginCheck(this)) {
            createReplyView.showWindow();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LoginActivity.REQUEST_LOGIN && resultCode == RESULT_OK) {
            refreshLayout.setRefreshing(true);
            onRefresh();
        }
    }

    @Override
    public void onGetTopicOk(@NonNull TopicWithReply topic) {
        this.topic = topic;
        header.updateViews(topic);
        adapter.setReplyList(topic.getReplyList());
        adapter.notifyDataSetChanged();
        iconNoData.setVisibility(View.GONE);
    }

    @Override
    public void onGetTopicFinish() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void appendReplyAndUpdateViews(@NonNull Reply reply) {
        adapter.addReply(reply);
        adapter.notifyDataSetChanged();
        header.updateReplyCount(adapter.getReplyList().size());
        listView.smoothScrollToPosition(adapter.getReplyList().size());
    }

    @Override
    public void backToContentTop() {
        listView.setSelection(0);
    }
}
