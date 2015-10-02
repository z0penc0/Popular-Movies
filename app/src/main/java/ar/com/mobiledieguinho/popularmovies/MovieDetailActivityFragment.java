package ar.com.mobiledieguinho.popularmovies;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ar.com.mobiledieguinho.popularmovies.contentprovider.MovieContract;
import ar.com.mobiledieguinho.popularmovies.entity.Movie;
import ar.com.mobiledieguinho.popularmovies.ws.WebService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private final static String TAG = "MovieDetailActivityFrag";

    final static String MOVIE_DETAIL_ID = "SELECTED_MOVIE_ID";

    private final static int MOVIE_LOADER = 0;
    private final static int MOVIE_TRAILERS_LOADER = 1;
    private final static int MOVIE_REVIEWS_LOADER = 2;

    private ImageView imageViewBackdrop;
    private ImageView imageViewPoster;
    private TextView textViewTitle;
    private TextView textViewGenres;
    private TextView textViewReleaseDate;
    private TextView textViewSynopsis;
    private TextView textViewUserRating;
    private TextView textViewFavourite;


    private Movie selectedMovie;
    private View root;
    private long selectedMovieId;

    private Callback<Movie> mCallback = new Callback<Movie>() {
        @Override
        public void success(Movie movie, Response response) {
            Log.d(TAG,"");
        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    public MovieDetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedMovie = getActivity().getIntent().getParcelableExtra("selectedMovie");
        selectedMovieId = getActivity().getIntent().getLongExtra("selectedMovieId", 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchMovieData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            selectedMovieId = arguments.getParcelable(MovieDetailActivityFragment.MOVIE_DETAIL_ID);
        }

        root = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        imageViewBackdrop = (ImageView)root.findViewById(R.id.imageView_backdrop);
        imageViewPoster = (ImageView)root.findViewById(R.id.imageView_poster);
        textViewTitle = (TextView)root.findViewById(R.id.textView_title);
//        textViewGenres = (TextView)root.findViewById(R.id.textView_genres);
        textViewReleaseDate = (TextView)root.findViewById(R.id.textView_release_date);
        textViewSynopsis = (TextView)root.findViewById(R.id.textView_synopsis);
        textViewUserRating = (TextView)root.findViewById(R.id.textView_user_rating);
        textViewFavourite = (TextView)root.findViewById(R.id.textView_favourite);

        imageViewBackdrop.setAdjustViewBounds(true);
//        String urlBackdrop = Constants.URL_BASE_MOVIE_DATABASE_IMAGE + Constants.IMAGE_SIZE_342 + selectedMovie.getBackdropPath();
//        Picasso.with(getActivity()).load(urlBackdrop).into(imageViewBackdrop);
//
//        if(selectedMovie.getPosterPath().equals("")){
//            imageViewPoster.setImageResource(R.drawable.poster_not_available);
//        }else {
//            String urlPoster = Constants.URL_BASE_MOVIE_DATABASE_IMAGE + Constants.IMAGE_SIZE_185 + selectedMovie.getPosterPath();
//            Picasso.with(getActivity()).load(urlPoster).into(imageViewPoster);
//        }
//
//        textViewTitle.setText(this.selectedMovie.getTitle());
//        textViewReleaseDate.setText(this.selectedMovie.getReleaseDate());
//        textViewSynopsis.setText(this.selectedMovie.getSynopsis());
//        textViewUserRating.setText(String.valueOf(this.selectedMovie.getUserRating()));
//        textViewFavourite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                ContentResolver contentResolver = getActivity().getContentResolver();
////                Cursor cursor = contentResolver.insert(MovieContract.MovieEntry.CONTENT_URI, new String[]{WeatherContract.LocationEntry._ID}, WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING + " = ?", new String[]{locationSetting}, null);
//
//            }
//        });

        return root;
    }

    private void fetchMovieData(){
        Log.d(TAG, "Fetching movie...");
        AsyncTask<Void, Void, Movie> task = new AsyncTask<Void, Void, Movie>() {
            @Override
            protected Movie doInBackground(Void... params) {
                WebService webService = new WebService();
                return webService.getMovie(selectedMovieId, mCallback);
            }

            @Override
            protected void onPostExecute(Movie movie) {
                selectedMovie = movie;
            }
        };
        task.execute();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(MOVIE_LOADER, savedInstanceState, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch(id){
            case MOVIE_LOADER:
                Uri moviesUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, selectedMovieId);
                return new CursorLoader(getActivity(),
                        moviesUri,
                        MovieListActivityFragment.ALL_CALOUMNS,
                        null,
                        null,
                        null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
        if (!data.moveToFirst() || data.getCount() > 1) {
            return;
        }

        imageViewBackdrop.setAdjustViewBounds(true);
        String urlBackdrop = Constants.URL_BASE_MOVIE_DATABASE_IMAGE + Constants.IMAGE_SIZE_342 + data.getString(MovieListActivityFragment.COLUMN_BACKDROP_PATH);
        Picasso.with(getActivity()).load(urlBackdrop).into(imageViewBackdrop);

        String posterPath = data.getString(MovieListActivityFragment.COLUMN_POSTER_PATH);
        if(TextUtils.isEmpty(posterPath)){
            imageViewPoster.setImageResource(R.drawable.poster_not_available);
        }else {
            String urlPoster = Constants.URL_BASE_MOVIE_DATABASE_IMAGE + Constants.IMAGE_SIZE_185 + posterPath;
            Picasso.with(getActivity()).load(urlPoster).into(imageViewPoster);
        }

        textViewTitle.setText(data.getString(MovieListActivityFragment.COLUMN_TITLE));
        textViewReleaseDate.setText(data.getString(MovieListActivityFragment.COLUMN_RELEASE_DATE));
        textViewSynopsis.setText(data.getString(MovieListActivityFragment.COLUMN_SYNOPSIS));
        textViewUserRating.setText(String.valueOf(data.getDouble(MovieListActivityFragment.COLUMN_VOTE_AVERAGE)));

        if(data.getInt(MovieListActivityFragment.COLUMN_FAVOURITE) != 0){
            textViewFavourite.setText(R.string.textView_favorited);
            textViewFavourite.setBackgroundResource(R.color.favourited);
        }else{
            textViewFavourite.setText(R.string.textView_favorite);
            textViewFavourite.setBackgroundResource(R.color.not_favourited);
        }
        textViewFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                int movieId = data.getInt(MovieListActivityFragment.COLUMN_ID);
                boolean favourite = !(data.getInt(MovieListActivityFragment.COLUMN_FAVOURITE) != 0);
                values.put(MovieContract.MovieEntry.COLUMN_FAVOURITE, favourite);

                ContentResolver contentResolver = getActivity().getContentResolver();
                int updates = contentResolver.update(MovieContract.MovieEntry.CONTENT_URI, values, "_id = ?", new String[]{String.valueOf(movieId)});
                Log.d(TAG, "Updated " + updates + " records");
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
