package ar.com.mobiledieguinho.popularmovies.ws;

import java.util.List;

import ar.com.mobiledieguinho.popularmovies.entity.Movie;
import ar.com.mobiledieguinho.popularmovies.entity.Review;
import ar.com.mobiledieguinho.popularmovies.entity.ReviewsData;
import ar.com.mobiledieguinho.popularmovies.entity.Trailer;
import ar.com.mobiledieguinho.popularmovies.entity.ResponsePage;
import ar.com.mobiledieguinho.popularmovies.entity.TrailersData;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Dieguinho on 14/07/2015.
 */
public interface InterfaceTheMovieDataBase {
    @GET("/discover/movie")
    ResponsePage discoverMovies(@Query("sort_by") String sort, @Query("api_key") String key);

    @GET("/movie/{id}")
    Movie getMovie(@Path("id") long id, @Query("api_key") String key);

    @GET("/movie/{id}/trailers")
    TrailersData getMovieTrailers(@Path("id") long id, @Query("api_key") String key);

    @GET("/movie/{id}/reviews")
    ReviewsData getMovieReviews(@Path("id") long id, @Query("api_key") String key);
}
