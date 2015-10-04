package ar.com.mobiledieguinho.popularmovies.task;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.Vector;

import ar.com.mobiledieguinho.popularmovies.contentprovider.MovieContract;
import ar.com.mobiledieguinho.popularmovies.entity.Review;
import ar.com.mobiledieguinho.popularmovies.entity.Trailer;
import ar.com.mobiledieguinho.popularmovies.ws.WebService;

/**
 * Created by Dieguinho on 29/09/2015.
 */
public class FetchReviewsTask extends AsyncTask<Long, Void, Void> {

    private final String TAG = FetchReviewsTask.class.getSimpleName();

    private final Context mContext;

    public FetchReviewsTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(Long...params) {
        long movieId = params[0];

        WebService webService = new WebService();
        List<Review> reviews = webService.getMovieReviews(movieId);

        Vector<ContentValues> cVVector = new Vector<ContentValues>(reviews.size());
        for (int i = 0; i < reviews.size(); i++) {
            Review review = reviews.get(i);
            ContentValues values = review.asContentValues();
            values.put(MovieContract.ReviewEntry.COLUMN_MOVIE_ID, movieId);
            cVVector.add(values);
        }

        // add to database
        int inserted = 0;
        if (cVVector.size() > 0) {
            ContentValues[] contentValues = new ContentValues[cVVector.size()];
            cVVector.toArray(contentValues);
            inserted = mContext.getContentResolver().bulkInsert(MovieContract.ReviewEntry.CONTENT_URI, contentValues);
        }
        Log.d(TAG, "FetchReviewTask Complete. " + inserted + " Inserted");

        return null;
    }

}


