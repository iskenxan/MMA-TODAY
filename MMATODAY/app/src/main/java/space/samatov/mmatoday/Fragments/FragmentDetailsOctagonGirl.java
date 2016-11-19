package space.samatov.mmatoday.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.model.OctagonGirl;

public class FragmentDetailsOctagonGirl extends Fragment {
    public static final String FRAGMENT_KEY="octagon_girls_details_fragment";
    public static final String ARGS_KEY="octagon_girl_details";

    private OctagonGirl mGirl;

    private TextView mNameTextView;
    private TextView mBio1TextView;
    private TextView mBio2TextView;
    private TextView mFromTextView;
    private TextView mBornTextView;
    private TextView mHeightTextView;
    private TextView mWeightTextView;
    private TextView mHobbiesTextView;
    private TextView mFoodTextView;
    private TextView mTwitterTextView;
    private ImageView mImageView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_details_octagon_girl,container,false);
        ArrayList<OctagonGirl> argumentList=getArguments().getParcelableArrayList(ARGS_KEY);
        mGirl=argumentList.get(0);

        mNameTextView= (TextView) view.findViewById(R.id.NameTextViewOctagonGirlsDetails);
        mBio1TextView=(TextView) view.findViewById(R.id.Bio1TextViewOctagonGirlDetails);
        mBio2TextView=(TextView) view.findViewById(R.id.Bio2TextViewOctagonGirlDetails);
        mFromTextView=(TextView) view.findViewById(R.id.FromTextViewOctagonGirlsDetails);
        mBornTextView=(TextView) view.findViewById(R.id.BornTextViewOctagonGirlsDetails);
        mHeightTextView=(TextView) view.findViewById(R.id.HeightTextViewOctagonGirlsDetails);
        mWeightTextView=(TextView) view.findViewById(R.id.WeightTextViewOctagonGirlsDetails);
        mHobbiesTextView=(TextView) view.findViewById(R.id.HobbiesTextViewOctagonGirlsDetails);
        mFoodTextView=(TextView) view.findViewById(R.id.FavoriteFoodTextViewOctagonGirlsDetails);
        mTwitterTextView=(TextView) view.findViewById(R.id.TwitterUsernameTextViewOctagonGirlsDetails);
        mImageView= (ImageView) view.findViewById(R.id.BackgroundImageViewOctagonGirlDetails);

        Glide.with(getContext()).load(mGirl.getmBodyPic()).into(mImageView);
        mNameTextView.setText(mGirl.getmFirst()+" "+mGirl.getmLast());
        mBio1TextView.setText(mGirl.getmBio1());
        mBio2TextView.setText(mGirl.getmBio2());
        mFromTextView.setText(mGirl.getmCity()+","+mGirl.getmCountry());
        mBornTextView.setText(mGirl.getmBirthDate());
        mHeightTextView.setText(mGirl.getmHeight()+"");
        mWeightTextView.setText(mGirl.getmWeight()+"");
        mHobbiesTextView.setText(mGirl.getmHobbies());
        mFoodTextView.setText(mGirl.getmFavoriteFood());
        mTwitterTextView.setText(mGirl.getmTwitterUsername());


        return view;
    }
}
