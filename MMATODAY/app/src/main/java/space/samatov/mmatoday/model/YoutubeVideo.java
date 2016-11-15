package space.samatov.mmatoday.model;


import android.os.Parcel;
import android.os.Parcelable;

public class YoutubeVideo implements Parcelable {
    private String mTitle;
    private String mId;

    public YoutubeVideo(){}

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mId);
    }

    protected YoutubeVideo(Parcel in) {
        mTitle = in.readString();
        mId = in.readString();
    }

    public static final Creator<YoutubeVideo> CREATOR = new Creator<YoutubeVideo>() {
        @Override
        public YoutubeVideo createFromParcel(Parcel in) {
            return new YoutubeVideo(in);
        }

        @Override
        public YoutubeVideo[] newArray(int size) {
            return new YoutubeVideo[size];
        }
    };


}
