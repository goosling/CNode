package joe.com.cnode.model.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.UUID;

import joe.com.cnode.util.Digest;

/**
 * Created by JOE on 2016/8/8.
 */
public class DeviceInfo {

    private DeviceInfo() {}

    private static final String TAG = "DeviceInfo";

    private static final String KEY_DEVICE_TOKEN = "deviceToken";

    private volatile static String deviceToken = null;

    private static SharedPreferences getSharedPreferences(@NonNull Context context) {
        return context.getSharedPreferences(Digest.MD5.getHex(TAG), Context.MODE_PRIVATE);
    }

    @NonNull
    public static String getDeviceToken(@NonNull Context context) {
        if(TextUtils.isEmpty(deviceToken)) {
           synchronized (DeviceInfo.class) {
               if(TextUtils.isEmpty(deviceToken)) {
                   deviceToken = getSharedPreferences(context).getString(KEY_DEVICE_TOKEN, null);
               }

               if(TextUtils.isEmpty(deviceToken)) {
                   deviceToken = UUID.randomUUID().toString();
                   getSharedPreferences(context).edit().putString(KEY_DEVICE_TOKEN, deviceToken).apply();
               }
           }
        }
        return deviceToken;
    }
}
