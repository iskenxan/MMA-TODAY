package space.samatov.mmatoday;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.Fragments.AllTimeRanksFragment;
import space.samatov.mmatoday.Fragments.ArticleDetailsFragment;
import space.samatov.mmatoday.Fragments.FighterDetailsFragment;
import space.samatov.mmatoday.Fragments.LoadingFragment;
import space.samatov.mmatoday.Fragments.NewsViewPagerFragment;
import space.samatov.mmatoday.Fragments.NewsfeedFragment;
import space.samatov.mmatoday.Fragments.UFCFightersViewPagerFragment;
import space.samatov.mmatoday.Fragments.YouTubeNewsFragment;
import space.samatov.mmatoday.model.Article;
import space.samatov.mmatoday.model.FighterReader;
import space.samatov.mmatoday.model.Fighter;
import space.samatov.mmatoday.model.NewsReader;
import space.samatov.mmatoday.model.OctagonGirlsReader;
import space.samatov.mmatoday.model.OnListItemClicked;
import space.samatov.mmatoday.model.OnNewsFeedItemClicked;
import space.samatov.mmatoday.model.OnOctagonGirlsDataReceived;
import space.samatov.mmatoday.model.OnYouTubeThumbnailClicked;
import space.samatov.mmatoday.model.OnYoutubeVideoListLoaded;
import space.samatov.mmatoday.model.YoutubeVideo;
import space.samatov.mmatoday.model.YoutubeVideoReader;

