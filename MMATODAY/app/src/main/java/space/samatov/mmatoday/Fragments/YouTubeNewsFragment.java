package space.samatov.mmatoday.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.Adapters.YoutubeListAdapter;
import space.samatov.mmatoday.model.OnYouTubeThumbnailClicked;
import space.samatov.mmatoday.model.YoutubeVideo;

public class YouTubeNewsFragment  extends Fragment
{

    private RecyclerView mRecyclerView;
    private ArrayList<YoutubeVideo> mVideos;

    public YouTubeNewsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_youtube_news,container,false);

            mRecyclerView= (RecyclerView) view.findViewById(R.id.youtubeRecyclerView);
            mVideos=new ArrayList<>();
            YoutubeVideo youtubeVideo1=new YoutubeVideo();
            youtubeVideo1.setmId("DsdDKCdOt2w");
            youtubeVideo1.setmTitle("I want to fight with Dana's Son Conor McGregor,Tyron Woodley vs Stephen Thompson Rematch will happen");
            YoutubeVideo youtubeVideo2=new YoutubeVideo();
            youtubeVideo2.setmId("3bQ4YjCf4b0");
            youtubeVideo2.setmTitle("Conor McGregor makes his Big Announcement,Dana on Khabib vs Conor,Eddie-I Fought Foolishly");

            mVideos.add(youtubeVideo1);
            mVideos.add(youtubeVideo2);

            YoutubeListAdapter adapter=new YoutubeListAdapter(mVideos);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.addListeners((OnYouTubeThumbnailClicked) getActivity());
            mRecyclerView.setAdapter(adapter);
        return view;
    }

}
