package joe.com.cnode.model.entity;

import android.support.annotation.NonNull;

import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import joe.com.cnode.model.util.EntityUtils;
import retrofit2.Response;

/**
 * Created by JOE on 2016/8/6.
 */
public class Result {

    protected boolean success;

    public boolean isSuccess() {
        return success;
    }

    public static class Data<T> extends Result {

        private T data;

        public T getData() {
            return data;
        }
    }

    public static class Error extends Result {
        @SerializedName("error_msg")
        private String errorMessage;

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    public static <T extends Result> Error buildError(@NonNull Response<T> response) {
        try{
            return EntityUtils.gson.fromJson(response.errorBody().string(), Error.class);
        } catch (IOException | JsonSyntaxException e) {
            Error error = new Error();
            error.success = false;
            switch (response.code()) {
                case 400:
                    error.errorMessage = "请求参数有误";
                    break;
                case 403:
                    error.errorMessage = "请求被拒绝";
                    break;
                case 404:
                    error.errorMessage = "资源未找到";
                    break;
                case 405:
                    error.errorMessage = "请求方式不被允许";
                    break;
                case 408:
                    error.errorMessage = "请求超时";
                    break;
                case 422:
                    error.errorMessage = "请求语义错误";
                    break;
                case 500:
                    error.errorMessage = "服务器逻辑错误";
                    break;
            }
        }
    }
}
