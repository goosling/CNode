package joe.com.cnode.ui.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import joe.com.cnode.R;
import joe.com.cnode.model.api.ApiDefine;
import joe.com.cnode.model.entity.Topic;
import joe.com.cnode.model.storage.SettingShared;
import joe.com.cnode.model.util.EntityUtils;
import joe.com.cnode.ui.activity.NotificationActivity;
import joe.com.cnode.ui.activity.NotificationCompatActivity;
import joe.com.cnode.ui.activity.UserDetailActivity;
import joe.com.cnode.util.FormatUtils;

/**
 * Created by JOE on 2016/8/11.
 */
public class Navigator {

    private Navigator() {}

    public static void openShare(@NonNull Context context, @NonNull String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)));
    }

    public static boolean openStandardLink(@NonNull Context context, @NonNull String url) {
        if(FormatUtils.isUserLinkUrl(url)) {
            UserDetailActivity.start(context, Uri.parse(url).getPath().replace(ApiDefine.USER_PATH_PREFIX, ""));
            return true;
        } else if(FormatUtils.isTopicLinkUrl(url)) {
            TopicWithAutoCompat.start(context, Uri.parse(url).getPath().replace(ApiDefine.TOPIC_PATH_PREFIX, ""));
            return true;
        } else {
            return false;
        }
    }

    public static void openEmail(@NonNull Context context, @NonNull String email,
                                 @NonNull String subject, @NonNull String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("mailto:" + email));
        if(intent.resolveActivity(context.getPackageManager()) != null) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(intent);
        } else {
            ToastUtils.with(context).show(R.string.no_email_client_install_in_system);
        }
    }

    public static void openInMarket(@NonNull Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
        if(intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            ToastUtils.with(context).show(R.string.no_market_install_in_system);
        }
    }

    /**
     * 浏览器中打开
     * @param context
     * @param url
     */
    public static void openInBrowser(@NonNull Context context, @NonNull String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            ToastUtils.with(context).show(R.string.no_browser_install_in_system);
        }
    }

    public static final class TopicWithAutoCompat {
        private TopicWithAutoCompat () {}

        public static final String EXTRA_TOPIC_ID = "topicId";
        public static final String EXTRA_TOPIC = "topic";

        private static Class<?> getTargetClass(@NonNull Context context) {
            //return SettingShared.isReallyEnableTopicRenderCompat(context) ? TopicCompatActivity.class : TopicActivity.class;
            return null;
        }

        public static void start(@NonNull Activity activity, @NonNull Topic topic) {
            Intent intent = new Intent(activity, getTargetClass(activity));
            intent.putExtra(EXTRA_TOPIC_ID, topic.getId());
            intent.putExtra(EXTRA_TOPIC, EntityUtils.gson.toJson(topic));
            activity.startActivity(intent);
        }

        public static void start(@NonNull Activity activity, String topicId) {
            Intent intent = new Intent(activity, getTargetClass(activity));
            intent.putExtra(EXTRA_TOPIC_ID, topicId);
            activity.startActivity(intent);
        }

        public static void start(@NonNull Context context, String topicId) {
            Intent intent = new Intent(context, getTargetClass(context));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(EXTRA_TOPIC_ID, topicId);
            context.startActivity(intent);
        }
    }

    public static final class NotificationWithAutoCompat {
        private NotificationWithAutoCompat () {}

        private static Class<?> getTargetClass(@NonNull Context context) {
            return SettingShared.isReallyEnableTopicRenderCompat(context) ?
                    NotificationCompatActivity.class : NotificationActivity.class ;
        }

        public static void start(@NonNull Activity activity) {
            activity.startActivity(new Intent(activity, getTargetClass(activity)));
        }
    }
}
