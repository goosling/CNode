package joe.com.cnode.ui.view;

import android.support.annotation.NonNull;

import java.util.List;

import joe.com.cnode.model.entity.TabType;
import joe.com.cnode.model.entity.Topic;
import joe.com.cnode.ui.viewholder.LoadMoreFooter;

/**
 * Created by JOE on 2016/8/15.
 */
public interface IMainView {

    void onSwitchTabOk(@NonNull TabType tab);

    void onRefreshTopicListOk(@NonNull List<Topic> topicList);

    void onRefreshTopicListFinish();

    void onLoadMoreTopicListOk(@NonNull List<Topic> topicList);

    void onLoadMoreTopicListFinish(@NonNull LoadMoreFooter.State state);

    void updateUserInfoViews();

    void updateMessageCountViews(int count);
}
