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
    public static final String FRAGMENT_KEY="youtube_list_fragment";
    public static final String ARGS_KEY="youtube_list";


    private RecyclerView mRecyclerView;
    private ArrayList<YoutubeVideo> mVideos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_youtube_news,container,false);

        Bundle  args=getArguments();
        mVideos=args.getParcelableArrayList("videos");
        YoutubeVideo video1=new YoutubeVideo();

        mRecyclerView= (RecyclerView) view.findViewById(R.id.youtubeRecyclerView);
        YoutubeListAdapter adapter=new YoutubeListAdapter(mVideos);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.addListeners((OnYouTubeThumbnailClicked) getActivity());
        mRecyclerView.setAdapter(adapter);
        return view;
    }

}
