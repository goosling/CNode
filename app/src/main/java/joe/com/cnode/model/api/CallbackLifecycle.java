package joe.com.cnode.model.api;

import joe.com.cnode.model.entity.Result;

import retrofit2.Response;

/**
 * Created by JOE on 2016/8/15.
 */
public interface CallbackLifecycle<T> {
    boolean onResultOk(Response<T> response, T result);

    boolean onResultError(Response<T> response, Result.Error error);

    boolean onCallCancel();

    boolean onCallException(Throwable t, Result.Error error);

    void onFinish();

}
