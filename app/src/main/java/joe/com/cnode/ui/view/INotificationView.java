package joe.com.cnode.ui.view;

import android.support.annotation.NonNull;

import joe.com.cnode.model.entity.Notification;

/**
 * Created by JOE on 2016/8/15.
 */
public interface INotificationView {

    void onGetMessagesOk(@NonNull Notification notification);

    void onGetMessagesFinish();

    void onMarkAllMessageReadOk();
}
