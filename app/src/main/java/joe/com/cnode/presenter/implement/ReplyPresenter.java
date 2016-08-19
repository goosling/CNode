package joe.com.cnode.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;

import joe.com.cnode.model.api.ApiClient;
import joe.com.cnode.model.api.DefaultCallback;
import joe.com.cnode.model.entity.Reply;
import joe.com.cnode.model.entity.Result;
import joe.com.cnode.model.storage.LoginShared;
import joe.com.cnode.presenter.contract.IReplyPresenter;
import joe.com.cnode.ui.view.IReplyView;
import okhttp3.Headers;

/**
 * Created by JOE on 2016/8/19.
 */
public class ReplyPresenter implements IReplyPresenter {

    private final Activity activity;

    private final IReplyView replyView;

    public ReplyPresenter(Activity activity, IReplyView replyView) {
        this.activity = activity;
        this.replyView = replyView;
    }

    @Override
    public void upReplyAsyncTask(@NonNull final Reply reply) {
        ApiClient.service.upReply(reply.getId(), LoginShared.getAccessToken(activity))
                .enqueue(new DefaultCallback<Result.UpReply>(activity) {
                    @Override
                    public boolean onResultOk(int code, Headers headers, Result.UpReply result) {
                        if(result.getAction() == Reply.UpAction.up) {
                            reply.getUpList().add(LoginShared.getId(getActivity()));
                        } else if (result.getAction() == Reply.UpAction.down) {
                            reply.getUpList().remove(LoginShared.getId(getActivity()));
                        }
                        replyView.onUpReplyOk(reply);
                        return false;
                    }
                });
    }
}
