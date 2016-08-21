package joe.com.cnode.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import joe.com.cnode.R;
import joe.com.cnode.model.entity.TopicSimple;
import joe.com.cnode.ui.adapter.TopicSimpleListAdapter;

/**
 * Created by JOE on 2016/8/21.
 */
public class UserDetailItemFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private TopicSimpleListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_detail_item, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TopicSimpleListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
    }

    public void notifyDataSetChanged(List<TopicSimple> topicSimpleList) {
        adapter.getTopicSimpleList().clear();
        adapter.getTopicSimpleList().addAll(topicSimpleList);
        adapter.notifyDataSetChanged();
    }
}
