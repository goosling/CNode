package joe.com.cnode.model.api;

import joe.com.cnode.model.util.EntityUtils;
import joe.com.cnode.model.util.HttpUtils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JOE on 2016/8/15.
 */
public final class ApiClient {

    private ApiClient() {}

    public static final ApiService service = new Retrofit.Builder()
            .baseUrl(ApiDefine.API_BASE_URL)
            .client(HttpUtils.client)
            .addConverterFactory(GsonConverterFactory.create(EntityUtils.gson))
            .build()
            .create(ApiService.class);
}
