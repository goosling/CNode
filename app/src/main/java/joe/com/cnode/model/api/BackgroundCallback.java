package joe.com.cnode.model.api;

import joe.com.cnode.model.entity.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JOE on 2016/8/15.
 */
public class BackgroundCallback<T extends Result> implements Callback<T>, CallbackLifecycle<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        boolean interrupt;
        if(response.isSuccessful()) {
            interrupt = onResultOk(response, response.body());
        } else {
            interrupt = onResultError(response, Result.buildError(response));
        }

        if (!interrupt) {
            onFinish();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        boolean interrupt;
        if (call.isCanceled()) {
            interrupt = onCallCancel();
        } else {
            interrupt = onCallException(t, Result.buildError(t));
        }
        if (!interrupt) {
            onFinish();
        }
    }

    @Override
    public boolean onResultOk(Response<T> response, T result) {
        return false;
    }

    @Override
    public boolean onResultError(Response<T> response, Result.Error error) {
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
    public void onFinish() {}

}
