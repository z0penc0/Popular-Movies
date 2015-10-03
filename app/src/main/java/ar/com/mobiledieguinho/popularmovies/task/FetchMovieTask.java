package ar.com.mobiledieguinho.popularmovies.task;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.Vector;

import ar.com.mobiledieguinho.popularmovies.Constants;
import ar.com.mobiledieguinho.popularmovies.MovieListActivity;
import ar.com.mobiledieguinho.popularmovies.R;
import ar.com.mobiledieguinho.popularmovies.contentprovider.MovieContract;
import ar.com.mobiledieguinho.popularmovies.entity.Movie;
import ar.com.mobiledieguinho.popularmovies.ws.WebService;

/**
 * Created by Dieguinho on 29/09/2015.
 */
public class FetchMovieTask extends AsyncTask<Void, Void, Void> {

    private final String TAG = FetchMovieTask.class.getSimpleName();

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
        ContentValues values = movie.getAsContentValues();
        return ContentUris.parseId(contentResolver.insert(MovieContract.MovieEntry.CONTENT_URI, values));
    }

    @Override
    protected Void doInBackground(Void...params) {
        SharedPreferences sharedPref = mContext.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String sortBy = sharedPref.getString(mContext.getString(R.string.pref_sort_key), mContext.getString(R.string.pref_sort_value));
        boolean favouriteSelected = sharedPref.getBoolean(mContext.getString(R.string.pref_favorite_key), false);
        Log.d(TAG, "Preferences SortBy: " + sortBy);
        Log.d(TAG, "Preferences favorite: " + favouriteSelected);

        if(!favouriteSelected) {
            WebService webService = new WebService();
            List<Movie> movies = webService.getMovies(sortBy);

            Vector<ContentValues> cVVector = new Vector<ContentValues>(movies.size());
            for (int i = 0; i < movies.size(); i++) {
                Movie movie = movies.get(i);
                ContentValues values = movie.getAsContentValues();
                cVVector.add(values);
            }

            // add to database
            int inserted = 0;
            if (cVVector.size() > 0) {
                ContentValues[] contentValues = new ContentValues[cVVector.size()];
                cVVector.toArray(contentValues);
                inserted = mContext.getContentResolver().bulkInsert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
            }
            Log.d(TAG, "FetchMovieTask Complete. " + inserted + " Inserted");
        }
        return null;
    }

}


