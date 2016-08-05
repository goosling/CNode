package joe.com.cnode.app;

import android.app.Application;
import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import net.danlew.android.joda.JodaTimeAndroid;

import joe.com.cnode.BuildConfig;

/**
 * Created by JOE on 2016/8/4.
 */
public class AppController extends Application implements Thread.UncaughtExceptionHandler{

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        //初始化JodaTimeAndroid
        JodaTimeAndroid.init(this);

        //配置全局异常捕获
        if(!BuildConfig.DEBUG) {
            Thread.setDefaultUncaughtExceptionHandler(this);
        }

        //友盟设置调试模式
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        //CrashLogActivity.start(this, e);
        System.exit(1);
    }
}
