package ar.com.mobiledieguinho.popularmovies.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dieguinho on 19/07/2015.
 */
public class Genre implements Parcelable{
    @SerializedName("id") private long id;
    @SerializedName("name") private String name;

    public Genre(long id, String name) {
        this.id = id;
        this.name = name;
    }

    private Genre(Parcel parcel){
        this.id = parcel.readLong();
        this.name = parcel.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
    }

    public static final Parcelable.Creator<Genre> CREATOR = new Parcelable.Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel parcel) {
            return new Genre(parcel);
        }

        @Override
        public Genre[] newArray(int i) {
            return new Genre[i];
        }
    };
}
