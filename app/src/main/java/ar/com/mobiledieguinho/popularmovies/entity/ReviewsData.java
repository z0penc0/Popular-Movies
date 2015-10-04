package ar.com.mobiledieguinho.popularmovies.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dieguinho on 04/10/2015.
 */
public class ReviewsData {
    @SerializedName("id") private long movieId;
    @SerializedName("results") private Review[] reviews;

    public ReviewsData(long movieId, Review[] reviews) {
        this.movieId = movieId;
        this.reviews = reviews;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public Review[] getReviews() {
        return reviews;
    }

    public void setReviews(Review[] reviews) {
        this.reviews = reviews;
    }
}
