package joe.com.cnode.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import joe.com.cnode.R;
import joe.com.cnode.model.entity.Notification;
import joe.com.cnode.presenter.contract.INotificationPresenter;
import joe.com.cnode.presenter.implement.NotificationPresenter;
import joe.com.cnode.ui.adapter.MessageListAdapter;
import joe.com.cnode.ui.base.StatusBarActivity;
import joe.com.cnode.ui.listener.DoubleClickBackToContentTopListener;
import joe.com.cnode.ui.listener.NavigationFinishClickListener;
import joe.com.cnode.ui.util.RefreshUtils;
import joe.com.cnode.ui.util.ThemeUtils;
import joe.com.cnode.ui.view.IBackToContentTopView;
import joe.com.cnode.ui.view.INotificationView;

/**
 * Created by JOE on 2016/8/25.
 */
public class NotificationActivity extends StatusBarActivity implements INotificationView, IBackToContentTopView,
        Toolbar.OnMenuItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.icon_no_data)
    TextView iconNoData;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private MessageListAdapter adapter;

    private INotificationPresenter notificationPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight, R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.notification);
        toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_done_all:
                        notificationPresenter.markAllMessageReadAsyncTask();
                        return true;
                    default:
                        return false;
                }
            }
        });

        toolbar.setOnClickListener(new DoubleClickBackToContentTopListener(this));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageListAdapter(this);
        recyclerView.setAdapter(adapter);

        notificationPresenter = new NotificationPresenter(this, this);

        RefreshUtils.init(refreshLayout, this);
        RefreshUtils.refresh(refreshLayout, this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    @Override
    public void onRefresh() {
        notificationPresenter.getMessageAsyncTask();
    }

    @Override
    public void onMarkAllMessageReadOk() {
        adapter.markAllMessageRead();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetMessagesFinish() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onGetMessagesOk(@NonNull Notification notification) {
        adapter.getMessageList().clear();
        adapter.getMessageList().addAll(notification.getHasNotReadMessageList());
        adapter.getMessageList().addAll(notification.getHasReadMessageList());
        adapter.notifyDataSetChanged();
        iconNoData.setVisibility(adapter.getMessageList().isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void backToContentTop() {
        recyclerView.scrollToPosition(0);
    }
}
