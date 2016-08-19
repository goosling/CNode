package joe.com.cnode.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;

import joe.com.cnode.model.api.ApiClient;
import joe.com.cnode.model.api.ApiDefine;
import joe.com.cnode.model.api.DefaultCallback;
import joe.com.cnode.model.entity.Result;
import joe.com.cnode.model.entity.TopicWithReply;
import joe.com.cnode.model.storage.LoginShared;
import joe.com.cnode.presenter.contract.ITopicPresenter;
import joe.com.cnode.ui.view.ITopicView;
import okhttp3.Headers;

/**
 * Created by JOE on 2016/8/19.
 */
public class TopicPresenter implements ITopicPresenter {

    private final Activity activity;

    private final ITopicView topicView;

    public TopicPresenter(Activity activity, ITopicView topicView) {

        this.activity = activity;
        this.topicView = topicView;
    }

    @Override
    public void getTopicAsyncTask(@NonNull String topicId) {
        ApiClient.service.getTopic(topicId, LoginShared.getAccessToken(activity), ApiDefine.MD_RENDER)
                .enqueue(new DefaultCallback<Result.Data<TopicWithReply>>(activity) {
                    @Override
                    public boolean onResultOk(int code, Headers headers, Result.Data<TopicWithReply> result) {
                        topicView.onGetTopicOk(result.getData());
                        return false;
                    }

                    @Override
                    public void onFinish() {
                        topicView.onGetTopicFinish();
                    }
                });
    }
}
