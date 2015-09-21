package ar.com.mobiledieguinho.popularmovies.ws;

import java.util.List;

import ar.com.mobiledieguinho.popularmovies.Constants;
import ar.com.mobiledieguinho.popularmovies.entity.Movie;
import ar.com.mobiledieguinho.popularmovies.entity.ResponsePage;
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

    public Movie getMovie(String id){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .build();

        InterfaceTheMovieDataBase service = restAdapter.create(InterfaceTheMovieDataBase.class);
        Movie movie = service.getMovie(id, Constants.KEY);
        return movie;
    }
}
