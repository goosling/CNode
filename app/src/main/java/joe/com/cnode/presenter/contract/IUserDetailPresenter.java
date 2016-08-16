package joe.com.cnode.presenter.contract;

import android.support.annotation.NonNull;

/**
 * Created by JOE on 2016/8/15.
 */
public interface IUserDetailPresenter {

    void getUserAsyncTask(@NonNull String loginName);
}
