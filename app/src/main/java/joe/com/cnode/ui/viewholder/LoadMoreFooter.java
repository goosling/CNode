package joe.com.cnode.ui.viewholder;

/**
 * Created by JOE on 2016/8/15.
 */
public class LoadMoreFooter {

    public enum State {
        disable, loading, nomore, endless, fail
    }

    public interface OnLoadMoreListener {

        void onLoadMore();

    }
}
