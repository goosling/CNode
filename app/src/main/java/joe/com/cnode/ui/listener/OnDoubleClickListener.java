package joe.com.cnode.ui.listener;

import android.view.View;

/**
 * Created by JOE on 2016/8/19.
 */
public abstract class OnDoubleClickListener implements View.OnClickListener{

    private final long delayTime;

    private long lastClickTime = 0;

    public OnDoubleClickListener(long delayTime) {
        this.delayTime = delayTime;
    }

    @Override
    public void onClick(View view) {
        long nowClickTime = System.currentTimeMillis();

        if(nowClickTime - lastClickTime > delayTime) {
            lastClickTime = nowClickTime;
        } else {
            onDoubleClick(view);
        }
    }

    public abstract void onDoubleClick(View v);
}
