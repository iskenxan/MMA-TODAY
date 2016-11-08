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

import samatov.space.mmatoday.R;
import space.samatov.mmatoday.model.Database;
import space.samatov.mmatoday.model.Fighter;
import space.samatov.mmatoday.model.FighterStats;

public class FighterDetailsFragment extends Fragment implements Database.StatsDataListener {
    public static final String FRAGMENT_KEY="fighters_details_fragment";
    private Fighter mFighter;
    private FighterStats mFighterStats;
    private View mView;
    private TextView mFirstTextView;
    private TextView mLastTextView;
    private TextView mNicknameTextView;
    private TextView mRecordsTextView;
    private TextView mAgeTextView;
    private TextView mWeightClassTextView;
    private TextView mWeightTextView;
    private TextView mHeightTextView;
    private TextView mOutOfTextView;
    private TextView mRankTextView;
    private TextView mProdebutTextView;
    private TextView mTeamTextView;
    private TextView mSigStrikesLandedTextView;
    private TextView mSigStrikesAccuracyTextView;
    private TextView mSigStrikesAbsorbedTextView;
    private TextView mStrikesDefenceTextView;
    private TextView mTakedownsLandedTextView;
    private TextView mTakeDownsAccuracyTextView;
    private TextView mTakeDownsDefenceTextView;
    private TextView mSubmissionTextView;
    private ImageView mImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args=getArguments();
        mView = inflater.inflate(R.layout.fragment_fighter_detail, container, false);
        mFirstTextView= (TextView) mView.findViewById(R.id.fDetailsFirstTextView);
        mLastTextView= (TextView) mView.findViewById(R.id.fDetailsLastTextView);
        mNicknameTextView= (TextView) mView.findViewById(R.id.fDetailsNickname);
        mRecordsTextView= (TextView) mView.findViewById(R.id.fDetailsRecordsTextView);
        mAgeTextView= (TextView) mView.findViewById(R.id.fDetailsAgeTextView);
        mWeightClassTextView= (TextView) mView.findViewById(R.id.fDetailsWeightClassTextView);
        mWeightTextView= (TextView) mView.findViewById(R.id.fDetailsWeightTextView);
        mHeightTextView= (TextView) mView.findViewById(R.id.fDetailsHeightTextView);
        mOutOfTextView= (TextView) mView.findViewById(R.id.fDetailsOutOfTextView);
        mRankTextView= (TextView) mView.findViewById(R.id.fDetailsRankTextView);
        mProdebutTextView= (TextView) mView.findViewById(R.id.fDetailsProfDebutTextView);
        mTeamTextView= (TextView) mView.findViewById(R.id.fDetailsTeamTextView);
        mImageView= (ImageView) mView.findViewById(R.id.backgroundImageView);


        mSigStrikesLandedTextView= (TextView) mView.findViewById(R.id.fDetailsSigStrikesLandedTextView);
        mSigStrikesAccuracyTextView= (TextView) mView.findViewById(R.id.fDetailsStrikesAccuracyTextView);
        mSigStrikesAbsorbedTextView= (TextView) mView.findViewById(R.id.fDetailsStrikesAbsorbedTextView);
        mStrikesDefenceTextView= (TextView) mView.findViewById(R.id.fDetailsDefenceTextView);
        mTakedownsLandedTextView= (TextView) mView.findViewById(R.id.fDetailsTakedownsLandedTextView);
        mTakeDownsAccuracyTextView= (TextView) mView.findViewById(R.id.fDetailsTakedownsAccuracyTextView);
        mTakeDownsDefenceTextView= (TextView) mView.findViewById(R.id.fDetailstakedownsDefenceTextView);
        mSubmissionTextView= (TextView) mView.findViewById(R.id.fDefenceSubmissionTextView);


        mFighterStats=new FighterStats();
        mFighter=args.getParcelable("fighter");


        Database database=new Database();
        database.addStatsListener(this);
        database.readFighterStatsHtml(mFighter);
        return mView;
    }

    @Override
    public void OnDataReceived(FighterStats fighterStats) {
        mFighterStats=fighterStats;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mFirstTextView.setText(mFighterStats.getmFirst());
                mLastTextView.setText(mFighterStats.getmLast());
                mNicknameTextView.setText(mFighter.getNickName());
                mRecordsTextView.setText(mFighter.getWins()+"-"+mFighter.getLosses()+"-"+mFighter.getDraws());
                mAgeTextView.setText(mFighterStats.getmAge());
                mWeightClassTextView.setText(mFighter.getmWeightClass());
                mWeightTextView.setText(mFighterStats.getmWeight());
                mHeightTextView.setText(mFighterStats.getmHeight());
                mOutOfTextView.setText(mFighterStats.getmFightsOutOf());
                mRankTextView.setText(mFighterStats.getmRank());
                mProdebutTextView.setText(mFighterStats.getmProfDebut());
                mTeamTextView.setText(mFighterStats.getmTeam());

                mSigStrikesLandedTextView.setText(mFighterStats.getmSigStrikesLanded());
                mSigStrikesAccuracyTextView.setText(mFighterStats.getmSigStrikesAccuracy());
                mSigStrikesAbsorbedTextView.setText(mFighterStats.getmStrikesAbsorbed());
                mStrikesDefenceTextView.setText(mFighterStats.getmSigStrikesDef());
                mTakedownsLandedTextView.setText(mFighterStats.getmTakeDownsLanded());
                mTakeDownsAccuracyTextView.setText(mFighterStats.getmTakeDownAccuracy());
                mTakeDownsDefenceTextView.setText(mFighterStats.getmTakeDownDefence());
                mSubmissionTextView.setText(mFighterStats.getmSubmissionAttempts());


                if(!mFighter.getFullBodyUrl().equals("default"))
                    Glide.with(getActivity()).load(mFighter.getFullBodyUrl()).into(mImageView);
                else
                    mImageView.setImageResource(R.drawable.default_fighter_full_body);
            }
        });
    }
}
