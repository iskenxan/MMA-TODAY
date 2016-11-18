package space.samatov.mmatoday.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.model.Article;
import space.samatov.mmatoday.model.YoutubeVideo;

public class NewsViewPagerFragment extends Fragment{


    public static final String FRAGMENT_KEY="news_view_pager_fragment";
    private ViewPager mViewPager;
    private FragmentManager fragmentManager;
    private ArrayList<YoutubeVideo> mVideos;
    private ArrayList<Article> mArticles;
    private NewsfeedFragment mNewsFeedFragment;
    private YouTubeNewsFragment mYoutubeNewsFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_news_view_pager,container,false);
        mViewPager= (ViewPager) view.findViewById(R.id.news_view_pager);
        fragmentManager=getChildFragmentManager();


        mNewsFeedFragment= (NewsfeedFragment) fragmentManager.findFragmentByTag(NewsfeedFragment.FRAGMENT_KEY);
        mYoutubeNewsFragment= (YouTubeNewsFragment) fragmentManager.findFragmentByTag(YouTubeNewsFragment.FRAGMENT_KEY);
        if(mNewsFeedFragment==null&&mYoutubeNewsFragment==null){
            mArticles=getArguments().getParcelableArrayList(NewsfeedFragment.ARGS_KEY);
            mVideos=getArguments().getParcelableArrayList(YouTubeNewsFragment.ARGS_KEY);
            Bundle args1=new Bundle();
            Bundle args2=new Bundle();
            args1.putParcelableArrayList(NewsfeedFragment.ARGS_KEY,mArticles);
            args2.putParcelableArrayList(YouTubeNewsFragment.ARGS_KEY,mVideos);

            mNewsFeedFragment=new NewsfeedFragment();
            mYoutubeNewsFragment=new YouTubeNewsFragment();

            mNewsFeedFragment.setArguments(args1);
            mYoutubeNewsFragment.setArguments(args2);
        }

        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return position==0?mNewsFeedFragment:mYoutubeNewsFragment;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        return view;
    }
}
