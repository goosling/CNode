package joe.com.cnode.model.api;

import joe.com.cnode.model.entity.Result;
import okhttp3.Headers;

/**
 * Created by JOE on 2016/8/15.
 */
public interface CallbackLifecycle<T> {
    boolean onResultOk(int code, Headers headers, T result);

    boolean onResultError(int code, Headers headers, Result.Error error);

    boolean onCallCancel();

    boolean onCallException(Throwable t, Result.Error error);

    void onFinish();

}
