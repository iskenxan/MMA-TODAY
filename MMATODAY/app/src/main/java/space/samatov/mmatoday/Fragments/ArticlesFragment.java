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
import space.samatov.mmatoday.Adapters.ArticlesAdapter;
import space.samatov.mmatoday.model.Article;
import space.samatov.mmatoday.model.interfaces.NewsFeedItemClicked;

public class ArticlesFragment extends Fragment {

    public static final String FRAGMENT_KEY="news_feed_fragment";
    public static final String ARGS_KEY="news_feed_list";

    ArrayList<Article> mArticles;
    RecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_recycler,container,false);
        mArticles=getArguments().getParcelableArrayList(ARGS_KEY);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.recyclerViewOnly);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        ArticlesAdapter adapter=new ArticlesAdapter(mArticles,(NewsFeedItemClicked)getActivity());

        mRecyclerView.setAdapter(adapter);

        return view;
    }
}
