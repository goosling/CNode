package joe.com.cnode.ui.view;

import android.support.annotation.NonNull;

import joe.com.cnode.model.entity.Reply;

/**
 * Created by JOE on 2016/8/13.
 */
public interface ICreateReplyView {

    void onUpReplyOk(@NonNull Reply reply);
}
