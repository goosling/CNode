package joe.com.cnode.presenter.contract;

import android.support.annotation.NonNull;

import joe.com.cnode.model.entity.TabType;

/**
 * Created by JOE on 2016/8/15.
 */
public interface ICreateTopicPresenter {

    void createTopicAsyncTask(@NonNull TabType tab, String title, String content);
}
