package space.samatov.mmatoday.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FighterStats implements Parcelable {

    private long mFighterId;
    private String mFirst;
    private String mLast;
    private String mFrom;
    private String mFightsOutOf;
    private String mHeight;
    private String mAge;
    private String mWeight;
    private String mWeightClass;
    private String mTeam;
    private String mTakeDownsLanded;
    private String mTakeDownAccuracy;
    private String mTakeDownDefence;
    private String mSubmissionAttempts;
    private String mSigStrikesLanded;
    private String mSigStrikesAccuracy;
    private String mStrikesAbsorbed;
    private String mSigStrikesDef;
    private String mProfDebut;
    private String mRank;
    private String mProfRecord;

    public FighterStats(){}


    public String getmFirst() {
        return mFirst;
    }

    public void setmFirst(String mFirst) {
        this.mFirst = mFirst;
    }

    public String getmLast() {
        return mLast;
    }

    public void setmLast(String mLast) {
        this.mLast = mLast;
    }

    public String getmFrom() {
        return mFrom;
    }

    public void setmFrom(String mFrom) {
        this.mFrom = mFrom;
    }

    public String getmFightsOutOf() {
        return mFightsOutOf;
    }

    public void setmFightsOutOf(String mFightsOutOf) {
        this.mFightsOutOf = mFightsOutOf;
    }

    public String getmHeight() {
        return mHeight;
    }

    public void setmHeight(String mHeight) {
        this.mHeight = mHeight;
    }

    public String getmAge() {
        return mAge;
    }

    public void setmAge(String mAge) {
        this.mAge = mAge;
    }

    public String getmWeight() {
        return mWeight;
    }

    public void setmWeight(String mWeight) {
        this.mWeight = mWeight;
    }

    public String getmWeightClass() {
        return mWeightClass;
    }

    public void setmWeightClass(String mWeightClass) {
        this.mWeightClass = mWeightClass;
    }

    public long getmFighterId() {
        return mFighterId;
    }

    public void setmFighterId(long mFighterId) {
        this.mFighterId = mFighterId;
    }

    public String getmTeam() {
        return mTeam;
    }

    public void setmTeam(String mTeam) {
        this.mTeam = mTeam;
    }

    public String getmTakeDownsLanded() {
        return mTakeDownsLanded;
    }

    public void setmTakeDownsLanded(String mTakeDownsLanded) {
        this.mTakeDownsLanded = mTakeDownsLanded;
    }

    public String getmTakeDownAccuracy() {
        return mTakeDownAccuracy;
    }

    public void setmTakeDownAccuracy(String mTakeDownAccuracy) {
        this.mTakeDownAccuracy = mTakeDownAccuracy;
    }

    public String getmTakeDownDefence() {
        return mTakeDownDefence;
    }

    public void setmTakeDownDefence(String mTakeDownDefence) {
        this.mTakeDownDefence = mTakeDownDefence;
    }

    public String getmSubmissionAttempts() {
        return mSubmissionAttempts;
    }

    public void setmSubmissionAttempts(String mSubmissionAttempts) {
        this.mSubmissionAttempts = mSubmissionAttempts;
    }

    public String getmSigStrikesLanded() {
        return mSigStrikesLanded;
    }

    public void setmSigStrikesLanded(String mSigStrikesLanded) {
        this.mSigStrikesLanded = mSigStrikesLanded;
    }

    public String getmSigStrikesAccuracy() {
        return mSigStrikesAccuracy;
    }

    public void setmSigStrikesAccuracy(String mSigStrikesAccuracy) {
        this.mSigStrikesAccuracy = mSigStrikesAccuracy;
    }

    public String getmStrikesAbsorbed() {
        return mStrikesAbsorbed;
    }

    public void setmStrikesAbsorbed(String mStrikesAbsorbed) {
        this.mStrikesAbsorbed = mStrikesAbsorbed;
    }

    public String getmSigStrikesDef() {
        return mSigStrikesDef;
    }

    public void setmSigStrikesDef(String mSigStrikesDef) {
        this.mSigStrikesDef = mSigStrikesDef;
    }

    public String getmProfDebut() {
        return mProfDebut;
    }

    public void setmProfDebut(String mProfDebut) {
        this.mProfDebut = mProfDebut;
    }

    public String getmRank() {
        return mRank;
    }

    public void setmRank(String mRank) {
        this.mRank = mRank;
    }

    public String getmProfRecord() {
        return mProfRecord;
    }

    public void setmProfRecord(String mProfRecord) {
        this.mProfRecord = mProfRecord;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mFighterId);
        parcel.writeString(mFirst);
        parcel.writeString(mLast);
        parcel.writeString(mFrom);
        parcel.writeString(mFightsOutOf);
        parcel.writeString(mHeight);
        parcel.writeString(mAge);
        parcel.writeString(mWeight);
        parcel.writeString(mWeightClass);
        parcel.writeString(mTeam);
        parcel.writeString(mTakeDownsLanded);
        parcel.writeString(mTakeDownAccuracy);
        parcel.writeString(mTakeDownDefence);
        parcel.writeString(mSubmissionAttempts);
        parcel.writeString(mSigStrikesLanded);
        parcel.writeString(mSigStrikesAccuracy);
        parcel.writeString(mStrikesAbsorbed);
        parcel.writeString(mSigStrikesDef);
        parcel.writeString(mProfDebut);
        parcel.writeString(mRank);
        parcel.writeString(mProfRecord);
    }
    protected FighterStats(Parcel in) {
        mFighterId = in.readLong();
        mFirst = in.readString();
        mLast = in.readString();
        mFrom = in.readString();
        mFightsOutOf = in.readString();
        mHeight = in.readString();
        mAge = in.readString();
        mWeight = in.readString();
        mWeightClass = in.readString();
        mTeam = in.readString();
        mTakeDownsLanded = in.readString();
        mTakeDownAccuracy = in.readString();
        mTakeDownDefence = in.readString();
        mSubmissionAttempts = in.readString();
        mSigStrikesLanded = in.readString();
        mSigStrikesAccuracy = in.readString();
        mStrikesAbsorbed = in.readString();
        mSigStrikesDef = in.readString();
        mProfDebut = in.readString();
        mRank = in.readString();
        mProfRecord = in.readString();
    }

    public static final Creator<FighterStats> CREATOR = new Creator<FighterStats>() {
        @Override
        public FighterStats createFromParcel(Parcel in) {
            return new FighterStats(in);
        }

        @Override
        public FighterStats[] newArray(int size) {
            return new FighterStats[size];
        }
    };
}
