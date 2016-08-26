package joe.com.cnode.ui.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import joe.com.cnode.R;
import joe.com.cnode.model.entity.Message;
import joe.com.cnode.ui.activity.UserDetailActivity;
import joe.com.cnode.ui.util.Navigator;
import joe.com.cnode.ui.widget.ContentWebView;
import joe.com.cnode.util.FormatUtils;
import joe.com.cnode.util.ResUtils;

/**
 * Created by JOE on 2016/8/25.
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    private final Activity activity;

    private final LayoutInflater inflater;

    private final List<Message> messageList = new ArrayList<>();

    public MessageListAdapter(Activity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    public List<Message> getMessageList() {
        return messageList;
    }

    public void markAllMessageRead() {
        for (Message message : messageList) {
            message.setRead(true);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_message, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.update(messageList.get(position));
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_avatar)
        CircleImageView imgAvatar;
        @BindView(R.id.tv_from)
        TextView tvFrom;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_action)
        TextView tvAction;
        @BindView(R.id.badge_read)
        View badgeRead;
        @BindView(R.id.web_content)
        ContentWebView webContent;
        @BindView(R.id.tv_topic_title)
        TextView tvTopicTitle;
        @BindView(R.id.btn_item)
        LinearLayout btnItem;

        private Message message;

        protected ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void update(@NonNull Message message) {
            this.message = message;

            Glide.with(activity).load(message.getAuthor().getAvatarUrl())
                    .placeholder(R.drawable.image_placeholder).dontAnimate().into(imgAvatar);
            tvFrom.setText(message.getAuthor().getLoginName());
            tvTime.setText(FormatUtils.getRelativeTimeSpanString(message.getCreateAt()));
            tvTime.setTextColor(ResUtils.getThemeAttrColor(activity, message.isRead() ?
                    android.R.attr.textColorSecondary : R.attr.colorAccent));
            badgeRead.setVisibility(message.isRead() ? View.GONE : View.VISIBLE);
            tvTopicTitle.setText(activity.getString(R.string.topic_$s, message.getTopic().getTitle()));

            //判断通知类型
            if (message.getType() == Message.Type.at) {
                if (message.getReply() == null || TextUtils.isEmpty(message.getReply().getId())) {
                    tvAction.setText(R.string.at_you_in_topic);
                    webContent.setVisibility(View.GONE);
                } else {
                    tvAction.setText(R.string.at_you_in_reply);
                    webContent.setVisibility(View.VISIBLE);
                    webContent.loadRenderedContent(message.getReply().getContentHtml());
                }
            } else {
                tvAction.setText(R.string.reply_your_topic);
                webContent.setVisibility(View.VISIBLE);
                webContent.loadRenderedContent(message.getReply().getContentHtml());
            }
        }

        @OnClick({R.id.img_avatar, R.id.btn_item})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_avatar:
                    UserDetailActivity.startWithTransitionAnimation(activity, message.getAuthor().getLoginName(),
                            imgAvatar, message.getAuthor().getAvatarUrl());
                    break;
                case R.id.btn_item:
                    Navigator.TopicWithAutoCompat.start(activity, message.getTopic().getId());
                    break;
            }
        }
    }
}
