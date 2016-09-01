package joe.com.cnode.ui.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import joe.com.cnode.R;
import joe.com.cnode.model.entity.Reply;
import joe.com.cnode.model.storage.SettingShared;
import joe.com.cnode.presenter.contract.ICreateReplyPresenter;
import joe.com.cnode.presenter.implement.CreateReplyPresenter;
import joe.com.cnode.ui.util.ToastUtils;
import joe.com.cnode.ui.view.ICreateReplyView;
import joe.com.cnode.ui.view.ITopicView;
import joe.com.cnode.ui.widget.EditBarHandler;

/**
 * Created by JOE on 2016/8/19.
 */
public class CreateReplyDialog extends AppCompatDialog implements ICreateReplyView {

    @BindView(R.id.btn_tool_close)
    ImageView btnToolClose;
    @BindView(R.id.btn_format_bold)
    ImageView btnFormatBold;
    @BindView(R.id.btn_format_italic)
    ImageView btnFormatItalic;
    @BindView(R.id.btn_format_quote)
    ImageView btnFormatQuote;
    @BindView(R.id.btn_format_list_bulleted)
    ImageView btnFormatListBulleted;
    @BindView(R.id.btn_format_list_numbered)
    ImageView btnFormatListNumbered;
    @BindView(R.id.btn_insert_code)
    ImageView btnInsertCode;
    @BindView(R.id.btn_insert_link)
    ImageView btnInsertLink;
    @BindView(R.id.btn_insert_photo)
    ImageView btnInsertPhoto;
    @BindView(R.id.btn_preview)
    ImageView btnPreview;
    @BindView(R.id.layout_editor_bar)
    LinearLayout layoutEditorBar;
    @BindView(R.id.btn_tool_send)
    ImageView btnToolSend;
    @BindView(R.id.tv_target)
    TextView tvTarget;
    @BindView(R.id.btn_clear_target)
    ImageView btnClearTarget;
    @BindView(R.id.layout_target)
    FrameLayout layoutTarget;
    @BindView(R.id.edt_content)
    EditText edtContent;

    public static CreateReplyDialog createWithAutoTheme(@NonNull Activity activity,
                                                        @NonNull String topicId, @NonNull ITopicView topicView) {
        return new CreateReplyDialog(
                activity,
                SettingShared.isEnableThemeDark(activity) ? R.style.AppDialogDark_TopicReply :
                        R.style.AppDialogLight_TopicReply,
                topicId,
                topicView
        );
    }

    private final String topicId;
    private final ITopicView topicView;
    private final ProgressDialog progressDialog;
    private final ICreateReplyPresenter createReplyPresenter;

    private String targetId = null;

    private CreateReplyDialog(@NonNull Activity activity, int theme, @NonNull String topicId, @NonNull ITopicView topicView) {
        super(activity, theme);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_create_reply);
        ButterKnife.bind(this);

        this.topicId = topicId;
        this.topicView = topicView;

        progressDialog = ProgressDialog.createWithAutoTheme(activity);
        progressDialog.setMessage(R.string.posting_$_);
        progressDialog.setCancelable(false);

        new EditBarHandler(activity, layoutEditorBar, edtContent);

        createReplyPresenter = new CreateReplyPresenter(activity, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
    }


    @OnClick({R.id.btn_tool_close, R.id.btn_tool_send, R.id.btn_clear_target})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tool_close:
                dismissWindow();
                break;
            case R.id.btn_tool_send:
                createReplyPresenter.createReplyAsyncTask(topicId, edtContent.getText().toString().trim(),
                        targetId);
                break;
            case R.id.btn_clear_target:
                targetId = null;
                layoutTarget.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void showWindow() {
        show();
    }

    @Override
    public void dismissWindow() {
        dismiss();
    }

    @Override
    public void onAt(@NonNull Reply target, @NonNull Integer targetPosition) {
        targetId = target.getId();
        layoutTarget.setVisibility(View.VISIBLE);
        tvTarget.setText(getContext().getString(R.string.reply_$d_floor, targetPosition + 1));
        edtContent.getText().insert(edtContent.getSelectionEnd(), "@" + target.getAuthor().getLoginName() + " ");
        showWindow();
    }

    @Override
    public void onContentError(@NonNull String message) {
        ToastUtils.with(getContext()).show(message);
        edtContent.requestFocus();
    }

    @Override
    public void onReplyTopicOk(@NonNull Reply reply) {
        topicView.appendReplyAndUpdateViews(reply);
        dismissWindow();
        targetId = null;
        layoutTarget.setVisibility(View.GONE);
        edtContent.setText(null);
        ToastUtils.with(getContext()).show(R.string.post_success);
    }

    @Override
    public void onReplyTopicStart() {
        progressDialog.show();
    }

    @Override
    public void onReplyTopicFinish() {
        progressDialog.dismiss();
    }

}
