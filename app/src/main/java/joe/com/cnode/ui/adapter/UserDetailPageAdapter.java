package joe.com.cnode.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import joe.com.cnode.model.entity.Topic;
import joe.com.cnode.model.entity.TopicSimple;
import joe.com.cnode.model.entity.User;
import joe.com.cnode.ui.fragment.UserDetailItemFragment;

/**
 * Created by JOE on 2016/8/22.
 */
public class UserDetailPagerAdapter extends FragmentPagerAdapter {

    private final List<UserDetailItemFragment> fmList = new ArrayList<>();
    private final String[] titles = {
            "最近回复",
            "最新发布",
            "话题收藏"
    };

    public UserDetailPagerAdapter(@NonNull FragmentManager manager) {
        super(manager);
        fmList.add(new UserDetailItemFragment());
        fmList.add(new UserDetailItemFragment());
        fmList.add(new UserDetailItemFragment());
    }

    public void update(@NonNull User user) {
        fmList.get(0).notifyDataSetChanged(user.getRecentReplyList());
        fmList.get(1).notifyDataSetChanged(user.getRecentTopicList());
    }

    public void update(@NonNull List<Topic> topicList) {
        List<TopicSimple> topicSimpleList = new ArrayList<>();
        for (Topic topic : topicList) {
            topicSimpleList.add(topic);
        }
        fmList.get(2).notifyDataSetChanged(topicSimpleList);
    }

    @Override
    public Fragment getItem(int position) {
        //return fmList.get(position);
        return null;
    }

    @Override
    public int getCount() {
        return fmList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
