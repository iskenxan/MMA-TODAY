package space.samatov.mmatoday;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.util.ArrayList;
import java.util.List;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.Fragments.AllTimeRanksFragment;
import space.samatov.mmatoday.Fragments.ArticleDetailsFragment;
import space.samatov.mmatoday.Fragments.FighterDetailsFragment;
import space.samatov.mmatoday.Fragments.FragmentDetailsOctagonGirl;
import space.samatov.mmatoday.Fragments.LoadingFragment;
import space.samatov.mmatoday.Fragments.NewsFragment;
import space.samatov.mmatoday.Fragments.ArticlesFragment;
import space.samatov.mmatoday.Fragments.NoConnectionDialogFragment;
import space.samatov.mmatoday.Fragments.OctagonGirlsRecyclerViewFragment;
import space.samatov.mmatoday.Fragments.UFCFightersFragment;
import space.samatov.mmatoday.Fragments.YouTubeNewsFragment;
import space.samatov.mmatoday.model.Article;
import space.samatov.mmatoday.model.FighterReader;
import space.samatov.mmatoday.model.Fighter;
import space.samatov.mmatoday.model.interfaces.NewsFeedReceived;
import space.samatov.mmatoday.model.NewsReader;
import space.samatov.mmatoday.model.OctagonGirl;
import space.samatov.mmatoday.model.OctagonGirlsReader;
import space.samatov.mmatoday.model.interfaces.FightersItemClicked;
import space.samatov.mmatoday.model.interfaces.NewsFeedItemClicked;
import space.samatov.mmatoday.model.interfaces.OctagonGirlItemClicked;
import space.samatov.mmatoday.model.interfaces.OctagonGirlsDataReceived;
import space.samatov.mmatoday.model.interfaces.YouTubeThumbnailClicked;
import space.samatov.mmatoday.model.interfaces.YoutubeVideoListLoaded;
import space.samatov.mmatoday.model.YoutubeVideo;
import space.samatov.mmatoday.model.YoutubeVideoReader;

