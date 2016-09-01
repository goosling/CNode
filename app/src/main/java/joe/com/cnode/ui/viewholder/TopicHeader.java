package joe.com.cnode.ui.viewholder;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import joe.com.cnode.R;
import joe.com.cnode.model.entity.Topic;
import joe.com.cnode.model.entity.TopicWithReply;
import joe.com.cnode.presenter.contract.ITopicHeaderPresenter;
import joe.com.cnode.presenter.implement.TopicHeaderPresenter;
import joe.com.cnode.ui.activity.LoginActivity;
import joe.com.cnode.ui.activity.UserDetailActivity;
import joe.com.cnode.ui.view.ITopicHeaderView;
import joe.com.cnode.ui.widget.ContentWebView;
import joe.com.cnode.util.FormatUtils;

/**
 * Created by JOE on 2016/8/31.
 */
public class TopicHeader implements ITopicHeaderView {

    private final ITopicHeaderPresenter topicHeaderPresenter;

    private final Activity activity;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_avatar)
    CircleImageView imgAvatar;
    @BindView(R.id.ctv_tab)
    CheckedTextView ctvTab;
    @BindView(R.id.tv_login_name)
    TextView tvLoginName;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_visit_count)
    TextView tvVisitCount;
    @BindView(R.id.btn_favorite)
    ImageView btnFavorite;
    @BindView(R.id.web_content)
    ContentWebView webContent;
    @BindView(R.id.layout_no_reply)
    LinearLayout layoutNoReply;
    @BindView(R.id.tv_reply_count)
    TextView tvReplyCount;
    @BindView(R.id.layout_reply_count)
    LinearLayout layoutReplyCount;
    @BindView(R.id.layout_content)
    LinearLayout layoutContent;
    @BindView(R.id.icon_good)
    ImageView iconGood;

    private Topic topic;

    private boolean isCollect;

    public TopicHeader(@NonNull Activity activity, @NonNull ListView listView) {
        this.activity = activity;
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View headerView = layoutInflater.inflate(R.layout.header_topic, listView, false);
        ButterKnife.bind(this, headerView);
        listView.addHeaderView(headerView, null, false);
        this.topicHeaderPresenter = new TopicHeaderPresenter(activity, this);
    }

    @OnClick({R.id.img_avatar, R.id.btn_favorite})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_avatar:
                UserDetailActivity.startWithTransitionAnimation(activity, topic.getAuthor().getLoginName(),
                        imgAvatar, topic.getAuthor().getAvatarUrl());
                break;
            case R.id.btn_favorite:
                if(topic != null) {
                    if(LoginActivity.startForResultWithLoginCheck(activity)) {
                        if(isCollect) {
                            topicHeaderPresenter.decollectTopicAsyncTask(topic.getId());
                        } else {
                            topicHeaderPresenter.collectTopicAsyncTask(topic.getId());
                        }
                    }
                }
                break;
        }
    }

    public void updateViews(@Nullable Topic topic, boolean isCollect, int replyCount) {
        this.topic = topic;
        this.isCollect = isCollect;
        if(topic != null) {
            layoutContent.setVisibility(View.VISIBLE);
            iconGood.setVisibility(topic.isGood() ? View.VISIBLE : View.GONE);

            tvTitle.setText(topic.getTitle());
            Glide.with(activity).load(topic.getAuthor().getAvatarUrl())
                    .placeholder(R.drawable.image_placeholder)
                    .dontAnimate().into(imgAvatar);
            ctvTab.setText(topic.isTop() ? R.string.tab_top : topic.getTab().getNameId());
            ctvTab.setChecked(topic.isTop());
            tvLoginName.setText(topic.getAuthor().getLoginName());
            tvCreateTime.setText(activity.getString(R.string.$s_create,
                    FormatUtils.getRelativeTimeSpanString(topic.getCreateAt())));
            tvVisitCount.setText(activity.getString(R.string.$d_count_visit, topic.getVisitCount()));
            btnFavorite.setImageResource(isCollect ? R.drawable.ic_favorite_theme_24dp :
                            R.drawable.ic_favorite_outline_grey600_24dp);

            //webContent
            webContent.loadRenderedContent(topic.getContentHtml());

            updateReplyCount(replyCount);
        } else {
            layoutContent.setVisibility(View.GONE);
            iconGood.setVisibility(View.GONE);
        }
    }

    public void updateViews(@NonNull TopicWithReply reply) {
        updateViews(reply, reply.isCollect(), reply.getReplyList().size());
    }

    public void updateReplyCount(int replyCount) {
        layoutNoReply.setVisibility(replyCount > 0 ? View.GONE : View.VISIBLE);
        layoutReplyCount.setVisibility(replyCount > 0 ? View.VISIBLE : View.GONE);
        tvReplyCount.setText(activity.getString(R.string.$d_count_reply, replyCount));
    }

    @Override
    public void onCollectTopicOk() {
        isCollect = true;
        btnFavorite.setImageResource(R.drawable.ic_favorite_theme_24dp);
    }

    @Override
    public void onDecollectTopicOk() {
        isCollect = false;
        btnFavorite.setImageResource(R.drawable.ic_favorite_outline_grey600_24dp);
    }
}
