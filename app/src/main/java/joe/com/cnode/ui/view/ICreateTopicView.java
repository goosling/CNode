package joe.com.cnode.ui.view;

import android.support.annotation.NonNull;

/**
 * Created by JOE on 2016/8/15.
 */
public interface ICreateTopicView {

    void onTitleError(@NonNull String message);

    void onContentError(@NonNull String message);

    void onCreateTopicOk(@NonNull String topicId);

    void onCreateTopicStart();

    void onCreateTopicFinish();
}
