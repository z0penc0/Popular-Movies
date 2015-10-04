package ar.com.mobiledieguinho.popularmovies.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dieguinho on 04/10/2015.
 */
public class TrailersData {
    @SerializedName("id") private long movieId;
    @SerializedName("youtube") private Trailer[] trailers;

    public TrailersData(long movieId, Trailer[] trailers) {
        this.movieId = movieId;
        this.trailers = trailers;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public Trailer[] getTrailers() {
        return trailers;
    }

    public void setTrailers(Trailer[] trailers) {
        this.trailers = trailers;
    }
}
