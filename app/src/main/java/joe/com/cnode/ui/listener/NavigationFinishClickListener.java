package joe.com.cnode.ui.listener;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.view.View;

/**
 * Created by JOE on 2016/8/19.
 */
public class NavigationFinishClickListener implements View.OnClickListener {

    private final Activity activity;

    public NavigationFinishClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        ActivityCompat.finishAfterTransition(activity);
    }
}
