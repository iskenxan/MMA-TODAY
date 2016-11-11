package space.samatov.mmatoday.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.Adapters.NewsFeedAdapter;
import space.samatov.mmatoday.model.Article;

public class NewsfeedFragment extends Fragment {

    public static final String FRAGMENT_KEY="news_feed_fragment";


    ArrayList<Article> mArticles;
    RecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_list,container,false);
        mArticles=getArguments().getParcelableArrayList("newsfeed");
        mRecyclerView= (RecyclerView) view.findViewById(R.id.listRecyclerView);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        NewsFeedAdapter adapter=new NewsFeedAdapter(mArticles);
        mRecyclerView.setAdapter(adapter);

        return view;
    }
}
