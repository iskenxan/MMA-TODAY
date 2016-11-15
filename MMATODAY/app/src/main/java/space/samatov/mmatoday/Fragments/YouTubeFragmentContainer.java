package space.samatov.mmatoday.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.model.YoutubeVideo;

public class YouTubeFragmentContainer extends Fragment {

    private YoutubeVideo mVideo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_you_tube_player,container,false);
        Bundle args=getArguments();

        mVideo=args.getParcelable("video");

        final YoutubeFragment youtubePlayerFragment=(YoutubeFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.youtubePlayerFragment);
        youtubePlayerFragment.setmVideo(mVideo);


        return view;
    }
}
