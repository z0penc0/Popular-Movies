package ar.com.mobiledieguinho.popularmovies.ws;

import java.util.Arrays;
import java.util.List;

import ar.com.mobiledieguinho.popularmovies.Constants;
import ar.com.mobiledieguinho.popularmovies.entity.Movie;
import ar.com.mobiledieguinho.popularmovies.entity.Review;
import ar.com.mobiledieguinho.popularmovies.entity.ReviewsData;
import ar.com.mobiledieguinho.popularmovies.entity.Trailer;
import ar.com.mobiledieguinho.popularmovies.entity.ResponsePage;
import ar.com.mobiledieguinho.popularmovies.entity.TrailersData;
import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by Dieguinho on 15/07/2015.
 */
public class WebService {

    public List<Movie> getMovies(String sort){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .build();

        InterfaceTheMovieDataBase service = restAdapter.create(InterfaceTheMovieDataBase.class);
        ResponsePage page = service.discoverMovies(sort, Constants.KEY);
        return page.getResults();
    }

    public Movie getMovie(long id){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .build();

        InterfaceTheMovieDataBase service = restAdapter.create(InterfaceTheMovieDataBase.class);
        Movie movie = service.getMovie(id, Constants.KEY);
        return movie;
    }

    public List<Trailer> getMovieTrailers(long id){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .build();

        InterfaceTheMovieDataBase service = restAdapter.create(InterfaceTheMovieDataBase.class);
        TrailersData trailers = service.getMovieTrailers(id, Constants.KEY);
        return Arrays.asList(trailers.getTrailers());
    }

    public List<Review> getMovieReviews(long id){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .build();

        InterfaceTheMovieDataBase service = restAdapter.create(InterfaceTheMovieDataBase.class);
        ReviewsData reviewsData = service.getMovieReviews(id, Constants.KEY);
        return Arrays.asList(reviewsData.getReviews());
    }
}
