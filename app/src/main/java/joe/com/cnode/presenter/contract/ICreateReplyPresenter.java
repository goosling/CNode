package joe.com.cnode.presenter.contract;

import android.support.annotation.NonNull;

/**
 * Created by JOE on 2016/8/15.
 */
public interface ICreateReplyPresenter {

    void createReplyAsyncTask(@NonNull String topicId, String content, String targetId);
}
