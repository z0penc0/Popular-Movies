package ar.com.mobiledieguinho.popularmovies.task;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.List;
import java.util.Vector;

import ar.com.mobiledieguinho.popularmovies.R;
import ar.com.mobiledieguinho.popularmovies.contentprovider.MovieContract;
import ar.com.mobiledieguinho.popularmovies.entity.Movie;
import ar.com.mobiledieguinho.popularmovies.ws.WebService;

/**
 * Created by Dieguinho on 29/09/2015.
 */
public class FetchMovieTask extends AsyncTask<Void, Void, Void> {

    private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

    private final Context mContext;

    public FetchMovieTask(Context context) {
        mContext = context;
    }

    private boolean DEBUG = true;

    long addTrailer(String name, String source, String size, String type) {
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(MovieContract.TrailerEntry.CONTENT_URI, new String[]{MovieContract.TrailerEntry._ID}, MovieContract.TrailerEntry.COLUMN_SOURCE + " = ?", new String[]{source}, null);
        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(MovieContract.TrailerEntry._ID);
            return cursor.getLong(columnIndex);
        }
        ContentValues values = new ContentValues();
        values.put(MovieContract.TrailerEntry.COLUMN_NAME, name);
        values.put(MovieContract.TrailerEntry.COLUMN_SOURCE, source);
        values.put(MovieContract.TrailerEntry.COLUMN_SIZE, size);
        values.put(MovieContract.TrailerEntry.COLUMN_TYPE, type);

        return ContentUris.parseId(contentResolver.insert(MovieContract.TrailerEntry.CONTENT_URI, values));
    }

    long addMovie(Movie movie) {
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(MovieContract.MovieEntry.CONTENT_URI, new String[]{MovieContract.MovieEntry._ID}, MovieContract.MovieEntry._ID + " = ?", new String[]{String.valueOf(movie.getId())}, null);
        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(MovieContract.MovieEntry._ID);
            return cursor.getLong(columnIndex);
        }
        ContentValues values = new ContentValues();
        values = movie.getAsContentValues();
        return ContentUris.parseId(contentResolver.insert(MovieContract.MovieEntry.CONTENT_URI, values));
    }

    @Override
    protected Void doInBackground(Void...params) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String sortBy = preferences.getString(mContext.getString(R.string.pref_sort_key), mContext.getString(R.string.pref_sort_value));

        WebService webService = new WebService();
        List<Movie> movies = webService.getMovies(sortBy);

        Vector<ContentValues> cVVector = new Vector<ContentValues>(movies.size());
        for(int i = 0; i < movies.size(); i++){
            Movie movie = movies.get(i);
            ContentValues values = new ContentValues();
            values.put(MovieContract.MovieEntry._ID, movie.getId());
            values.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
            values.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
            values.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackdropPath());
            values.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
            values.put(MovieContract.MovieEntry.COLUMN_SYNOPSIS, movie.getSynopsis());
            values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
            values.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
            values.put(MovieContract.MovieEntry.COLUMN_ADULT, movie.isAdult());
            values.put(MovieContract.MovieEntry.COLUMN_BUDGET, movie.getBudget());
            values.put(MovieContract.MovieEntry.COLUMN_HOMEPAGE, movie.getHomepage());
            values.put(MovieContract.MovieEntry.COLUMN_ID_IMDB, movie.getIdImdb());
            values.put(MovieContract.MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
//        values.put(MovieContract.MovieEntry.COLUMN_ID_PRODUCTION_COMPANY, );
//        values.put(MovieContract.MovieEntry.COLUMN_ID_PRODUCTION_COUNTRY, );
            values.put(MovieContract.MovieEntry.COLUMN_REVENUE, movie.getRevenue());
            values.put(MovieContract.MovieEntry.COLUMN_RUNTIME, movie.getRuntime());
//        values.put(MovieContract.MovieEntry.COLUMN_ID_SPOKEN_LANGUAGE, movie.get);
            values.put(MovieContract.MovieEntry.COLUMN_STATUS, movie.getStatus());
            values.put(MovieContract.MovieEntry.COLUMN_TAGLINE, movie.getTagLine());
            values.put(MovieContract.MovieEntry.COLUMN_VIDEO, movie.isVideo());
            values.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getUserRating());
            values.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, movie.getVoteCount());
            values.put(MovieContract.MovieEntry.COLUMN_FAVOURITE, false);

            cVVector.add(values);
        }

        // add to database
        int inserted = 0;
        if ( cVVector.size() > 0 ) {
            ContentValues[] contentValues = new ContentValues[cVVector.size()];
            cVVector.toArray(contentValues);
            inserted = mContext.getContentResolver().bulkInsert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
        }
        Log.d(LOG_TAG, "FetchMovieTask Complete. " + inserted + " Inserted");

        return null;
    }

}


