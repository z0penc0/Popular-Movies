package ar.com.mobiledieguinho.popularmovies.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ar.com.mobiledieguinho.popularmovies.entity.Movie;

/**
 * Created by Dieguinho on 10/07/2015.
 */
public class MovieParser {
    public static List<Movie> parseJSON(String moviesJSONString){
        List<Movie> movies = new ArrayList<Movie>();
        try {
            JSONObject response = new JSONObject(moviesJSONString);
            JSONArray jsonMovies = response.getJSONArray("results");

            Movie movie;
            for(int i = 0; i < jsonMovies.length(); i++){
                movie = new Movie();
                JSONObject jsonMovie = jsonMovies.getJSONObject(i);
                movie.setId(jsonMovie.isNull("id") ? 0L : jsonMovie.getLong("id"));
                movie.setTitle(jsonMovie.isNull("title") ? "Title Unavailable." : jsonMovie.getString("title"));
                movie.setSynopsis(jsonMovie.isNull("overview") ? "Unavailable." : jsonMovie.getString("overview"));
                movie.setUserRating(jsonMovie.isNull("vote_average") ? 0D : jsonMovie.getDouble("vote_average"));
                movie.setBackdropPath(jsonMovie.isNull("backdrop_path") ? "" : jsonMovie.getString("backdrop_path"));
                movie.setPosterPath(jsonMovie.isNull("poster_path") ? "" : jsonMovie.getString("poster_path"));
                movie.setReleaseDate(jsonMovie.isNull("release_date") ? "Unavailable" : jsonMovie.getString("release_date"));
                movie.setOriginalTitle(jsonMovie.isNull("original_title") ? "Unavailable" : jsonMovie.getString("original_title"));
                movie.setOriginalLanguage(jsonMovie.isNull("original_language") ? "Unavailable" : jsonMovie.getString("original_language"));
                movie.setAdult(jsonMovie.isNull("adult") ? false : jsonMovie.getBoolean("adult"));

                movies.add(movie);
            }
        }catch(JSONException je){
            je.printStackTrace();
        }

        return movies;
    }


}
