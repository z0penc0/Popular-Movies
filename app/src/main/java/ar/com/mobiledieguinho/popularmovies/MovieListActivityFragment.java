package ar.com.mobiledieguinho.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ar.com.mobiledieguinho.popularmovies.adapter.MoviesAdapter;
import ar.com.mobiledieguinho.popularmovies.entity.Movie;
import ar.com.mobiledieguinho.popularmovies.parser.MovieParser;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieListActivityFragment extends Fragment {
    private final static String TAG = "MovieListActivity";
    private boolean recreated = false;
    private MoviesAdapter adapter;
    private GridView moviesListView;
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    public MovieListActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState != null && savedInstanceState.containsKey("movies")) {
            movies = savedInstanceState.getParcelableArrayList("movies");
        }else{
            fetchMovieData();
        }
        this.recreated = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        if(!this.recreated) {
            this.movies.clear();
            fetchMovieData();
        }
        this.recreated = false;
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", this.movies);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentRoot = inflater.inflate(R.layout.fragment_movie_list, container, false);
        moviesListView = (GridView) fragmentRoot.findViewById(R.id.listView_movies);

        adapter = new MoviesAdapter(getActivity(), R.layout.list_item_movie, movies);
        moviesListView.setAdapter(adapter);
        moviesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie = (Movie) adapter.getItem(position);
                Intent detailIntent = new Intent(getActivity(), MovieDetailActivity.class);

                Bundle bundle = new Bundle();
                bundle.putParcelable("selectedMovie", selectedMovie);
                detailIntent.putExtras(bundle);
                startActivity(detailIntent);
            }
        });
        return fragmentRoot;
    }

    private void add(Movie movie){
        movies.add(movie);
        adapter.notifyDataSetChanged();
    }

    private void addAll(List<Movie> serverMovies){
        movies.addAll(serverMovies);
        adapter.notifyDataSetChanged();
    }

    private void fetchMovieData(){
        Log.d(TAG, "Fetching movies...");
        AsyncTask<Void, Void, List<Movie>> task = new AsyncTask<Void, Void, List<Movie>>() {
            @Override
            protected List<Movie> doInBackground(Void... params) {
                List<Movie> movies = new ArrayList<Movie>();
                List<Pair<String, String>> parameters = new ArrayList<Pair<String, String>>();

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String sortBy = preferences.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_value));

                WebProxy proxy = new WebProxy();
                parameters.add(new Pair(WebProxy.PARAM_KEY, Constants.KEY));
                parameters.add(new Pair(WebProxy.PARAM_SORT, sortBy));

                String jsonData = proxy.fetchJSONData(Constants.URL_BASE_MOVIE_DATABASE, parameters);
                Log.d(TAG, "JSON: " + jsonData);
                if(jsonData != null && !jsonData.equals("")){
                    movies = MovieParser.parseJSON(jsonData);
                }

                return movies;
            }

            @Override
            protected void onPostExecute(List<Movie> movies) {
                addAll(movies);
            }
        };
        task.execute();
    }
}
