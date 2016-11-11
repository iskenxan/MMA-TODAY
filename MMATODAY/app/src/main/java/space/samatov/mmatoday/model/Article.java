package space.samatov.mmatoday.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable {

    private String mHeadline;
    private String mAuthor;
    private String mDate;
    private String mDescription;
    private String mContent;
    private String mUrl;

    public  Article(){}
    public String getmHeadline() {
        return mHeadline;
    }

    public void setmHeadline(String mHeadline) {
        this.mHeadline = mHeadline;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mHeadline);
        parcel.writeString(mAuthor);
        parcel.writeString(mUrl);
        parcel.writeString(mContent);
        parcel.writeString(mDate);
        parcel.writeString(mDescription);
    }

    public static  final Parcelable.Creator CREATOR=new Creator() {
        @Override
        public Object createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public Object[] newArray(int i) {
            return new Object[0];
        }
    };

    public Article(Parcel in){
        mHeadline=in.readString();
        mAuthor=in.readString();
        mUrl=in.readString();
        mContent=in.readString();
        mDate=in.readString();
        mDescription=in.readString();
    }

}
