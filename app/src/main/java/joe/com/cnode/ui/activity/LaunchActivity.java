package joe.com.cnode.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import joe.com.cnode.R;
import joe.com.cnode.ui.base.BaseActivity;
import joe.com.cnode.ui.util.ActivityUtils;
import joe.com.cnode.util.HandlerUtils;

/**
 * Created by JOE on 2016/8/29.
 */
public class LaunchActivity extends BaseActivity implements Runnable{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        HandlerUtils.postDelayed(this, 1000);
    }

    @Override
    public void run() {
        if(ActivityUtils.isAlive(this)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
