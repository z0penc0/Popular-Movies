package ar.com.mobiledieguinho.popularmovies.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dieguinho on 19/07/2015.
 */
public class Trailer implements Parcelable{
    @SerializedName("name") private String name;
    @SerializedName("size") private String size;
    @SerializedName("source") private String source;
    @SerializedName("type") private String type;

    public Trailer(String name, String size, String source, String type) {
        this.name = name;
        this.size = size;
        this.size = source;
        this.size = type;
    }

    private Trailer(Parcel parcel){
        this.name = parcel.readString();
        this.size = parcel.readString();
        this.size = parcel.readString();
        this.size = parcel.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.size);
        dest.writeString(this.source);
        dest.writeString(this.type);
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel parcel) {
            return new Trailer(parcel);
        }

        @Override
        public Trailer[] newArray(int i) {
            return new Trailer[i];
        }
    };
}
