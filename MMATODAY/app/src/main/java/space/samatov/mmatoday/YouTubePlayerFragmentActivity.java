package space.samatov.mmatoday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import samatov.space.mmatoday.R;
import space.samatov.mmatoday.Fragments.YoutubeFragment;
import space.samatov.mmatoday.model.YoutubeVideo;

public class YouTubePlayerFragmentActivity extends AppCompatActivity {

    private YoutubeVideo mVideo;
    private YoutubeFragment mFragment;
    private TextView mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_player);
        Bundle args=getIntent().getExtras();
        mVideo=args.getParcelable("video");

        mFragment=(YoutubeFragment) getSupportFragmentManager().findFragmentById(R.id.youtubePlayerFragment);
        mTitle= (TextView) findViewById(R.id.youtubePlayerTextView);
        mTitle.setText(mVideo.getmTitle());
        mFragment.setmVideo(mVideo);

    }
}
