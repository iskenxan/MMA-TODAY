package space.samatov.mmatoday.model;

public class FighterStats {

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
    private String mStikesLandedPerMinute;
    private String mSigStrikesDef;
    private String mProfDebut;
    private String mRank;

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
        float value=Float.valueOf(mTakeDownAccuracy);
        int rounded=Math.round(value);
        this.mTakeDownAccuracy = rounded+"%";
    }

    public String getmTakeDownDefence() {
        return mTakeDownDefence;
    }

    public void setmTakeDownDefence(String mTakeDownDefence) {
        float value=Float.valueOf(mTakeDownDefence);
        int rounded=Math.round(value);
        this.mTakeDownDefence = rounded+"%";
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
        float value=Float.valueOf(mSigStrikesAccuracy);
        int rounded=Math.round(value);
        this.mSigStrikesAccuracy = rounded+"%";
    }

    public String getmStikesLandedPerMinute() {
        return mStikesLandedPerMinute;
    }

    public void setmStikesLandedPerMinute(String mStikesLandedPerMinute) {
        this.mStikesLandedPerMinute = mStikesLandedPerMinute;
    }

    public String getmSigStrikesDef() {
        return mSigStrikesDef;
    }

    public void setmSigStrikesDef(String mSigStrikesDef) {
        float value=Float.valueOf(mSigStrikesDef);
        int rounded=Math.round(value);
        this.mSigStrikesDef = rounded+"%";
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
}
