package joe.com.cnode.presenter.implement;

import android.app.Activity;

import joe.com.cnode.model.api.ApiClient;
import joe.com.cnode.model.api.ApiDefine;
import joe.com.cnode.model.api.DefaultCallback;
import joe.com.cnode.model.entity.Notification;
import joe.com.cnode.model.entity.Result;
import joe.com.cnode.model.storage.LoginShared;
import joe.com.cnode.presenter.contract.INotificationPresenter;
import joe.com.cnode.ui.view.INotificationView;
import okhttp3.Headers;

/**
 * Created by JOE on 2016/8/19.
 */
public class NotificationPresenter implements INotificationPresenter{

    private final Activity activity;

    private final INotificationView notificationView;

    public NotificationPresenter(Activity activity, INotificationView notificationView) {
        this.activity = activity;
        this.notificationView = notificationView;
    }

    @Override
    public void getMessageAsyncTask() {
        ApiClient.service.getMessages(LoginShared.getAccessToken(activity), ApiDefine.MD_RENDER)
                .enqueue(new DefaultCallback<Result.Data<Notification>>(activity) {
                    @Override
                    public boolean onResultOk(int code, Headers headers, Result.Data<Notification> result) {
                        notificationView.onGetMessagesOk(result.getData());
                        return false;
                    }

                    @Override
                    public void onFinish() {
                        notificationView.onGetMessagesFinish();
                    }
                });
    }

    @Override
    public void markAllMessageReadAsyncTask() {
        ApiClient.service.markAllMessageRead(LoginShared.getAccessToken(activity))
                .enqueue(new DefaultCallback<Result>(activity) {
                    @Override
                    public boolean onResultOk(int code, Headers headers, Result result) {
                        notificationView.onMarkAllMessageReadOk();
                        return false;
                    }
                });
    }
}
