package joe.com.cnode.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * Created by JOE on 2016/8/11.
 */
public class ImagePreviewActivity {

    private static final String EXTRA_IMAGE_URL = "imageUrl";

    public static void start(@NonNull Context context, String imageUrl) {
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_IMAGE_URL, imageUrl);
        context.startActivity(intent);
    }
}
