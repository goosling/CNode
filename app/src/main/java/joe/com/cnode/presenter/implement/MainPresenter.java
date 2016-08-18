package joe.com.cnode.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.util.List;

import joe.com.cnode.model.api.ApiClient;
import joe.com.cnode.model.api.ApiDefine;
import joe.com.cnode.model.api.ForegroundCallback;
import joe.com.cnode.model.entity.Result;
import joe.com.cnode.model.entity.TabType;
import joe.com.cnode.model.entity.Topic;
import joe.com.cnode.presenter.contract.IMainPresenter;
import joe.com.cnode.ui.view.IMainView;
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
                    return super.onResultOk(code, headers, result);
                }

                @Override
                public boolean onResultError(int code, Headers headers, Result.Error error) {
                    return super.onResultError(code, headers, error);
                }

                @Override
                public boolean onCallCancel() {
                    return super.onCallCancel();
                }

                @Override
                public boolean onCallException(Throwable t, Result.Error error) {
                    return super.onCallException(t, error);
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                }
            });
        }
    }

    @Override
    public void loadMoreTopicListAsyncTask(int page) {

    }

    @Override
    public void getUserAsyncTask() {

    }

    @Override
    public void getMessageCountAsyncTask() {

    }
}
