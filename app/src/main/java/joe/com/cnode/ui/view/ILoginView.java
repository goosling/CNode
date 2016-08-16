package joe.com.cnode.ui.view;

import android.support.annotation.NonNull;

import joe.com.cnode.model.entity.Result;
import retrofit2.Call;

/**
 * Created by JOE on 2016/8/15.
 */
public interface ILoginView {

    void onAccessTokenError(@NonNull String message);

    void onLoginOk(@NonNull String accessToken, @NonNull Result.Login loginInfo);

    void onLoginStart(@NonNull Call<Result.Login> call);

    void onLoginFinish();


}
