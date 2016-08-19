package joe.com.cnode.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;

import joe.com.cnode.model.api.ApiClient;
import joe.com.cnode.model.api.DefaultCallback;
import joe.com.cnode.model.entity.Result;
import joe.com.cnode.model.storage.LoginShared;
import joe.com.cnode.presenter.contract.ITopicHeaderPresenter;
import joe.com.cnode.ui.view.ITopicHeaderView;
import okhttp3.Headers;

/**
 * Created by JOE on 2016/8/19.
 */
public class TopicHeaderPresenter implements ITopicHeaderPresenter {

    private final Activity activity;

    private final ITopicHeaderView topicHeaderView;

    public TopicHeaderPresenter(Activity activity, ITopicHeaderView topicHeaderView) {
        this.activity = activity;
        this.topicHeaderView = topicHeaderView;
    }

    @Override
    public void collectTopicAsyncTask(@NonNull String topicId) {
        ApiClient.service.collectTopic(LoginShared.getAccessToken(activity), topicId).enqueue(new DefaultCallback<Result>(activity) {
            @Override
            public boolean onResultOk(int code, Headers headers, Result result) {
                topicHeaderView.onCollectTopicOk();
                return false;
            }
        });

    }

    @Override
    public void decollectTopicAsyncTask(@NonNull String topicId) {
        ApiClient.service.decollectTopic(LoginShared.getAccessToken(activity), topicId).enqueue(new DefaultCallback<Result>(activity) {
            @Override
            public boolean onResultOk(int code, Headers headers, Result result) {
                topicHeaderView.onDecollectTopicOk();
                return false;
            }
        });
    }
}
