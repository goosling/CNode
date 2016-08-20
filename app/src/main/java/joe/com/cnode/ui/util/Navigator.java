package joe.com.cnode.ui.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import joe.com.cnode.model.entity.Topic;
import joe.com.cnode.model.util.EntityUtils;

/**
 * Created by JOE on 2016/8/11.
 */
public class Navigator {

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
}
