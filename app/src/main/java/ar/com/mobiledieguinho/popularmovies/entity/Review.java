package ar.com.mobiledieguinho.popularmovies.entity;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import ar.com.mobiledieguinho.popularmovies.contentprovider.MovieContract;

/**
 * Created by Dieguinho on 19/07/2015.
 */
public class Review implements Parcelable{
    @SerializedName("id") private String id;
    @SerializedName("author") private String author;
    @SerializedName("content") private String content;
    @SerializedName("url") private String url;

    public Review(String id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    private Review(Parcel parcel){
        this.id = parcel.readString();
        this.author = parcel.readString();
        this.content = parcel.readString();
        this.url = parcel.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ContentValues asContentValues() {
        ContentValues values = new ContentValues();
        values.put(MovieContract.ReviewEntry._ID, id);
        values.put(MovieContract.ReviewEntry.COLUMN_AUTHOR, author);
        values.put(MovieContract.ReviewEntry.COLUMN_CONTENT, content);
        values.put(MovieContract.ReviewEntry.COLUMN_URL, url);
        return values;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.author);
        dest.writeString(this.content);
        dest.writeString(this.url);
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel parcel) {
            return new Review(parcel);
        }

        @Override
        public Review[] newArray(int i) {
            return new Review[i];
        }
    };
}
