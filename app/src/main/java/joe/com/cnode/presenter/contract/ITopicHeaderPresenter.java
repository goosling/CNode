package joe.com.cnode.presenter.contract;

import android.support.annotation.NonNull;

/**
 * Created by JOE on 2016/8/13.
 */
public interface ITopicHeaderPresenter {

    void collectTopicAsyncTask(@NonNull String topicId);

    void decollectTopicAsyncTask(@NonNull String topicId);
}
