package space.samatov.mmatoday;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.Fragments.FighterDetailsFragment;
import space.samatov.mmatoday.Fragments.List_Fragment;
import space.samatov.mmatoday.Fragments.ViewPagerFragment;
import space.samatov.mmatoday.model.Database;
import space.samatov.mmatoday.model.FighterStats;
import space.samatov.mmatoday.model.OnListItemClicked;

public class MainActivity extends AppCompatActivity implements Database.DataListener,OnListItemClicked {
    private Database mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase=new Database();
        mDatabase.addListener(this);
        if(isConnected())
        mDatabase.getFightersData();
        else
            DisplayErrorMessage();
        FighterStats stats=new FighterStats();
        stats.setmFighterId(1161);
        mDatabase.readFighterStatsHtml(stats);
    }

    public void startViewPagerFragment(){

        FragmentManager fragmentManager=getSupportFragmentManager();
        ViewPagerFragment savedFragmentInstance= (ViewPagerFragment) fragmentManager.findFragmentByTag(ViewPagerFragment.FRAGMENT_KEY);
        if(savedFragmentInstance==null) {
            ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("fighters", mDatabase.mFighters);
            viewPagerFragment.setArguments(bundle);
            fragmentManager.beginTransaction().add(R.id.mainPlaceholder, viewPagerFragment, ViewPagerFragment.FRAGMENT_KEY).commit();
        }
    }


    public boolean isConnected(){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    private void DisplayErrorMessage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,"Error retrieving data from the server",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDataReceived() {
        startViewPagerFragment();
    }

    @Override
    public void onDataFailed() {
        DisplayErrorMessage();
    }

    @Override
    public void OnListItemSelected(int position) {
        FighterDetailsFragment fragment=new FighterDetailsFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();

        Bundle args=new Bundle();
        args.putInt("fighter_position",position);
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.mainPlaceholder,fragment,FighterDetailsFragment.FRAGMENT_KEY)
                .addToBackStack(null).commit();

    }
}
