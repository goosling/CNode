package joe.com.cnode.model.api;

import android.app.Activity;
import android.support.annotation.NonNull;

import joe.com.cnode.model.entity.Result;
import joe.com.cnode.ui.util.ActivityUtils;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JOE on 2016/8/16.
 */
public class ForegroundCallback<T extends Result> implements Callback<T>, CallbackLifecycle<T> {

    private final Activity activity;

    public ForegroundCallback(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    protected final Activity getActivity() {
        return activity;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(ActivityUtils.isAlive(activity)) {
            boolean interrupt;
            if(response.isSuccessful()) {
                interrupt = onResultOk(response.code(), response.headers(), response.body());
            } else {
                interrupt = onResultError(response.code(), response.headers(), Result.buildError(response));
            }

            if(!interrupt) {
                onFinish();
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if(ActivityUtils.isAlive(activity)) {
            boolean interrupt;
            if(call.isCanceled()) {
                interrupt = onCallCancel();
            } else {
                interrupt = onCallException(t, Result.buildError(t));
            }

            if(!interrupt) {
                onFinish();
            }
        }
    }

    @Override
    public boolean onResultOk(int code, Headers headers, T result) {
        return false;
    }

    @Override
    public boolean onResultError(int code, Headers headers, Result.Error error) {
        return false;
    }

    @Override
    public boolean onCallCancel() {
        return false;
    }

    @Override
    public boolean onCallException(Throwable t, Result.Error error) {
        return false;
    }

    @Override
    public void onFinish() {

    }
}
