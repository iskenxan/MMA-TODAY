package space.samatov.mmatoday.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class OctagonGirl implements Parcelable {
    private String mFirst;
    private String mLast;
    private String mCountry;
    private String mCity;
    private String mBio1;
    private String mBio2;
    private String mFavoriteFood;
    private String mHobbies;
    private String mBirthDate;
    private String mQuote;
    private String mTwitterUsername;
    private String mBodyPic;
    private String mBanner;
    private ArrayList<String> mGallery;
    private int mHeight;
    private int mWeight;

    public OctagonGirl(){}



    public int getmWeight() {
        return mWeight;
    }

    public void setmWeight(int mWeight) {
        this.mWeight = mWeight;
    }

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

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmBio1() {
        return mBio1;
    }

    public void setmBio1(String mBio1) {
        this.mBio1=formatBioData(mBio1);
    }

    public String getmBio2() {
        return mBio2;
    }

    public void setmBio2(String mBio2) {
        this.mBio2 = formatBioData(mBio2);
    }

    public String getmFavoriteFood() {
        return mFavoriteFood;
    }

    public void setmFavoriteFood(String mFavoriteFood) {
        this.mFavoriteFood = mFavoriteFood;
    }

    public String getmHobbies() {
        return mHobbies;
    }

    public void setmHobbies(String mHobbies) {
        this.mHobbies = mHobbies;
    }

    public String getmBirthDate() {
        return mBirthDate;
    }

    public void setmBirthDate(String mBirthDate) {
        this.mBirthDate = mBirthDate;
    }

    public String getmQuote() {
        return mQuote;
    }

    public void setmQuote(String mQuote) {
        this.mQuote = mQuote;
    }

    public String getmTwitterUsername() {
        return mTwitterUsername;
    }

    public void setmTwitterUsername(String mTwitterUsername) {
        this.mTwitterUsername = mTwitterUsername;
    }

    public ArrayList<String> getmGallery() {
        return mGallery;
    }

    public void setmGallery(ArrayList<String> mGallery) {
        this.mGallery = mGallery;
    }

    public int getmHeight() {
        return mHeight;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public String getmBodyPic() {
        return mBodyPic;
    }

    public void setmBodyPic(String mBodyPic) {
        this.mBodyPic = mBodyPic;
    }

    public String getmBanner() {
        return mBanner;
    }

    public void setmBanner(String mBanner) {
        this.mBanner = mBanner;
    }


    private String formatBioData(String bio){
        String str=bio;
        str=str.replaceAll("&nbsp;","");
        str=str.replaceAll("&ldquo;","");
        str=str.replaceAll("&rdquo;","");
        str=str.replaceAll("&ndash;","");
        str=str.replaceAll("\\<.*?\\> ?","");

        return str;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mFirst);
        parcel.writeString(mLast);
        parcel.writeString(mCountry);
        parcel.writeString(mCity);
        parcel.writeString(mBio1);
        parcel.writeString(mBio2);
        parcel.writeString(mFavoriteFood);
        parcel.writeString(mHobbies);
        parcel.writeString(mBirthDate);
        parcel.writeString(mQuote);
        parcel.writeString(mTwitterUsername);
        parcel.writeString(mBodyPic);
        parcel.writeString(mBanner);
        parcel.writeList(mGallery);
        parcel.writeInt(mHeight);
        parcel.writeInt(mWeight);
    }
    public static final Creator<OctagonGirl> CREATOR = new Creator<OctagonGirl>() {
        @Override
        public OctagonGirl createFromParcel(Parcel in) {
            return new OctagonGirl(in);
        }

        @Override
        public OctagonGirl[] newArray(int size) {
            return new OctagonGirl[size];
        }
    };

    protected OctagonGirl(Parcel in) {
        mFirst = in.readString();
        mLast = in.readString();
        mCountry = in.readString();
        mCity = in.readString();
        mBio1 = in.readString();
        mBio2 = in.readString();
        mFavoriteFood = in.readString();
        mHobbies = in.readString();
        mBirthDate = in.readString();
        mQuote = in.readString();
        mTwitterUsername = in.readString();
        mBodyPic=in.readString();
        mBanner=in.readString();
        mGallery = in.createStringArrayList();
        mHeight = in.readInt();
        mWeight = in.readInt();
    }


}
