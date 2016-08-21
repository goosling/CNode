package joe.com.cnode.ui.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import joe.com.cnode.R;
import joe.com.cnode.model.entity.TopicSimple;
import joe.com.cnode.ui.util.Navigator;
import joe.com.cnode.util.FormatUtils;

/**
 * Created by JOE on 2016/8/21.
 */
public class TopicSimpleListAdapter extends RecyclerView.Adapter<TopicSimpleListAdapter.ViewHolder> {

    private final Activity activity;

    private final LayoutInflater inflater;

    private final List<TopicSimple> topicSimpleList = new ArrayList<>();


    public TopicSimpleListAdapter(Activity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    public List<TopicSimple> getTopicSimpleList() {
        return topicSimpleList;
    }

    @Override
    public int getItemCount() {
        return topicSimpleList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_topic_simple, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.update(topicSimpleList.get(position));
    }

    @OnClick({R.id.image_avatar, R.id.btn_item})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_avatar:
                break;
            case R.id.btn_item:
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_avatar)
        CircleImageView imageAvatar;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_login_name)
        TextView tvLoginName;
        @BindView(R.id.tv_last_reply_time)
        TextView tvLastReplyTime;

        private TopicSimple topicSimple;

        protected ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void update(@NonNull TopicSimple topicSimple) {
            this.topicSimple = topicSimple;

            tvTitle.setText(topicSimple.getTitle());
            Glide.with(activity).load(topicSimple.getAuthor().getAvatarUrl())
                    .placeholder(R.drawable.image_placeholder).dontAnimate().into(imageAvatar);
            tvLoginName.setText(topicSimple.getAuthor().getLoginName());
            tvLastReplyTime.setText(FormatUtils.getRelativeTimeSpanString(topicSimple.getLastReplyAt()));
        }

        @OnClick({R.id.image_avatar, R.id.btn_item})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.image_avatar:
                    /*UserDetailActivity.
                            startWithTransitionAnimation(activity, topicSimple.getAuthor().getLoginName(),
                                    imageAvatar, topicSimple.getAuthor().getAvatarUrl());*/
                    break;
                case R.id.btn_item:
                    Navigator.TopicWithAutoCompat.start(activity, topicSimple.getId());
                    break;
            }
        }
    }
}
