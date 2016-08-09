package joe.com.cnode.model.util;

import android.os.Build;

import java.io.IOException;

import joe.com.cnode.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by JOE on 2016/8/8.
 */
public final class HttpUtils {

    private HttpUtils() {}

    public static final String USER_AGENT = "CNodeMD/" + BuildConfig.VERSION_NAME + " (Android " + Build.VERSION.RELEASE + "; " + Build.MANUFACTURER + " - " + Build.MODEL + ")";

    public static final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(createUserAgentInterceptor())
            .addInterceptor(createHttpLoggingInterceptor())
            .build();

    private static Interceptor createUserAgentInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .header("User-Agent", USER_AGENT)
                        .build();
                return chain.proceed(request);
            }
        };
    }

    private static Interceptor createHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY :
                                    HttpLoggingInterceptor.Level.NONE);
        return loggingInterceptor;
    }
}