public class MainActivity extends AppCompatActivity implements OctagonGirlItemClicked, OctagonGirlsDataReceived, YoutubeVideoListLoaded, YouTubeThumbnailClicked, NewsFeedItemClicked,
        FighterReader.DataListener, FightersItemClicked, FighterReader.AllTimeDataListener,NewsFeedReceived {
    private static final long RIPPLE_DURATION = 250;
    private FighterReader mFighterReader =new FighterReader();
    private NewsReader mNewsReader=new NewsReader();
    private Toolbar mToolbar;
    private YoutubeVideoReader mVideoReader=new YoutubeVideoReader();
    private OctagonGirlsReader mOctagonGirlsReader=OctagonGirlsReader.referenceActivity(this);
    private FrameLayout mRootLayout;
    private int mCurrentMenuChoice=0;
    private View mGuillotine;
    private View mHamburgerButton;
    private GuillotineAnimation.GuillotineBuilder mMenuAnimationBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupMenu();
        restoreActivityState(savedInstanceState);
        if(isConnected()) {
            if (mNewsReader.mNewsFeed == null || mNewsReader.mNewsFeed.size() <= 0) {
                startLoadingFragment();
                mNewsReader.getNewsFeed();
            }
        }
    }

    private void setupMenu(){
        bindMenuNecessaryVariables();
        setupMenuListeners();
        setupMenuFont();
    }

    private void bindMenuNecessaryVariables(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRootLayout= (FrameLayout) findViewById(R.id.rootLayout);
        mGuillotine= LayoutInflater.from(this).inflate(R.layout.guillotine,null);
        mHamburgerButton=  findViewById(R.id.content_hamburger);

    }

    private void setupMenuListeners() {
        mHamburgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupGuillotineMenuAnimation();
            }
        });

        View ufcFightersMenuItem=mGuillotine.findViewById(R.id.ufcFightersMenuItem);
        ufcFightersMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ufcFightersMenuItemClicked();
            }
        });

        View allTimeMenuItem=mGuillotine.findViewById(R.id.allTimeRanksMenuItem);
        allTimeMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allTimeRanksMenuItemClicked();
            }
        });

        View newsMenuItem=mGuillotine.findViewById(R.id.newsMenuItem);
        newsMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsMenuItemClicked();
            }
        });

        View octagonGirlsMenuItem=mGuillotine.findViewById(R.id.octagonGirlsMenuItem);
        octagonGirlsMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                octagonGirlsMenuItemClicked();
            }
        });
    }

    public void setupMenuFont(){
        TextView title= (TextView) findViewById(R.id.titleTextView);
       setFontForTextView(title);
        TextView ufcFightersMenuItem= (TextView) mGuillotine.findViewById(R.id.ufcFightersMenuItem);
        TextView allTimeMenuItem= (TextView) mGuillotine.findViewById(R.id.allTimeRanksMenuItem);
        TextView newsMenuItem= (TextView) mGuillotine.findViewById(R.id.newsMenuItem);
        TextView octagonGirlsMenuItem= (TextView) mGuillotine.findViewById(R.id.octagonGirlsMenuItem);
        setFontForTextView(ufcFightersMenuItem);
        setFontForTextView(allTimeMenuItem);
        setFontForTextView(newsMenuItem);
        setFontForTextView(octagonGirlsMenuItem);
    }

    public void setFontForTextView(TextView textView){
        Typeface custom_font=Typeface.createFromAsset(getAssets(),"fonts/canaro_extra_bold.otf");
        textView.setTypeface(custom_font);
    }

    private void setupGuillotineMenuAnimation(){
        mRootLayout.addView(mGuillotine);
        mMenuAnimationBuilder= new GuillotineAnimation.GuillotineBuilder(mGuillotine, mGuillotine.findViewById(R.id.guillotine_hamburger), mHamburgerButton)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(mToolbar);
        mMenuAnimationBuilder.build();
    }


    public void closeGuillotineMenu(){
        mMenuAnimationBuilder.setStartDelay(RIPPLE_DURATION).build().close();
    }

    public void restoreActivityState(Bundle savedInstanceState){
        if(savedInstanceState!=null) {
            mFighterReader.mUFCfighters = savedInstanceState.getParcelableArrayList(FighterReader.UFC_FIGHTERS_KEY);
            mFighterReader.mAllTimeFighters = savedInstanceState.getParcelableArrayList(FighterReader.ALL_TIME_FIGHTERS_KEY);
            mNewsReader.mNewsFeed = savedInstanceState.getParcelableArrayList(NewsReader.NEWS_FEED_KEY);
            mVideoReader.mVideos = savedInstanceState.getParcelableArrayList(YoutubeVideoReader.YOUTUBE_VIDEOS_KEY);
            mOctagonGirlsReader.mOctagonGirls = savedInstanceState.getParcelableArrayList(OctagonGirlsReader.OCT_GIRLS_KEY);
        }
        mFighterReader.addListener(this);
        mFighterReader.addAllTimeRankListener(this);
        mNewsReader.addListener(this);
        mVideoReader.addListener(this);
        mOctagonGirlsReader.addListener(this);
    }



    public boolean checkIfListEmpty(List list){
        if(list!=null&&list.size()>0)
            return true;
        else
            return false;
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo==null)
            showNoConnectionMessage();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showNoConnectionMessage() {
        NoConnectionDialogFragment fragment =new NoConnectionDialogFragment();
        fragment.show(getSupportFragmentManager(),NoConnectionDialogFragment.FRAGMENT_KEY);
    }

    public void startLoadingFragment(){
        LoadingFragment loadingFragment=new LoadingFragment();
        startFragment(loadingFragment,null,null,null,LoadingFragment.ARGS_KEY,LoadingFragment.FRAGMENT_KEY);
    }

    public void startUFCListFragment() {
        UFCFightersFragment UFCFightersFragment = new UFCFightersFragment();
        startFragment(UFCFightersFragment, mFighterReader.mUFCfighters,null, UFCFightersFragment.ARGS_KEY,null, UFCFightersFragment.FRAGMENT_KEY);
    }
    public void startAllTimeListFragment(){
        AllTimeRanksFragment fragment=new AllTimeRanksFragment();
        startFragment(fragment, mFighterReader.mAllTimeFighters,null,AllTimeRanksFragment.ARGS_KEY,null,AllTimeRanksFragment.FRAGMENT_KEY);
    }

    private void startNewsFragment(){
        NewsFragment fragment=new NewsFragment();
        startFragment(fragment,mNewsReader.mNewsFeed,mVideoReader.mVideos, ArticlesFragment.ARGS_KEY,YouTubeNewsFragment.ARGS_KEY, NewsFragment.FRAGMENT_KEY);
    }

    public void startFighterDetailsFragment(Fighter fighter){
        FighterDetailsFragment fragment = new FighterDetailsFragment();
        ArrayList<Fighter> currentfighter=new ArrayList<>();
        currentfighter.add(fighter);
        startFragment(fragment,currentfighter,null,FighterDetailsFragment.ARGS_KEY,null,FighterDetailsFragment.FRAGMENT_KEY);
    }

    public void startArticleDetailsFragment(int position){
        ArticleDetailsFragment fragment=new ArticleDetailsFragment();
        Article article=mNewsReader.mNewsFeed.get(position);
        ArrayList<Article> articleArrayList=new ArrayList<>();
        articleArrayList.add(article);
        startFragment(fragment,articleArrayList,null,ArticleDetailsFragment.ARGS_KEY,null,ArticleDetailsFragment.FRAGMENT_KEY);
    }

    private void startOctagonGirlsListFragment(){
        OctagonGirlsRecyclerViewFragment fragment=new OctagonGirlsRecyclerViewFragment();
        startFragment(fragment,mOctagonGirlsReader.mOctagonGirls,null,OctagonGirlsRecyclerViewFragment.ARGS_KEY,null,OctagonGirlsRecyclerViewFragment.FRAGMENT_KEY);
    }

    public void startYouTubePlayer(YoutubeVideo video){
        Intent intent=new Intent(this,YouTubePlayerFragmentActivity.class);
        intent.putExtra("video",video);
        startActivity(intent);
    }


    @Override
    public void onFightersDataReceived() {
        if(mCurrentMenuChoice==R.id.ufcFightersMenuItem)
        startUFCListFragment();
        else if(mCurrentMenuChoice==R.id.allTimeRanksMenuItem)
        mFighterReader.getAllTimeRanks();
    }

    @Override
    public void onDataFailed() {
        showNoConnectionMessage();
    }

    @Override
    public void OnFighterListItemSelected(Fighter fighter) {
        startFighterDetailsFragment(fighter);
    }

    @Override
    public void OnAllTimeDataReceived(){
       startAllTimeListFragment();
    }



    @Override
    public void OnNewsFeedReceived(){
        mVideoReader.readYouTubeChannel();
    }


    @Override
    public void OnNewsFeedItemClicked(int position) {
        startArticleDetailsFragment(position);
    }


    @Override
    public void onYouTubeItemClicked(YoutubeVideo video) {
     startYouTubePlayer(video);
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


    @Override
    public void OnOctagonGirlsDataReceived() {
        startOctagonGirlsListFragment();
    }


    @Override
    public void OnItemClickedOctagonGirl(int position) {
        FragmentDetailsOctagonGirl fragment=new FragmentDetailsOctagonGirl();
        ArrayList<OctagonGirl> argumentList=new ArrayList<>();
        argumentList.add(mOctagonGirlsReader.mOctagonGirls.get(position));
        startFragment(fragment,argumentList,null,FragmentDetailsOctagonGirl.ARGS_KEY,null,FragmentDetailsOctagonGirl.FRAGMENT_KEY);
    }


    private void startFragment(final Fragment fragment, ArrayList args, ArrayList args2, String args_key, String args2_key, final String fragment_key){
        final FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment savedInstance=fragmentManager.findFragmentByTag(fragment_key);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(args_key, args);
            if(args2!=null)
            {
                bundle.putParcelableArrayList(args2_key,args2);
            }
            fragment.setArguments(bundle);
        //if program was closed unexpectedly but activity is still callling commit
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!(fragment instanceof LoadingFragment)) {
                        mToolbar.setVisibility(View.VISIBLE);
                        LoadingFragment currentFragment=(LoadingFragment)getSupportFragmentManager().findFragmentByTag(LoadingFragment.FRAGMENT_KEY);

                        // if current running fragment is not a loading fragment than add it to back stack , otherwise don't
                        if(currentFragment==null)
                            fragmentManager.beginTransaction().replace(R.id.mainPlaceholder,fragment, fragment_key).addToBackStack(null).commit();
                        else
                            fragmentManager.beginTransaction().replace(R.id.mainPlaceholder,fragment, fragment_key).commit();
                    }
                    else {
                        mToolbar.setVisibility(View.GONE);
                        fragmentManager.beginTransaction().replace(R.id.mainPlaceholder,fragment, fragment_key).commit();
                    }
                }
            });
        }
        catch (IllegalStateException e){}
    }


    private void ufcFightersMenuItemClicked(){
        if(isConnected()) {
            mCurrentMenuChoice = R.id.ufcFightersMenuItem;
           closeGuillotineMenu();
            getUFCDataOrStartFragment();
        }
    }
    private void getUFCDataOrStartFragment(){
        boolean validList = checkIfListEmpty(mFighterReader.mUFCfighters);
        if (validList)
            startUFCListFragment();
        else {
            startLoadingFragment();
            mFighterReader.getFightersData();
        }
    }

    public void  allTimeRanksMenuItemClicked(){
        if(isConnected()){
            mCurrentMenuChoice=R.id.allTimeRanksMenuItem;
           closeGuillotineMenu();
            getAllTimeDataOrStartFragment();
        }
    }

    private void getAllTimeDataOrStartFragment(){
        boolean validUFCList=checkIfListEmpty(mFighterReader.mUFCfighters);
        boolean validAllTimeList=checkIfListEmpty(mFighterReader.mAllTimeFighters);
        if(validUFCList&&validAllTimeList)
            startAllTimeListFragment();
        else if(validUFCList)
        {
            startLoadingFragment();
            mFighterReader.getAllTimeRanks();
        }
        else
        {
            startLoadingFragment();
            mFighterReader.getFightersData();
        }
    }

    private void newsMenuItemClicked(){
        if(isConnected()) {
           closeGuillotineMenu();
            getNewsDataOrStartFragment();
        }
    }

    private void getNewsDataOrStartFragment(){
        mCurrentMenuChoice = R.id.newsMenuItem;
        boolean validArticleList = checkIfListEmpty(mNewsReader.mNewsFeed);
        boolean validVideoList = checkIfListEmpty(mVideoReader.mVideos);
        if (validArticleList && validVideoList)
            startNewsFragment();
        else{
            startLoadingFragment();
            mNewsReader.getNewsFeed();
        }

    }

    private void octagonGirlsMenuItemClicked(){
        if(isConnected()){
          closeGuillotineMenu();
            getOctagonGirlsDataOrStartFragment();
        }
    }

    private void getOctagonGirlsDataOrStartFragment(){
        mCurrentMenuChoice=R.id.octagonGirlsMenuItem;
        boolean validGirlsList=checkIfListEmpty(mOctagonGirlsReader.mOctagonGirls);
        if(validGirlsList)
            startOctagonGirlsListFragment();
        else
            mOctagonGirlsReader.getOctagonGirls();
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        saveActivityData(outState,FighterReader.UFC_FIGHTERS_KEY,mFighterReader.mUFCfighters);
        saveActivityData(outState,FighterReader.ALL_TIME_FIGHTERS_KEY,mFighterReader.mAllTimeFighters);
        saveActivityData(outState,NewsReader.NEWS_FEED_KEY,mNewsReader.mNewsFeed);
        saveActivityData(outState,YoutubeVideoReader.YOUTUBE_VIDEOS_KEY,mVideoReader.mVideos);
        saveActivityData(outState,OctagonGirlsReader.OCT_GIRLS_KEY,mOctagonGirlsReader.mOctagonGirls);

        super.onSaveInstanceState(outState);
    }

    private void saveActivityData(Bundle bundle, String key, ArrayList list){
        if(list!=null){
            bundle.putParcelableArrayList(key,list);
        }
    }


}
