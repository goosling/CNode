package joe.com.cnode.ui.listener;

import android.webkit.JavascriptInterface;

import org.joda.time.DateTime;

import joe.com.cnode.util.FormatUtils;

/**
 * Created by JOE on 2016/8/13.
 */
public class FormatJavascriptInterface {

    public static final FormatJavascriptInterface instance = new FormatJavascriptInterface();

    public static final String NAME = "formatBridge";

    private FormatJavascriptInterface() {}

    @JavascriptInterface
    public String getRelativeTimeSpanString(String time) {
        return FormatUtils.getRelativeTimeSpanString(new DateTime(time));
    }
}
