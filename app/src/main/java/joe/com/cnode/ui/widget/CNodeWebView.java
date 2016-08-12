package joe.com.cnode.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import joe.com.cnode.R;

/**
 * Created by JOE on 2016/8/11.
 */
public class CNodeWebView extends WebView {

    private final WebViewClient webViewClient = new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            /*if (!TextUtils.isEmpty(url) && !Navigator.openStandardLink(webView.getContext(), url)) {
                Navigator.openInBrowser(webView.getContext(), url);
            }*/
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            CNodeWebView.this.onPageFinished(url);
        }
    };

    private boolean darkTheme;

    public CNodeWebView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public CNodeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public CNodeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CNodeWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CNodeWebView,
                defStyleAttr, defStyleRes);
        darkTheme = a.getBoolean(R.styleable.CNodeWebView_darkTheme, false);
        a.recycle();

        getSettings().setJavaScriptEnabled(true);
        setWebViewClient(webViewClient);
    }

    public boolean isDarkTheme() {
        return darkTheme;
    }

    protected void onPageFinished(String url) {}
}
