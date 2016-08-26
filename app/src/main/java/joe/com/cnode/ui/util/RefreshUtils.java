package joe.com.cnode.ui.util;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;

import joe.com.cnode.R;
import joe.com.cnode.util.HandlerUtils;

/**
 * Created by JOE on 2016/8/25.
 */
public class RefreshUtils {

    private RefreshUtils() {}

    public static void init(@NonNull SwipeRefreshLayout refreshLayout, @NonNull SwipeRefreshLayout.OnRefreshListener refreshListener) {
        refreshLayout.setColorSchemeResources(R.color.red_light, R.color.green_light, R.color.blue_light);
        refreshLayout.setOnRefreshListener(refreshListener);
    }

    public static void refresh(@NonNull final SwipeRefreshLayout refreshLayout, @NonNull final SwipeRefreshLayout.OnRefreshListener refreshListener) {
        HandlerUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
            }
        }, 100);
    }
}
