package joe.com.cnode.presenter.contract;

import android.support.annotation.NonNull;

import joe.com.cnode.model.entity.TabType;

/**
 * Created by JOE on 2016/8/15.
 */
public interface IMainPresenter {

    void switchTab(@NonNull TabType tab);

    void refreshTopicListAsyncTask();

    void loadMoreTopicListAsyncTask(int page);

    void getUserAsyncTask();

    void getMessageCountAsyncTask();
}
