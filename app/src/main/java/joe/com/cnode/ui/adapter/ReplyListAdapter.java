package joe.com.cnode.ui.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import joe.com.cnode.R;
import joe.com.cnode.model.entity.Reply;
import joe.com.cnode.model.storage.LoginShared;
import joe.com.cnode.presenter.contract.IReplyPresenter;
import joe.com.cnode.presenter.implement.ReplyPresenter;
import joe.com.cnode.ui.activity.LoginActivity;
import joe.com.cnode.ui.activity.UserDetailActivity;
import joe.com.cnode.ui.util.ToastUtils;
import joe.com.cnode.ui.view.ICreateReplyView;
import joe.com.cnode.ui.view.IReplyView;
import joe.com.cnode.ui.widget.ContentWebView;
import joe.com.cnode.util.FormatUtils;

/**
 * Created by JOE on 2016/9/1.
 */
public class ReplyListAdapter extends BaseAdapter {

    private final Activity activity;

    private final LayoutInflater inflater;

    private final List<Reply> replyList = new ArrayList<>();

    private final Map<String, Integer> positionMap = new HashMap<>();

    private final ICreateReplyView createReplyView;

    public ReplyListAdapter(@NonNull Activity activity, @NonNull ICreateReplyView createReplyView) {
        this.activity = activity;
        this.createReplyView = createReplyView;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    public List<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(@NonNull List<Reply> replyList) {
        this.replyList.clear();
        this.replyList.addAll(replyList);
        positionMap.clear();
        for (int i = 0; i < replyList.size(); i++) {
            Reply reply = replyList.get(i);
            positionMap.put(reply.getId(), i);
        }
    }

    public void addReply(@NonNull Reply reply) {
        replyList.add(reply);
        positionMap.put(reply.getId(), replyList.size() - 1);
    }

    @Override
    public int getCount() {
        return replyList.size();
    }

    @Override
    public Object getItem(int i) {
        return replyList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_reply, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.update(i);
        return view;
    }

    protected class ViewHolder implements IReplyView {
        @BindView(R.id.image_avatar)
        CircleImageView imageAvatar;
        @BindView(R.id.tv_login_name)
        TextView tvLoginName;
        @BindView(R.id.tv_index)
        TextView tvIndex;
        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;
        @BindView(R.id.btn_ups)
        TextView btnUps;
        @BindView(R.id.btn_at)
        ImageView btnAt;
        @BindView(R.id.tv_target_position)
        TextView tvTargetPosition;
        @BindView(R.id.web_content)
        ContentWebView webContent;
        @BindView(R.id.icon_deep_line)
        View iconDeepLine;
        @BindView(R.id.icon_shadow_gap)
        View iconShadowGap;

        private final IReplyPresenter replyPresenter;

        private Reply reply;

        protected ViewHolder(View view) {
            ButterKnife.bind(this, view);
            replyPresenter = new ReplyPresenter(activity, this);
        }

        protected void update(int position) {
            reply = replyList.get(position);
            updateReplyViews(reply, position, positionMap.get(reply.getReplyId()));
        }

        protected void updateReplyViews(Reply reply, int position, Integer targetPosition) {
            Glide.with(activity).load(reply.getAuthor().getAvatarUrl())
                    .placeholder(R.drawable.image_placeholder).dontAnimate().into(imageAvatar);
            tvLoginName.setText(reply.getAuthor().getLoginName());
            tvIndex.setText(activity.getString(R.string.$d_floor, position + 1));
            tvCreateTime.setText(FormatUtils.getRelativeTimeSpanString(reply.getCreateAt()));
            updateUpViews(reply);
            if (targetPosition == null) {
                tvTargetPosition.setVisibility(View.GONE);
            } else {
                tvTargetPosition.setVisibility(View.VISIBLE);
                tvTargetPosition.setText(activity.getString(R.string.reply_$d_floor, targetPosition + 1));
            }

            // 这里直接使用WebView，有性能问题
            webContent.loadRenderedContent(reply.getContentHtml());

            iconDeepLine.setVisibility(position == replyList.size() - 1 ? View.GONE : View.VISIBLE);
            iconShadowGap.setVisibility(position == replyList.size() - 1 ? View.VISIBLE : View.GONE);
        }

        protected void updateUpViews(@NonNull Reply reply) {
            btnUps.setText(String.valueOf(reply.getUpList().size()));
            btnUps.setCompoundDrawablesWithIntrinsicBounds(reply.getUpList().contains(LoginShared.getId(activity)) ?
                    R.drawable.ic_thumb_up_theme_24dp : R.drawable.ic_thumb_up_grey600_24dp, 0, 0, 0);

        }

        @Override
        public void onUpReplyOk(@NonNull Reply reply) {
            if (TextUtils.equals(reply.getId(), this.reply.getId())) {
                updateUpViews(reply);
            }
        }

        @OnClick(R.id.img_avatar)
        protected void onBtnAvatarClick() {
            UserDetailActivity.startWithTransitionAnimation(activity, reply.getAuthor().getLoginName(),
                    imageAvatar, reply.getAuthor().getAvatarUrl());
        }

        @OnClick(R.id.btn_ups)
        protected void onBtnUpsClick() {
            if (LoginActivity.startForResultWithLoginCheck(activity)) {
                if (reply.getAuthor().getLoginName().equals(LoginShared.getLoginName(activity))) {
                    ToastUtils.with(activity).show(R.string.can_not_up_yourself_reply);
                } else {
                    replyPresenter.upReplyAsyncTask(reply);
                }
            }
        }

        @OnClick(R.id.btn_at)
        protected void onBtnAtClick() {
            if (LoginActivity.startForResultWithLoginCheck(activity)) {
                createReplyView.onAt(reply, positionMap.get(reply.getId()));
            }
        }
    }

}
