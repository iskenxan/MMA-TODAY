package space.samatov.mmatoday.Adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.model.Config;
import space.samatov.mmatoday.model.OnYouTubeThumbnailClicked;
import space.samatov.mmatoday.model.YoutubeVideo;

public class YoutubeListAdapter extends RecyclerView.Adapter {
    private ArrayList<YoutubeVideo> mVideoList;
    private ArrayList<OnYouTubeThumbnailClicked> mListeners=new ArrayList<>();

    public void addListeners(OnYouTubeThumbnailClicked listener){
        mListeners.add(listener);
    }

    public YoutubeListAdapter(ArrayList<YoutubeVideo> list){
        mVideoList=list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_thumbnail_item,parent,false);
        ViewHolder holder=new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }


    private class  ViewHolder extends RecyclerView.ViewHolder implements YouTubeThumbnailView.OnInitializedListener,View.OnClickListener{
        YouTubeThumbnailView mThumb;
        TextView mTitle;
        int mCurrentPosition;
        public ViewHolder(View itemView) {
            super(itemView);
            mThumb = (YouTubeThumbnailView) itemView.findViewById(R.id.youtubeThumbnail);
            mTitle= (TextView) itemView.findViewById(R.id.youtubeThumbnailTextView);
            itemView.setOnClickListener(this);
        }


        public void bindView(int position){
            mCurrentPosition=position;
            mThumb.initialize(Config.YOUTUBE_API_KEY,this);
            mTitle.setText(mVideoList.get(position).getmTitle());
        }

        @Override
        public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
            youTubeThumbnailLoader.setVideo(mVideoList.get(mCurrentPosition).getmId());
        }

        @Override
        public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
            String error=youTubeInitializationResult.toString();

            Toast.makeText(youTubeThumbnailView.getContext(),error,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onClick(View view) {
            notifyListeners(mVideoList.get(mCurrentPosition));
        }
    }

    private void notifyListeners(YoutubeVideo video){
     for (OnYouTubeThumbnailClicked listener:mListeners)
         listener.onYouTubeItemClicked(video);
    }
}
