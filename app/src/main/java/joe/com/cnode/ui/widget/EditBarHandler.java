package joe.com.cnode.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by JOE on 2016/8/27.
 */
public class EditBarHandler {

    private final Activity activity;

    private final EditText etContent;

    private final InputMethodManager inputMethodManager;

    public EditBarHandler(Activity activity, @NonNull View editBar, EditText etContent) {
        this.activity = activity;
        this.etContent = etContent;
        inputMethodManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);

    }
}
