package ar.com.mobiledieguinho.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ar.com.mobiledieguinho.popularmovies.adapter.MoviesAdapter;
import ar.com.mobiledieguinho.popularmovies.contentprovider.MovieContract;
import ar.com.mobiledieguinho.popularmovies.entity.Movie;
import ar.com.mobiledieguinho.popularmovies.task.FetchMovieTask;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieListActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private final static String TAG = "MovieListActivity";
    private MoviesAdapter adapter;
    private GridView moviesListView;
    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private String lastSortOrder;
    private int mCurrentPosition;
    private String SELECTED_ITEM = "position";
    private static final int MOVIE_LOADER = 0;

    public static final String[] ALL_CALOUMNS = {
            MovieContract.MovieEntry.TABLE_NAME + "." + MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE,
            MovieContract.MovieEntry.COLUMN_BACKDROP_PATH,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            MovieContract.MovieEntry.COLUMN_SYNOPSIS,
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
            MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE,
            MovieContract.MovieEntry.COLUMN_ADULT,
            MovieContract.MovieEntry.COLUMN_BUDGET,
            MovieContract.MovieEntry.COLUMN_HOMEPAGE,
            MovieContract.MovieEntry.COLUMN_ID_IMDB,
            MovieContract.MovieEntry.COLUMN_POPULARITY,
            MovieContract.MovieEntry.COLUMN_ID_PRODUCTION_COMPANY,
            MovieContract.MovieEntry.COLUMN_ID_PRODUCTION_COUNTRY,
            MovieContract.MovieEntry.COLUMN_REVENUE,
            MovieContract.MovieEntry.COLUMN_RUNTIME,
            MovieContract.MovieEntry.COLUMN_ID_SPOKEN_LANGUAGE,
            MovieContract.MovieEntry.COLUMN_STATUS,
            MovieContract.MovieEntry.COLUMN_TAGLINE,
            MovieContract.MovieEntry.COLUMN_VIDEO,
            MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE,
            MovieContract.MovieEntry.COLUMN_VOTE_COUNT,
            MovieContract.MovieEntry.COLUMN_FAVOURITE
    };

    public static final int COLUMN_ID = 0;
    public static final int COLUMN_TITLE = 1;
    public static final int COLUMN_ORIGINAL_TITLE = 2;
    public static final int COLUMN_BACKDROP_PATH = 3;
    public static final int COLUMN_POSTER_PATH = 4;
    public static final int COLUMN_SYNOPSIS = 5;
    public static final int COLUMN_RELEASE_DATE = 6;
    public static final int COLUMN_ORIGINAL_LANGUAGE = 7;
    public static final int COLUMN_ADULT = 8;
    public static final int COLUMN_BUDGET = 9;
    public static final int COLUMN_HOMEPAGE = 10;
    public static final int COLUMN_ID_IMDB = 11;
    public static final int COLUMN_POPULARITY = 12;
    public static final int COLUMN_ID_PRODUCTION_COMPANY = 13;
    public static final int COLUMN_ID_PRODUCTION_COUNTRY = 14;
    public static final int COLUMN_REVENUE = 15;
    public static final int COLUMN_RUNTIME = 16;
    public static final int COLUMN_ID_SPOKEN_LANGUAGE = 17;
    public static final int COLUMN_STATUS = 18;
    public static final int COLUMN_TAGLINE = 19;
    public static final int COLUMN_VIDEO = 20;
    public static final int COLUMN_VOTE_AVERAGE = 21;
    public static final int COLUMN_VOTE_COUNT = 22;
    public static final int COLUMN_FAVOURITE = 23;
    public static final int COLUMN_ID_TRAILER = 24;
    public static final int COLUMN_ID_REVIEW = 25;

    public MovieListActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState != null && savedInstanceState.containsKey("movies")) {
            movies = savedInstanceState.getParcelableArrayList("movies");
        }else{
//            fetchMoviesData();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
        FetchMovieTask movieTask = new FetchMovieTask(getActivity());
        movieTask.execute();
    }

    @Override
    public void onResume() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = preferences.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_value));

        // Fetch movies only if sort criteria has changed
        if(lastSortOrder!= null && !sortBy.equals(lastSortOrder)){
            this.movies.clear();
//            fetchMoviesData();
        }
        lastSortOrder = sortBy;
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", this.movies);
        if(mCurrentPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_ITEM, mCurrentPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(savedInstanceState != null && savedInstanceState.getInt(SELECTED_ITEM) != 0){
            mCurrentPosition = savedInstanceState.getInt(SELECTED_ITEM);
        }

        View fragmentRoot = inflater.inflate(R.layout.fragment_movie_list, container, false);
        moviesListView = (GridView) fragmentRoot.findViewById(R.id.listView_movies);

        adapter = new MoviesAdapter(getActivity(), null, 0);
        moviesListView.setAdapter(adapter);
        moviesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentPosition = position;
//                Movie selectedMovie = (Movie) adapter.getItem(position);
                Cursor cursor = adapter.getCursor();
                if(cursor.moveToPosition(position)){
                    int selectedMovieId = cursor.getInt(COLUMN_ID);
                    Intent detailIntent = new Intent(getActivity(), MovieDetailActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putLong("selectedMovieId", selectedMovieId);
//                    bundle.putParcelable("selectedMovie", selectedMovie);
                    detailIntent.putExtras(bundle);
                    startActivity(detailIntent);
                }
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = preferences.getString(getActivity().getString(R.string.pref_sort_key), getActivity().getString(R.string.pref_sort_value));

//        Uri moviesUri = MovieContract.MovieEntry.buildMovieUri();
        Uri moviesUri = MovieContract.MovieEntry.CONTENT_URI;
        return new CursorLoader(getActivity(),
                moviesUri,
                ALL_CALOUMNS,
                null,
                null,
                sortBy.replace(".", " "));
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
        if(mCurrentPosition != ListView.INVALID_POSITION) {
            moviesListView.setSelection(mCurrentPosition);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

//    private void fetchMoviesData(){
//        Log.d(TAG, "Fetching movies...");
//        AsyncTask<Void, Void, List<Movie>> task = new AsyncTask<Void, Void, List<Movie>>() {
//            @Override
//            protected List<Movie> doInBackground(Void... params) {
//                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//                String sortBy = preferences.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_value));
//
//                WebService webService = new WebService();
//                return webService.getMovies(sortBy);
//            }
//
//            @Override
//            protected void onPostExecute(List<Movie> movies) {
//                addAll(movies);
//            }
//        };
//        task.execute();
//    }
}
