package ar.com.mobiledieguinho.popularmovies.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dieguinho on 19/07/2015.
 */
public class ProductionCountry implements Parcelable{
    @SerializedName("iso_3166_1") private long iso_3166_1;
    @SerializedName("name") private String name;

    public ProductionCountry(long iso_3166_1, String name) {
        this.iso_3166_1 = iso_3166_1;
        this.name = name;
    }

    private ProductionCountry(Parcel parcel){
        this.iso_3166_1 = parcel.readLong();
        this.name = parcel.readString();
    }

    public long getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(long iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
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
        dest.writeLong(this.iso_3166_1);
        dest.writeString(this.name);
    }

    public static final Parcelable.Creator<ProductionCountry> CREATOR = new Parcelable.Creator<ProductionCountry>() {
        @Override
        public ProductionCountry createFromParcel(Parcel parcel) {
            return new ProductionCountry(parcel);
        }

        @Override
        public ProductionCountry[] newArray(int i) {
            return new ProductionCountry[i];
        }
    };
}
