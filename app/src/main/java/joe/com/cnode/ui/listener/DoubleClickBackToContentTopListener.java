package joe.com.cnode.ui.listener;

import android.view.View;

import joe.com.cnode.ui.view.IBackToContentTopView;

/**
 * Created by JOE on 2016/8/19.
 */
public class DoubleClickBackToContentTopListener extends OnDoubleClickListener {

    private IBackToContentTopView backToContentTopView;

    public DoubleClickBackToContentTopListener(IBackToContentTopView backToContentTopView) {
        super(300);
        this.backToContentTopView = backToContentTopView;
    }

    @Override
    public void onDoubleClick(View v) {
        backToContentTopView.backToContentTop();
    }
}