public class MainActivity extends AppCompatActivity implements OnOctagonGirlsDataReceived, OnYoutubeVideoListLoaded, OnYouTubeThumbnailClicked, OnNewsFeedItemClicked,
        FighterReader.DataListener, OnListItemClicked, FighterReader.AllTimeDataListener,NewsReader.NewsFeedListener {
    private FighterReader mFighterReader =new FighterReader();
    private NewsReader mNewsReader=new NewsReader();
    private Toolbar mToolbar;
    private YoutubeVideoReader mVideoReader=new YoutubeVideoReader();
    private OctagonGirlsReader mOctagonGirlsReader=new OctagonGirlsReader();

    private int mCurrentMenuChoice=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoadingFragment loadingFragment=new LoadingFragment();
        startFragment(loadingFragment,null,null,null,LoadingFragment.ARGS_KEY,LoadingFragment.FRAGMENT_KEY);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setVisibility(View.INVISIBLE);
        mFighterReader.addListener(this);
        mFighterReader.addAllTimeRankListener(this);
        mNewsReader.addListener(this);
        mVideoReader.addListener(this);
        mOctagonGirlsReader.addListener(this);
        mOctagonGirlsReader.getOctagonGirls();
        //mNewsReader.getNewsFeed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.ufcFightersListItem: {
                mCurrentMenuChoice=R.id.ufcFightersListItem;
                if (isConnected()) {
                    if(mFighterReader.mUFCfighters!=null&& mFighterReader.mUFCfighters.size()>0)
                   startUFCListFragment();
                    else
                        mFighterReader.getFightersData();
                }
                else
                    DisplayErrorMessage();
                return true;
            }
            case R.id.allTimeRanksItem: {
                mCurrentMenuChoice=R.id.allTimeRanksItem;
                if (isConnected()) {
                    if(mFighterReader.mAllTimeFighters!=null&& mFighterReader.mAllTimeFighters.size()>0)
                        startAllTimeRankFragment();
                    else if(mFighterReader.mUFCfighters!=null&& mFighterReader.mUFCfighters.size()>0)
                        mFighterReader.readAllTimeRanks();
                    else
                        mFighterReader.getFightersData();
                }
                else
                    DisplayErrorMessage();
                return true;
        }
            case R.id.newsItem:{
                if(isConnected()){
                    mCurrentMenuChoice=R.id.newsItem;
                    if((mNewsReader.mNewsFeed!=null&&mNewsReader.mNewsFeed.size()>0)&&(mVideoReader.mVideos!=null&&mVideoReader.mVideos.size()>0))
                        startNewsFragment();
                    if(mNewsReader.mNewsFeed==null||mNewsReader.mNewsFeed.size()<=0)
                        mNewsReader.getNewsFeed();
                }
                else
                    DisplayErrorMessage();
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startUFCListFragment() {
        UFCFightersViewPagerFragment UFCFightersViewPagerFragment = new UFCFightersViewPagerFragment();
        startFragment(UFCFightersViewPagerFragment, mFighterReader.mUFCfighters,null,UFCFightersViewPagerFragment.ARGS_KEY,null, UFCFightersViewPagerFragment.FRAGMENT_KEY);
    }


    public boolean isConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void DisplayErrorMessage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Error retrieving data from the server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startNewsFragment(){
        NewsViewPagerFragment fragment=new NewsViewPagerFragment();
        startFragment(fragment,mNewsReader.mNewsFeed,mVideoReader.mVideos,NewsfeedFragment.ARGS_KEY,YouTubeNewsFragment.ARGS_KEY,NewsfeedFragment.FRAGMENT_KEY);
    }

    @Override
    public void onDataReceived() {
        if(mCurrentMenuChoice==R.id.ufcFightersListItem)
        startUFCListFragment();
        else if(mCurrentMenuChoice==R.id.allTimeRanksItem)
        mFighterReader.readAllTimeRanks();
    }

    @Override
    public void onDataFailed() {
        DisplayErrorMessage();
    }

    @Override
    public void OnListItemSelected(Fighter fighter) {
        startFighterDetailsFragment(fighter);
    }

    public void startFighterDetailsFragment(Fighter fighter){
        FighterDetailsFragment fragment = new FighterDetailsFragment();
        ArrayList<Fighter> currentfighter=new ArrayList<>();
        currentfighter.add(fighter);
        startFragment(fragment,currentfighter,null,FighterDetailsFragment.ARGS_KEY,null,FighterDetailsFragment.FRAGMENT_KEY);
    }


    @Override
    public void OnAllTimeDataReceived(){
       startAllTimeRankFragment();
    }

    public void startAllTimeRankFragment(){
        AllTimeRanksFragment fragment=new AllTimeRanksFragment();
        startFragment(fragment, mFighterReader.mAllTimeFighters,null,AllTimeRanksFragment.ARGS_KEY,null,AllTimeRanksFragment.FRAGMENT_KEY);
    }

    @Override
    public void OnNewsFeedReceived(){
        mVideoReader.readYouTubeChannel();
    }


    @Override
    public void OnNewsFeedItemClicked(int position) {
        startArticleDetailsFragment(position);
    }

    public void startArticleDetailsFragment(int position){
        ArticleDetailsFragment fragment=new ArticleDetailsFragment();
        Article article=mNewsReader.mNewsFeed.get(position);
        ArrayList<Article> articleArrayList=new ArrayList<>();
        articleArrayList.add(article);
        startFragment(fragment,articleArrayList,null,ArticleDetailsFragment.ARGS_KEY,null,ArticleDetailsFragment.FRAGMENT_KEY);
    }

    @Override
    public void onYouTubeItemClicked(YoutubeVideo video) {
        Intent intent=new Intent(this,YouTubePlayerFragmentActivity.class);

        intent.putExtra("video",video);
        startActivity(intent);
    }

    @Override
    public void OnVideosLoaded() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startNewsFragment();
            }
        });

    }



    public void startFragment(Fragment fragment, ArrayList args,ArrayList args2,String args_key,String args2_key, String fragment_key){
        if(!(fragment instanceof LoadingFragment))
            mToolbar.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment savedInstance=fragmentManager.findFragmentByTag(fragment_key);
        if(savedInstance==null) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(args_key, args);
            if(args2!=null)
            {
                bundle.putParcelableArrayList(args2_key,args2);
            }
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.mainPlaceholder,fragment, fragment_key).addToBackStack(null).commit();
        }
        else
            fragmentManager.beginTransaction().replace(R.id.mainPlaceholder,savedInstance,fragment_key).addToBackStack(null).commit();
    }

    @Override
    public void OnOctagonGirlsDataReceived() {

    }

    private void startOctagonGirlsListFragment(){

    }
}
