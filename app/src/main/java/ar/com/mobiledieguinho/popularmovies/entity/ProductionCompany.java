package ar.com.mobiledieguinho.popularmovies.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dieguinho on 19/07/2015.
 */
public class ProductionCompany implements Parcelable{
    @SerializedName("id") private long id;
    @SerializedName("name") private String name;

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

    private ProductionCompany(Parcel parcel){
        this.id = parcel.readLong();
        this.name = parcel.readString();
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

    public static final Parcelable.Creator<ProductionCompany> CREATOR = new Parcelable.Creator<ProductionCompany>() {
        @Override
        public ProductionCompany createFromParcel(Parcel parcel) {
            return new ProductionCompany(parcel);
        }

        @Override
        public ProductionCompany[] newArray(int i) {
            return new ProductionCompany[i];
        }
    };
}
