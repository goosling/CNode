package joe.com.cnode.ui.listener;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.webkit.JavascriptInterface;

import joe.com.cnode.presenter.contract.IReplyPresenter;
import joe.com.cnode.presenter.contract.ITopicHeaderPresenter;
import joe.com.cnode.ui.view.ICreateReplyView;

/**
 * Created by JOE on 2016/8/13.
 */
public final class TopicJavascriptInterface {

    public static final String NAME = "topicBridge";

    private final Activity activity;

    private final ICreateReplyView createReplyView;
    private final ITopicHeaderPresenter topicHeaderPresenter;
    private final IReplyPresenter replyPresenter;

    public TopicJavascriptInterface(@NonNull Activity activity, @NonNull ICreateReplyView createReplyView, @NonNull ITopicHeaderPresenter topicHeaderPresenter, @NonNull IReplyPresenter replyPresenter) {
        this.activity = activity;
        this.createReplyView = createReplyView;
        this.topicHeaderPresenter = topicHeaderPresenter;
        this.replyPresenter = replyPresenter;
    }

    @JavascriptInterface
    public void collectTopic(String topicId) {

    }

    @JavascriptInterface
    public void decollectTopic(String topicId) {

    }

    @JavascriptInterface
    public void upReply(String replyJson) {

    }

    @JavascriptInterface
    public void up(final String targetJson, final int targetPosition) {

    }

    @JavascriptInterface
    public void openUser(String loginName) {

    }


}
