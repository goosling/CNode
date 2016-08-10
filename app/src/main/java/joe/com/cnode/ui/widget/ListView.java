package joe.com.cnode.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOE on 2016/8/9.
 */
public class ListView extends android.widget.ListView {

    private OnScrollListener onScrollListener;

    private List<OnScrollListener> mScrollListeners;

    public ListView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public ListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context ,attrs, 0, 0);
    }

    public ListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if(onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(absListView, i);
                }

                if(mScrollListeners != null && mScrollListeners.size() > 0) {
                    for(OnScrollListener onScrollListener : mScrollListeners) {
                        onScrollListener.onScrollStateChanged(absListView, i);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if(onScrollListener != null) {
                    onScrollListener.onScroll(absListView, i, i1, i2);
                }

                if(mScrollListeners != null && mScrollListeners.size() > 0) {
                    for(OnScrollListener onScrollListener : mScrollListeners) {
                        onScrollListener.onScroll(absListView, i, i1, i2);
                    }
                }
            }
        });
    }

    @Override
    public void setOnScrollListener(OnScrollListener listener) {
        onScrollListener = listener;
    }

    public void addOnScrollListener(OnScrollListener listener) {
        if (mScrollListeners == null) {
            mScrollListeners = new ArrayList<>();
        }
        mScrollListeners.add(listener);
    }

    public void removeOnScrollListener(OnScrollListener listener) {
        if (mScrollListeners != null) {
            mScrollListeners.remove(listener);
        }
    }

    public void clearOnScrollListeners() {
        if (mScrollListeners != null) {
            mScrollListeners.clear();
        }
    }
}
