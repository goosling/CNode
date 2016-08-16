package joe.com.cnode.ui.view;

import android.support.annotation.NonNull;

import java.util.List;

import joe.com.cnode.model.entity.Topic;
import joe.com.cnode.model.entity.User;

/**
 * Created by JOE on 2016/8/15.
 */
public interface IUserDetailView {

    void onGetUserOk(@NonNull User user);

    void onGetCollectTopicListOk(@NonNull List<Topic> topicList);

    void onGetUserError(@NonNull String message);

    void onGetUserStart();

    void onGetUserFinish();

}
