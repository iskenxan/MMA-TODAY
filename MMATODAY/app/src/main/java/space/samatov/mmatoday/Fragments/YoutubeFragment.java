package space.samatov.mmatoday.Fragments;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import space.samatov.mmatoday.model.YoutubeVideoReader;
import space.samatov.mmatoday.model.YoutubeVideo;

public class YoutubeFragment extends YouTubePlayerSupportFragment implements YouTubePlayer.OnInitializedListener {

    private YoutubeVideo mVideo;


    public static YoutubeFragment newInstance(YoutubeVideo video){
        YoutubeFragment youtubeFragment=new YoutubeFragment();
        Bundle args=new Bundle();
        args.putParcelable("video",video);
        youtubeFragment.setArguments(args);

        return  youtubeFragment;
    }
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle args=getArguments();


        if(bundle!=null&&bundle.containsKey("video"))
            mVideo = bundle.getParcelable("video");
        else if(args!=null&&args.containsKey("video"))
            mVideo=args.getParcelable("video");
        initialize(YoutubeVideoReader.YOUTUBE_API_KEY,this);
    }

    public void setmVideo(YoutubeVideo video){
        mVideo=video;
        initialize(YoutubeVideoReader.YOUTUBE_API_KEY,this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {
        youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);

        youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);

        if(mVideo!=null){
            if(restored)
                youTubePlayer.play();
            else
                youTubePlayer.loadVideo(mVideo.getmId());
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(getActivity(),1).show();
        } else {
            //Handle the failure
            Toast.makeText(getActivity(), "Error Loading the Video", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable("video",mVideo);
    }
}
