package space.samatov.mmatoday.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Fighter implements Parcelable,ListItem {
    private String mFirstName;
    private String mLastName;
    private String mNickName;
    private String mWeightClass;
    private int mId;
    private String mProfileUrl;
    private String mBeltProfileUrl="";
    private String mPFP;
    private boolean mTitleHolder;
    private int mWins;
    private int mLosses;
    private int mDraws;

    public Fighter(){

    }


    public void setFightsRecord(int wins,int losses,int draws){
        setWins(wins);
        setLosses(losses);
        setDraws(draws);
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getNickName() {
        return mNickName;
    }

    public void setNickName(String nickName) {
        mNickName = nickName;
    }

    public String getmWeightClass() {
        return mWeightClass;
    }
    public void setmWeightClass(String mWeightClass) {
        this.mWeightClass = mWeightClass;
    }

    public String getPFP() {
        return mPFP;
    }

    public void setPFP(String PFP) {
        mPFP = PFP;
    }

    public String getProfileUrl() {
        return mProfileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        mProfileUrl = profileUrl;
    }

    public boolean isTitleHolder() {
        return mTitleHolder;
    }

    public void setTitleHolder(boolean titleHolder) {
        mTitleHolder = titleHolder;
    }

    public int getWins() {
        return mWins;
    }

    public void setWins(int mWins) {
        this.mWins = mWins;
    }

    public int getLosses() {
        return mLosses;
    }

    public void setLosses(int mLosses) {
        this.mLosses = mLosses;
    }

    public int getDraws() {
        return mDraws;
    }

    public void setDraws(int mDraws) {
        this.mDraws = mDraws;
    }

    public String getmBeltProfileUrl() {
        return mBeltProfileUrl;
    }

    public void setmBeltProfileUrl(String mBeltProfileUrl) {
        this.mBeltProfileUrl = mBeltProfileUrl;
    }

    //**********Parcelable*************
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mFirstName);
        parcel.writeString(mLastName);
        parcel.writeString(mNickName);
        parcel.writeString(mWeightClass);
        parcel.writeInt(mWins);
        parcel.writeInt(mLosses);
        parcel.writeInt(mDraws);
        parcel.writeString(mProfileUrl);
        parcel.writeInt(mId);
        parcel.writeString(mPFP);
        parcel.writeInt(mTitleHolder?1:0);
    }
    public static final Parcelable.Creator CREATOR=new Creator() {
        @Override
        public Object createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public Object[] newArray(int i) {
            return new Object[0];
        }
    };

    public Fighter(Parcel in){
        mFirstName=in.readString();
        mLastName=in.readString();
        mNickName=in.readString();
        mWeightClass=in.readString();
        mWins=in.readInt();
        mLosses=in.readInt();
        mDraws=in.readInt();
        mProfileUrl=in.readString();
        mId=in.readInt();
        mPFP=in.readString();
        if(in.readInt()==1)
            mTitleHolder=true;
        else
            mTitleHolder=false;
    }

    @Override
    public boolean isGroupHeader() {
        return false;
    }
}
