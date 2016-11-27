package space.samatov.mmatoday.Adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.model.Article;
import space.samatov.mmatoday.model.interfaces.NewsFeedItemClicked;

public class ArticlesAdapter extends RecyclerView.Adapter {

    private ArrayList<Article> mArticles;
    private ArrayList<NewsFeedItemClicked> mListeners=new ArrayList<>();

    public ArticlesAdapter(ArrayList<Article> articles, NewsFeedItemClicked listener){
        mArticles=articles;
        mListeners.add(listener);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_feed_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bindView(position);

    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }



    public class  ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTitle;
        ImageView mImage;
        private int mIndex;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitle= (TextView) itemView.findViewById(R.id.newsFeedTitleTextView);
            mImage= (ImageView) itemView.findViewById(R.id.newsFeedImageView);

        }

        public void bindView(int position){
           Article article=mArticles.get(position);

            mTitle.setText(article.getmHeadline());
            Glide.with(itemView.getContext()).load(article.getmImageUrl()).into(mImage);
            mIndex=position;
        }

        @Override
        public void onClick(View view) {
            notifyListeners(mIndex);
        }
    }

    private void notifyListeners(int position){
        for (NewsFeedItemClicked listener:mListeners)
            listener.OnNewsFeedItemClicked(position);
    }
}
