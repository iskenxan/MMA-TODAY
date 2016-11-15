package space.samatov.mmatoday;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;

import java.util.ArrayList;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.Fragments.AllTimeRanksFragment;
import space.samatov.mmatoday.Fragments.ArticleDetailsFragment;
import space.samatov.mmatoday.Fragments.FighterDetailsFragment;
import space.samatov.mmatoday.Fragments.LoadingFragment;
import space.samatov.mmatoday.Fragments.NewsfeedFragment;
import space.samatov.mmatoday.Fragments.ViewPagerFragment;
import space.samatov.mmatoday.Fragments.YouTubeNewsFragment;
import space.samatov.mmatoday.model.Database;
import space.samatov.mmatoday.model.Fighter;
import space.samatov.mmatoday.model.NewsReader;
import space.samatov.mmatoday.model.OnListItemClicked;
import space.samatov.mmatoday.model.OnNewsFeedItemClicked;
import space.samatov.mmatoday.model.OnYouTubeThumbnailClicked;
import space.samatov.mmatoday.model.YoutubeVideo;

public class MainActivity extends AppCompatActivity implements OnYouTubeThumbnailClicked, OnNewsFeedItemClicked,
        Database.DataListener, OnListItemClicked, Database.AllTimeDataListener,NewsReader.NewsFeedListener {
    private Database mDatabase;
    private NewsReader mNewsReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        LoadingFragment loadingFragment = new LoadingFragment();
        setContentView(R.layout.activity_main);

        fragmentManager.beginTransaction().add(R.id.mainPlaceholder, loadingFragment, LoadingFragment.FRAGMENT_KEY).commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase = new Database();
        mDatabase.addListener(this);
        mDatabase.addAllTimeRankListener(this);

        mNewsReader=new NewsReader();
        mNewsReader.addListener(this);

       DisplayYoutubeVideoList();
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
                if (isConnected())
                    startViewPagerFragment();
                else
                    DisplayErrorMessage();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startViewPagerFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        ViewPagerFragment savedFragmentInstance = (ViewPagerFragment) fragmentManager.findFragmentByTag(ViewPagerFragment.FRAGMENT_KEY);
        if (savedFragmentInstance == null) {
            ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("fighters", mDatabase.mUFCfighters);
            viewPagerFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.mainPlaceholder, viewPagerFragment, ViewPagerFragment.FRAGMENT_KEY).commit();
        }
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

    @Override
    public void onDataReceived() {
        mDatabase.readAllTimeRanks();
    }

    @Override
    public void onDataFailed() {
        DisplayErrorMessage();
    }

    @Override
    public void OnListItemSelected(Fighter fighter) {
        FighterDetailsFragment fragment = new FighterDetailsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        Bundle args = new Bundle();
        args.putParcelable("fighter", fighter);
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.mainPlaceholder, fragment, FighterDetailsFragment.FRAGMENT_KEY)
                .addToBackStack(null).commit();
    }


    @Override
    public void OnAllTimeDataReceived() {
     startAllTimeRankFragment();
    }

    public void startAllTimeRankFragment(){
        AllTimeRanksFragment fragment=new AllTimeRanksFragment();

        FragmentManager fragmentManager=getSupportFragmentManager();
        Bundle args=new Bundle();
        args.putParcelableArrayList("fighters",mDatabase.mAllTimeFighters);

        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.mainPlaceholder,fragment,AllTimeRanksFragment.FRAGMENT_KEY).commit();
    }

    @Override
    public void OnNewsFeedReceived() {
        FragmentManager fragmentManager=getSupportFragmentManager();
        NewsfeedFragment newsfeedFragment=new NewsfeedFragment();
        Bundle args=new Bundle();
        args.putParcelableArrayList("news_feed",mNewsReader.mNewsFeed);
        newsfeedFragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.mainPlaceholder,newsfeedFragment,NewsfeedFragment.FRAGMENT_KEY).commit();
    }

    @Override
    public void OnNewsFeedItemClicked(int position) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        ArticleDetailsFragment fragment=new ArticleDetailsFragment();
        Bundle args=new Bundle();
        args.putParcelable("article",mNewsReader.mNewsFeed.get(position));
        fragment.setArguments(args);

        fragmentManager.beginTransaction().replace(R.id.mainPlaceholder,fragment,ArticleDetailsFragment.FRAGMENT_KEY).addToBackStack(null).commit();
    }

    public void DisplayYoutubeVideoList(){
         YouTubeInitializationResult mResult= YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this);
        if(mResult==YouTubeInitializationResult.SUCCESS)
            {
                YouTubeNewsFragment fragment = new YouTubeNewsFragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.mainPlaceholder, fragment).addToBackStack(null).commit();
            }
            else
            Toast.makeText(this,"Yotutube app is not installed",Toast.LENGTH_LONG).show();
    }


    @Override
    public void onYouTubeItemClicked(YoutubeVideo video) {
        Intent intent=new Intent(this,YouTubePlayerFragmentActivity.class);

        intent.putExtra("video",video);
        startActivity(intent);
    }
}
