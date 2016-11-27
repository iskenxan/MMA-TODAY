package space.samatov.mmatoday.Fragments;

import android.os.Bundle;

import java.util.ArrayList;

import space.samatov.mmatoday.model.Article;
import space.samatov.mmatoday.model.YoutubeVideo;

public class NewsFragment extends ViewPagerFragment{


    public static final String FRAGMENT_KEY="news_view_pager_fragment";

    private ArrayList<YoutubeVideo> mVideos;
    private ArrayList<Article> mArticles;

    @Override
    protected void setupChildFragments() {
        mArticles=getArguments().getParcelableArrayList(ArticlesFragment.ARGS_KEY);
        mVideos=getArguments().getParcelableArrayList(YouTubeNewsFragment.ARGS_KEY);
        Bundle args1=new Bundle();
        Bundle args2=new Bundle();
        args1.putParcelableArrayList(ArticlesFragment.ARGS_KEY,mArticles);
        args2.putParcelableArrayList(YouTubeNewsFragment.ARGS_KEY,mVideos);

        ArticlesFragment articlesFragment =new ArticlesFragment();
        YouTubeNewsFragment youTubeNewsFragment=new YouTubeNewsFragment();

        articlesFragment.setArguments(args1);
        youTubeNewsFragment.setArguments(args2);

        mChildFragments.add(articlesFragment);
        mChildFragments.add(youTubeNewsFragment);
    }

    @Override
    protected void setupFragmentTittles() {
        mFragmentTittles.add("Articles");
        mFragmentTittles.add("Videos");
    }
}
