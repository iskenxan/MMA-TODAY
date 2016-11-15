package space.samatov.mmatoday.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.model.Article;

public class ArticleDetailsFragment  extends Fragment{


    public static final String FRAGMENT_KEY="article_details_fragment";
    private Article mArticle;
    private ImageView mImageView;
    private TextView mAuthorTextView;
    private TextView mTittleTextView;
    private TextView mContentTextView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_article_details,container,false);
        Bundle args=getArguments();
        mArticle=args.getParcelable("article");
        mImageView= (ImageView) view.findViewById(R.id.articleImageView);
        mAuthorTextView= (TextView) view.findViewById(R.id.articleAuthorTextView);
        mTittleTextView= (TextView) view.findViewById(R.id.articleTittleTextView);
        mContentTextView= (TextView) view.findViewById(R.id.articleContentTextView);
        Glide.with(getActivity()).load(mArticle.getmImageUrl()).into(mImageView);
        mTittleTextView.setText(mArticle.getmHeadline());
        mAuthorTextView.setText("by "+ mArticle.getmAuthor());
        for (String paragraph:mArticle.getContent()){
            mContentTextView.append(paragraph);
            mContentTextView.append("\n\n");
        }


        return view;
    }
}
