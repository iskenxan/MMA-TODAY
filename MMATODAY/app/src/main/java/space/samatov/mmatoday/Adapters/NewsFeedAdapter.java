package space.samatov.mmatoday.Adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.model.Article;
import space.samatov.mmatoday.model.ListItem;
import space.samatov.mmatoday.model.OnListItemClicked;

public class NewsFeedAdapter extends RecyclerView.Adapter {

    private ArrayList<Article> mArticles;

    public NewsFeedAdapter(ArrayList<Article> articles){
        mArticles=articles;
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



    public class  ViewHolder extends RecyclerView.ViewHolder{
        TextView mTitle;
        TextView mBlurb;
        TextView mAuthor;
        ImageView mImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitle= (TextView) itemView.findViewById(R.id.newsFeedTitle);
            mBlurb= (TextView) itemView.findViewById(R.id.newsFeedblurb);
            mAuthor= (TextView) itemView.findViewById(R.id.newsFeedAuthor);
            mImage= (ImageView) itemView.findViewById(R.id.newsFeedImageView);

        }

        public void bindView(int position){
           Article article=mArticles.get(position);

            mTitle.setText(article.getmHeadline());
            mBlurb.setText(article.getmDescription());
            mAuthor.setText("by "+article.getmAuthor());
            Glide.with(itemView.getContext()).load(article.getUrl()).into(mImage);
        }
    }
}
