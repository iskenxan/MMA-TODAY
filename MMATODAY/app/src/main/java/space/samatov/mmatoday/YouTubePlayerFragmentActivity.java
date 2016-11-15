package space.samatov.mmatoday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import samatov.space.mmatoday.R;
import space.samatov.mmatoday.Fragments.YoutubeFragment;
import space.samatov.mmatoday.model.YoutubeVideo;

public class YouTubePlayerFragmentActivity extends AppCompatActivity {

    private YoutubeVideo mVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_player);
        Bundle args=getIntent().getExtras();
        mVideo=args.getParcelable("video");

        final YoutubeFragment youtubePlayerFragment=(YoutubeFragment) getSupportFragmentManager().findFragmentById(R.id.youtubePlayerFragment);
        youtubePlayerFragment.setmVideo(mVideo);

    }
}
