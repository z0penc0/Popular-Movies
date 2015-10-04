package ar.com.mobiledieguinho.popularmovies.task;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.Vector;

import ar.com.mobiledieguinho.popularmovies.contentprovider.MovieContract;
import ar.com.mobiledieguinho.popularmovies.entity.Trailer;
import ar.com.mobiledieguinho.popularmovies.entity.TrailersData;
import ar.com.mobiledieguinho.popularmovies.ws.WebService;

/**
 * Created by Dieguinho on 29/09/2015.
 */
public class FetchTrailersTask extends AsyncTask<Long, Void, Void> {

    private final String TAG = FetchTrailersTask.class.getSimpleName();

    private final Context mContext;

    public FetchTrailersTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(Long...params) {
        long movieId = params[0];

        WebService webService = new WebService();
        List<Trailer> trailers = webService.getMovieTrailers(movieId);

        Vector<ContentValues> cVVector = new Vector<ContentValues>(trailers.size());
        for (int i = 0; i < trailers.size(); i++) {
            Trailer trailer = trailers.get(i);
            ContentValues values = trailer.asContentValues();
            values.put(MovieContract.TrailerEntry.COLUMN_MOVIE_ID, movieId);
            cVVector.add(values);
        }

        // add to database
        int inserted = 0;
        if (cVVector.size() > 0) {
            ContentValues[] contentValues = new ContentValues[cVVector.size()];
            cVVector.toArray(contentValues);
            inserted = mContext.getContentResolver().bulkInsert(MovieContract.TrailerEntry.CONTENT_URI, contentValues);
        }
        Log.d(TAG, "FetchTrailersTask Complete. " + inserted + " Inserted");

        return null;
    }

}


