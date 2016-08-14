package joe.com.cnode.presenter.contract;

import android.support.annotation.NonNull;

import joe.com.cnode.model.entity.Reply;

/**
 * Created by JOE on 2016/8/13.
 */
public interface IReplyPresenter {

    void upReplyAsyncTask(@NonNull Reply reply);
}
