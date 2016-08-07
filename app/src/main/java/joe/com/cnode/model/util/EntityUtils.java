package joe.com.cnode.model.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

/**
 * Created by JOE on 2016/8/6.
 */
public final class EntityUtils {

    private EntityUtils() {}

    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter())
            .create();
}
