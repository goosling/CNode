package joe.com.cnode.ui.activity;

import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.Toolbar;

import joe.com.cnode.presenter.contract.ITopicPresenter;
import joe.com.cnode.ui.base.StatusBarActivity;
import joe.com.cnode.ui.dialog.ProgressDialog;
import joe.com.cnode.ui.view.ICreateTopicView;

/**
 * Created by JOE on 2016/8/27.
 */
public class CreateTopicActivity extends StatusBarActivity implements Toolbar.OnMenuItemClickListener, ICreateTopicView {

    private ProgressDialog progressDialog;

    private ITopicPresenter topicPresenter;

    @Override
    public void onTitleError(@NonNull String message) {

    }

    @Override
    public void onContentError(@NonNull String message) {

    }

    @Override
    public void onCreateTopicOk(@NonNull String topicId) {

    }

    @Override
    public void onCreateTopicStart() {

    }

    @Override
    public void onCreateTopicFinish() {

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }
}
