package joe.com.cnode.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by JOE on 2016/8/9.
 */
public final class ResUtils {

    private ResUtils() {}

    public static int getStatusBarHeight(@NonNull Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return resourceId > 0 ? context.getResources().getDimensionPixelSize(resourceId) : 0;
    }

    @ColorInt
    public static int getThemeAttrColor(@NonNull Context context, @AttrRes int attr) {
        TypedArray a = context.obtainStyledAttributes(null, new int[]{attr});
        try{
            return a.getColor(0, 0);
        } finally {
            a.recycle();
        }
    }

    public static Drawable getThemeAttrDrawable(@NonNull Context context, @AttrRes int attr) {
        TypedArray a = context.obtainStyledAttributes(null, new int[]{attr});
        try{
            return a.getDrawable(0);
        }finally {
            a.recycle();
        }
    }

    public static String getRawString(@NonNull Context context, @RawRes int rawId) throws IOException {
        InputStream in = context.getResources().openRawResource(rawId);
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            sb.append(line).append("/n");
        }

        sb.deleteCharAt(sb.length() - 1);
        br.close();
        return sb.toString();
    }
}
