package joe.com.cnode.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.util.List;

import joe.com.cnode.R;
import joe.com.cnode.model.api.ApiClient;
import joe.com.cnode.model.api.DefaultCallback;
import joe.com.cnode.model.api.ForegroundCallback;
import joe.com.cnode.model.entity.Result;
import joe.com.cnode.model.entity.Topic;
import joe.com.cnode.model.entity.User;
import joe.com.cnode.presenter.contract.IUserDetailPresenter;
import joe.com.cnode.ui.util.ActivityUtils;
import joe.com.cnode.ui.view.IUserDetailView;
import joe.com.cnode.util.HandlerUtils;
import okhttp3.Headers;

/**
 * Created by JOE on 2016/8/19.
 */
public class UserDetailPresenter implements IUserDetailPresenter {

    private final Activity activity;

    private final IUserDetailView userDetailView;

    private boolean loading = false;

    public UserDetailPresenter(Activity activity, IUserDetailView userDetailView) {
        this.activity = activity;
        this.userDetailView = userDetailView;
    }

    @Override
    public void getUserAsyncTask(@NonNull String loginName) {
        if(!loading) {
            loading = true;
            userDetailView.onGetUserStart();
            ApiClient.service.getUser(loginName).enqueue(new ForegroundCallback<Result.Data<User>>(activity) {
                private long startLoadingTime = System.currentTimeMillis();

                private long getPostTime() {
                    long postTime = 1000 - (System.currentTimeMillis() - startLoadingTime);
                    if(postTime > 0) {
                        return postTime;
                    } else {
                        return 0;
                    }
                }

                @Override
                public boolean onResultOk(int code, Headers headers, final Result.Data<User> result) {
                    HandlerUtils.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (ActivityUtils.isAlive(getActivity())) {
                                userDetailView.onGetUserOk(result.getData());
                                onFinish();
                            }
                        }
                    }, getPostTime());

                    return true;
                }

                @Override
                public boolean onResultError(final int code, Headers headers, final Result.Error error) {
                    HandlerUtils.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(ActivityUtils.isAlive(activity)) {
                                userDetailView.onGetUserError(code == 404 ? error.getErrorMessage() : getActivity().getString(R.string.data_load_faild_and_click_avatar_to_reload));
                                onFinish();
                            }
                        }
                    }, getPostTime());

                    return true;
                }

                @Override
                public boolean onCallCancel() {
                    return super.onCallCancel();
                }

                @Override
                public boolean onCallException(Throwable t, Result.Error error) {
                    HandlerUtils.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            if (ActivityUtils.isAlive(getActivity())) {
                                userDetailView.onGetUserError(getActivity().getString(R.string.data_load_faild_and_click_avatar_to_reload));
                                onFinish();
                            }
                        }

                    }, getPostTime());
                    return true;
                }

                @Override
                public void onFinish() {
                    userDetailView.onGetUserFinish();
                    loading = false;
                }
            });

            ApiClient.service.getCollectTopicList(loginName)
                    .enqueue(new DefaultCallback<Result.Data<List<Topic>>>(activity){
                        @Override
                        public boolean onResultOk(int code, Headers headers, Result.Data<List<Topic>> result) {
                            userDetailView.onGetCollectTopicListOk(result.getData());
                            return false;
                        }
                    });
        }
    }
}
