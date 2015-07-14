package ar.com.mobiledieguinho.popularmovies.entity;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ParcelableCompat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dieguinho on 10/07/2015.
 */
public class Movie implements Parcelable {
    private static final String TAG = "Movie";

    private long id;
    private String title;
    private String originalTitle;
    private String backdropPath;
    private String posterPath;
    private String synopsis;
    private double userRating;
    private String releaseDate;
    private String originalLanguage;
    private boolean adult;

    public Movie() {
    }

    public Movie(long id, String title, String originalTitle, String backdropPath, String posterPath, String synopsis, double userRating, String releaseDate, boolean adult, String originalLanguage) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.synopsis = synopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.originalLanguage = originalLanguage;
        this.adult = adult;
    }

    private Movie(Parcel parcel){
        this.id = parcel.readLong();
        this.title = parcel.readString();
        this.originalTitle = parcel.readString();
        this.backdropPath = parcel.readString();
        this.posterPath = parcel.readString();
        this.synopsis = parcel.readString();
        this.userRating = parcel.readDouble();
        this.releaseDate = parcel.readString();
        this.originalLanguage = parcel.readString();
        this.adult = parcel.readByte() != 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public void setUserRating(long userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.originalTitle);
        dest.writeString(this.backdropPath);
        dest.writeString(this.posterPath);
        dest.writeString(this.synopsis);
        dest.writeDouble(this.userRating);
        dest.writeString(this.releaseDate);
        dest.writeString(this.originalLanguage);
        dest.writeByte((byte) (this.adult ? 1 : 0));
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }

    };
}
