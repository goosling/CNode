package joe.com.cnode.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;

import joe.com.cnode.R;
import joe.com.cnode.model.api.ApiClient;
import joe.com.cnode.model.api.DefaultCallback;
import joe.com.cnode.model.entity.Result;
import joe.com.cnode.presenter.contract.ILoginPresenter;
import joe.com.cnode.ui.view.ILoginView;
import joe.com.cnode.util.FormatUtils;
import retrofit2.Call;
import retrofit2.http.Headers;

/**
 * Created by JOE on 2016/8/17.
 */
public class LoginPresenter implements ILoginPresenter{

    private final Activity activity;
    private final ILoginView loginView;

    public LoginPresenter(@NonNull Activity activity, @NonNull ILoginView loginView) {
        this.activity = activity;
        this.loginView = loginView;
    }

    @Override
    public void loginAsyncTask(final String accessToken) {
        if(!FormatUtils.isAccessToken(accessToken)) {
            loginView.onAccessTokenError(activity.getString(R.string.access_token_format_error));
        } else {
            Call<Result.Login> call = ApiClient.service.accessToken(accessToken);
            loginView.onLoginStart(call);
            call.enqueue(new DefaultCallback<Result.Login>(activity) {

                public boolean onResultOk(int code, Headers headers, Result.Login loginInfo) {
                    loginView.onLoginOk(accessToken, loginInfo);
                    return false;
                }

                public boolean onResultAuthError(int code, Headers headers, Result.Error error) {
                    loginView.onAccessTokenError(getActivity().getString(R.string.access_token_auth_error));
                    return false;
                }

                public void onFinish() {
                    loginView.onLoginFinish();
                }
            });
        }

    }
}
