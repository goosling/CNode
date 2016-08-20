package joe.com.cnode.ui.listener;

import android.content.Context;
import android.support.annotation.NonNull;
import android.webkit.JavascriptInterface;

import joe.com.cnode.ui.util.Navigator;

/**
 * Created by JOE on 2016/8/19.
 */
public class NotificationJavascriptInterface {

    private volatile static NotificationJavascriptInterface instance;

    public static NotificationJavascriptInterface with(@NonNull Context context) {
        if(instance == null) {
            synchronized (NotificationJavascriptInterface.class) {
                if(instance == null) {
                    instance = new NotificationJavascriptInterface(context);
                }
            }
        }
        return instance;
    }

    public static final String NAME = "notificationBridge";

    private final Context context;

    public NotificationJavascriptInterface(Context context) {
        this.context = context.getApplicationContext();
    }

    @JavascriptInterface
    public void openTopic(String topicId) {
        Navigator.TopicWithAutoCompat.start(context, topicId);
    }

    @JavascriptInterface
    public void openUser(String loginName) {
       // UserDetailActivity.start(context, loginName);
    }
}
