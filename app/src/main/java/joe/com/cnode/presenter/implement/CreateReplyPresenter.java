package joe.com.cnode.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import joe.com.cnode.R;
import joe.com.cnode.model.storage.SettingShared;
import joe.com.cnode.presenter.contract.ICreateReplyPresenter;
import joe.com.cnode.ui.view.ICreateReplyView;

/**
 * Created by JOE on 2016/8/15.
 */
public class CreateReplyPresenter implements ICreateReplyPresenter {

    private final Activity activity;

    private final ICreateReplyView createReplyView;

    public CreateReplyPresenter(@NonNull Activity activity, @NonNull ICreateReplyView createReplyView) {
        this.activity = activity;
        this.createReplyView = createReplyView;
    }

    @Override
    public void createReplyAsyncTask(@NonNull String topicId, String content, final String targetId) {
        if(TextUtils.isEmpty(content)) {
            createReplyView.onContentError(activity.getString(R.string.content_empty_error_tip));
        } else {
            final String finalContent;
            if(SettingShared.isEnableTopicSign(activity)) {
                finalContent = content + "\n\n" + SettingShared.getTopicSignContent(activity);
            } else {
                finalContent = content;
            }

            createReplyView.onReplyTopicStart();
            
        }
    }
}
