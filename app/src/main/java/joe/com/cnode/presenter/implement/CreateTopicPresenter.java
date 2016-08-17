package joe.com.cnode.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import joe.com.cnode.R;
import joe.com.cnode.model.api.ApiClient;
import joe.com.cnode.model.api.DefaultCallback;
import joe.com.cnode.model.entity.Result;
import joe.com.cnode.model.entity.TabType;
import joe.com.cnode.model.storage.LoginShared;
import joe.com.cnode.model.storage.SettingShared;
import joe.com.cnode.presenter.contract.ICreateTopicPresenter;
import joe.com.cnode.ui.view.ICreateTopicView;
import retrofit2.Response;

/**
 * Created by JOE on 2016/8/16.
 */
public class CreateTopicPresenter implements ICreateTopicPresenter {

    private final Activity activity;

    private final ICreateTopicView createTopicView;

    public CreateTopicPresenter(Activity activity, ICreateTopicView createTopicView) {
        this.activity = activity;
        this.createTopicView = createTopicView;
    }

    @Override
    public void createTopicAsyncTask(@NonNull TabType tab, String title, String content) {
        if(TextUtils.isEmpty(title) || title.length() < 10) {
            createTopicView.onTitleError(activity.getString(R.string.title_empty_error_tip));
        } else if(TextUtils.isEmpty(content)) {
            createTopicView.onContentError(activity.getString(R.string.content_empty_error_tip));
        } else {
            if (SettingShared.isEnableTopicSign(activity)) { // 添加小尾巴
                content += "\n\n" + SettingShared.getTopicSignContent(activity);
            }
            createTopicView.onCreateTopicStart();
            ApiClient.service.createTopic(LoginShared.getAccessToken(activity), tab, title, content).enqueue(new DefaultCallback<Result.CreateTopic>(activity) {

                @Override
                public boolean onResultOk(Response<Result.CreateTopic> response, Result.CreateTopic result) {
                    createTopicView.onCreateTopicOk(result.getTopicId());
                    return false;
                }

                @Override
                public void onFinish() {
                    createTopicView.onCreateTopicFinish();
                }

            });

        }
    }
}
