package ar.com.mobiledieguinho.popularmovies.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dieguinho on 19/07/2015.
 */
public class SpokenLanguage implements Parcelable {
    @SerializedName("iso_639_1") private long iso_639_1;
    @SerializedName("name") private String name;

    private SpokenLanguage(long iso_639_1, String name) {
        this.iso_639_1 = iso_639_1;
        this.name = name;
    }

    private SpokenLanguage(Parcel parcel){
        this.iso_639_1 = parcel.readLong();
        this.name = parcel.readString();
    }

    public long getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(long iso_639_1) {
        this.iso_639_1 = iso_639_1;
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
        dest.writeLong(this.iso_639_1);
        dest.writeString(this.name);
    }

    public static final Parcelable.Creator<SpokenLanguage> CREATOR = new Parcelable.Creator<SpokenLanguage>() {
        @Override
        public SpokenLanguage createFromParcel(Parcel parcel) {
            return new SpokenLanguage(parcel);
        }

        @Override
        public SpokenLanguage[] newArray(int i) {
            return new SpokenLanguage[i];
        }
    };
}
