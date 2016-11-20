package space.samatov.mmatoday.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.model.OctagonGirl;
import space.samatov.mmatoday.model.OnGalleryButtonClicked;

public class FragmentDetailsOctagonGirl extends Fragment implements View.OnClickListener {
    public static final String FRAGMENT_KEY="octagon_girls_details_fragment";
    public static final String ARGS_KEY="octagon_girl_details";

    private OctagonGirl mGirl;

    private TextView mNameTextView;
    private TextView mBio1TextView;
    private TextView mFromTextView;
    private TextView mBornTextView;
    private TextView mHeightTextView;
    private TextView mWeightTextView;
    private TextView mHobbiesTextView;
    private TextView mFoodTextView;
    private TextView mTwitterTextView;
    private ImageView mImageView;
    private Button mGalleryButton;

    private LinearLayout mFromLayout;
    private LinearLayout mFoodLayout;
    private LinearLayout mHobbiesLayout;
    private LinearLayout mTwitterLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_details_octagon_girl,container,false);
        ArrayList<OctagonGirl> argumentList=getArguments().getParcelableArrayList(ARGS_KEY);
        mGirl=argumentList.get(0);

        mNameTextView= (TextView) view.findViewById(R.id.NameTextViewOctagonGirlsDetails);
        mBio1TextView=(TextView) view.findViewById(R.id.BioTextViewOctagonGirlDetails);
        mFromTextView=(TextView) view.findViewById(R.id.FromTextViewOctagonGirlsDetails);
        mBornTextView=(TextView) view.findViewById(R.id.BornTextViewOctagonGirlsDetails);
        mHeightTextView=(TextView) view.findViewById(R.id.HeightTextViewOctagonGirlsDetails);
        mWeightTextView=(TextView) view.findViewById(R.id.WeightTextViewOctagonGirlsDetails);
        mHobbiesTextView=(TextView) view.findViewById(R.id.HobbiesTextViewOctagonGirlsDetails);
        mFoodTextView=(TextView) view.findViewById(R.id.FavoriteFoodTextViewOctagonGirlsDetails);
        mTwitterTextView=(TextView) view.findViewById(R.id.TwitterUsernameTextViewOctagonGirlsDetails);
        mImageView= (ImageView) view.findViewById(R.id.BackgroundImageViewOctagonGirlDetails);
        mFoodLayout= (LinearLayout) view.findViewById(R.id.FavoriteFoodLinearLayoutOctagonGirlsDetails);
        mHobbiesLayout=(LinearLayout) view.findViewById(R.id.HobbiesLinearLayoutOctagonGirlsDetails);
        mFromLayout=(LinearLayout) view.findViewById(R.id.FromLinearLayoutOctagonGirlsDetails);
        mTwitterLayout=(LinearLayout) view.findViewById(R.id.TwitterUserNameLinearLayoutOctagonGirlsDetails);
        mGalleryButton= (Button) view.findViewById(R.id.GalleryButtonOctagonGirlsDetails);

        Glide.with(getContext()).load(mGirl.getmBodyPic()).into(mImageView);
        mNameTextView.setText(mGirl.getmFirst()+" "+mGirl.getmLast());
        mBio1TextView.setText(mGirl.getmBio());
        mBornTextView.setText(mGirl.getmBirthDate());
        mHeightTextView.setText(mGirl.getmHeight()+"");
        mWeightTextView.setText(mGirl.getmWeight()+"");
        mGalleryButton.setOnClickListener(this);

        if(mGirl.getmCountry().isEmpty())
            mFromLayout.setVisibility(View.INVISIBLE);
        else
        mFromTextView.setText(mGirl.getmCity()+","+mGirl.getmCountry());
        if(mGirl.getmHobbies().isEmpty())
            mHobbiesLayout.setVisibility(View.INVISIBLE);
        else
        mHobbiesTextView.setText(mGirl.getmHobbies());
        if(mGirl.getmFavoriteFood().isEmpty())
            mFoodLayout.setVisibility(View.INVISIBLE);
        else
        mFoodTextView.setText(mGirl.getmFavoriteFood());
        if(mGirl.getmTwitterUsername().isEmpty())
            mTwitterLayout.setVisibility(View.INVISIBLE);
        else
        mTwitterTextView.setText(mGirl.getmTwitterUsername());
        return view;
    }


    @Override
    public void onClick(View view) {
        ((OnGalleryButtonClicked)getActivity()).galleryButtonClicked(mGirl);
    }
}
