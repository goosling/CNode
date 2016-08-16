package joe.com.cnode.ui.view;

import android.support.annotation.NonNull;

import joe.com.cnode.model.entity.Reply;

/**
 * Created by JOE on 2016/8/13.
 */
public interface ICreateReplyView {

    void showWindow();

    void dismissWindow();

    void onAt(@NonNull Reply target, @NonNull Integer targetPosition);

    void onContentError(@NonNull String message);

    void onReplyTopicOk(@NonNull Reply reply);

    void onReplyTopicStart();

    void onReplyTopicFinish();
}
