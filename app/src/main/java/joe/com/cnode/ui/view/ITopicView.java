package joe.com.cnode.ui.view;

import android.support.annotation.NonNull;

import joe.com.cnode.model.entity.Reply;
import joe.com.cnode.model.entity.TopicWithReply;

/**
 * Created by JOE on 2016/8/15.
 */
public interface ITopicView {
    void onGetTopicOk(@NonNull TopicWithReply topic);

    void onGetTopicFinish();

    void appendReplyAndUpdateViews(@NonNull Reply reply);
}
