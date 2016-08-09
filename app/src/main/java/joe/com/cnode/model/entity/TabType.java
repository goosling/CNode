package joe.com.cnode.model.entity;

import android.support.annotation.StringRes;

import joe.com.cnode.R;

/**
 * Created by JOE on 2016/8/8.
 */
public enum TabType {

    all(R.string.tab_all),

    good(R.string.tab_good),

    share(R.string.tab_share),

    ask(R.string.tab_ask),

    job(R.string.tab_job);

    @StringRes
    private int nameId;

    TabType(@StringRes int nameId) {
        this.nameId = nameId;
    }

    @StringRes
    public int getNameId() {
        return nameId;
    }
}
