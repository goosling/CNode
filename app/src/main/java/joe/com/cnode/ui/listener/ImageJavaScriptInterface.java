package joe.com.cnode.ui.listener;

import android.content.Context;
import android.support.annotation.NonNull;
import android.webkit.JavascriptInterface;

import joe.com.cnode.ui.activity.ImagePreviewActivity;

/**
 * Created by JOE on 2016/8/11.
 */
public final class ImageJavaScriptInterface {
    private volatile static ImageJavaScriptInterface singleton;

    public static ImageJavaScriptInterface with(@NonNull Context context) {
        if(singleton == null) {
            synchronized (ImageJavaScriptInterface.class) {
                if(singleton == null) {
                    singleton = new ImageJavaScriptInterface(context);
                }
            }
        }
        return singleton;
    }

    public static final String NAME = "imageBridge";

    private final Context context;

    private ImageJavaScriptInterface(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    @JavascriptInterface
    public void openImage(String imageUrl) {
        ImagePreviewActivity.start(context, imageUrl);
    }
}
