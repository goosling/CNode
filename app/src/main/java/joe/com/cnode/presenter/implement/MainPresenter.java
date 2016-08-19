package joe.com.cnode.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.List;

import joe.com.cnode.model.api.ApiClient;
import joe.com.cnode.model.api.ApiDefine;
import joe.com.cnode.model.api.ForegroundCallback;
import joe.com.cnode.model.entity.Result;
import joe.com.cnode.model.entity.TabType;
import joe.com.cnode.model.entity.Topic;
import joe.com.cnode.model.entity.User;
import joe.com.cnode.model.storage.LoginShared;
import joe.com.cnode.presenter.contract.IMainPresenter;
import joe.com.cnode.ui.util.ToastUtils;
import joe.com.cnode.ui.view.IMainView;
import joe.com.cnode.ui.viewholder.LoadMoreFooter;
import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by JOE on 2016/8/17.
 */
public class MainPresenter implements IMainPresenter {

    private static final int PAGE_LIMIT = 20;

    private final Activity activity;

    private final IMainView mainView;

    private TabType tab = TabType.all;
    private Call<Result.Data<List<Topic>>> refreshCall = null;
    private Call<Result.Data<List<Topic>>> loadMoreCall = null;

    public MainPresenter(@NonNull Activity activity, @NonNull IMainView mainView) {
        this.activity = activity;
        this.mainView = mainView;
    }

    private void cancelRefreshCall() {
        if(refreshCall != null) {
            if(!refreshCall.isCanceled()) {
                refreshCall.cancel();
            }
            refreshCall = null;
        }
    }

    private void cancelLoadMoreCall() {
        if(loadMoreCall != null) {
            if(!loadMoreCall.isCanceled()) {
                loadMoreCall.cancel();
            }
            loadMoreCall = null;
        }
    }

    @Override
    public void switchTab(@NonNull TabType tab) {
        if(this.tab != tab) {
            this.tab = tab;
            cancelRefreshCall();
            cancelLoadMoreCall();
            mainView.onSwitchTabOk(tab);
        }
    }

    @Override
    public void refreshTopicListAsyncTask() {
        if(refreshCall == null) {
            refreshCall = ApiClient.service.getTopicList(tab, 1, PAGE_LIMIT, ApiDefine.MD_RENDER);
            refreshCall.enqueue(new ForegroundCallback<Result.Data<List<Topic>>>(activity) {
                @Override
                public boolean onResultOk(int code, Headers headers, Result.Data<List<Topic>> result) {
                    cancelLoadMoreCall();
                    mainView.onRefreshTopicListOk(result.getData());
                    return false;
                }

                @Override
                public boolean onResultError(int code, Headers headers, Result.Error error) {
                    ToastUtils.with(getActivity()).show(error.getErrorMessage());
                    return false;
                }

                @Override
                public boolean onCallCancel() {
                    return true;
                }

                @Override
                public boolean onCallException(Throwable t, Result.Error error) {
                    ToastUtils.with(getActivity()).show(error.getErrorMessage());
                    return false;
                }

                @Override
                public void onFinish() {
                    refreshCall = null;
                    mainView.onRefreshTopicListFinish();
                }
            });
        }
    }

    @Override
    public void loadMoreTopicListAsyncTask(int page) {
        if(loadMoreCall == null) {
            loadMoreCall = ApiClient.service.getTopicList(tab, page, PAGE_LIMIT, ApiDefine.MD_RENDER);
            loadMoreCall.enqueue(new ForegroundCallback<Result.Data<List<Topic>>>(activity) {
                @Override
                public boolean onResultOk(int code, Headers headers, Result.Data<List<Topic>> result) {
                    if(result.getData().size() > 0) {
                        mainView.onLoadMoreTopicListOk(result.getData());
                        mainView.onLoadMoreTopicListFinish(LoadMoreFooter.State.endless);
                    } else {
                        mainView.onLoadMoreTopicListFinish(LoadMoreFooter.State.nomore);
                    }
                    return false;
                }

                @Override
                public boolean onResultError(int code, Headers headers, Result.Error error) {
                    mainView.onLoadMoreTopicListFinish(LoadMoreFooter.State.fail);
                    ToastUtils.with(getActivity()).show(error.getErrorMessage());
                    return false;
                }

                @Override
                public boolean onCallCancel() {
                    return true;
                }

                @Override
                public boolean onCallException(Throwable t, Result.Error error) {
                    mainView.onLoadMoreTopicListFinish(LoadMoreFooter.State.fail);
                    ToastUtils.with(getActivity()).show(error.getErrorMessage());
                    return false;
                }

                @Override
                public void onFinish() {
                    loadMoreCall = null;
                }
            });
        }
    }

    @Override
    public void getUserAsyncTask() {
        final String accessToken = LoginShared.getAccessToken(activity);
        if(!TextUtils.isEmpty(accessToken)) {
            ApiClient.service.getUser(LoginShared.getLoginName(activity))
                    .enqueue(new ForegroundCallback<Result.Data<User>>(activity) {
                        @Override
                        public boolean onResultOk(int code, Headers headers, Result.Data<User> result) {
                            if(TextUtils.equals(accessToken, LoginShared.getAccessToken(getActivity()))) {
                                LoginShared.update(getActivity(), result.getData());
                                mainView.updateUserInfoViews();
                            }
                            return false;
                        }
                    });
        }
    }

    @Override
    public void getMessageCountAsyncTask() {
        final String accessToken = LoginShared.getAccessToken(activity);
        if(!TextUtils.isEmpty(accessToken)) {
            ApiClient.service.getMessageCount(accessToken).enqueue(new ForegroundCallback<Result.Data<Integer>>(activity){
                @Override
                public boolean onResultOk(int code, Headers headers, Result.Data<Integer> result) {
                    if(TextUtils.equals(accessToken, LoginShared.getAccessToken(getActivity()))) {
                        mainView.updateMessageCountViews(result.getData());
                    }

                    return false;
                }
            });
        }
    }
}
