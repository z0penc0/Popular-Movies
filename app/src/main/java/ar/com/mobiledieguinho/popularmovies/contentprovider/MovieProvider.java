package ar.com.mobiledieguinho.popularmovies.contentprovider;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;


/**
 * Created by Dieguinho on 27/09/2015.
 */
public class MovieProvider extends ContentProvider {
    private static final String TAG = "MovieProvider";
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDbHelper mMovieDbHelper;

    static final int MOVIE = 100;
    static final int MOVIE_ID = 101;
    static final int TRAILER = 200;
    static final int TRAILER_ID = 201;
    static final int REVIEW = 300;
    static final int REVIEW_ID = 301;
    static final int PRODUCTION_COMPANY = 401;
    static final int PRODUCTION_COUNTRY = 501;
    static final int SPOKEN_LANGUAGE = 601;


    private static final SQLiteQueryBuilder sMovieQueryBuilder;

    static{
        sMovieQueryBuilder = new SQLiteQueryBuilder();

        sMovieQueryBuilder.setTables(
                MovieContract.MovieEntry.TABLE_NAME +
                        " LEFT OUTER JOIN " + MovieContract.ProductionCompanyEntry.TABLE_NAME +
                        " ON " + MovieContract.MovieEntry.TABLE_NAME +
                        "." + MovieContract.MovieEntry.COLUMN_ID_PRODUCTION_COMPANY +
                        " = " + MovieContract.ProductionCompanyEntry.TABLE_NAME +
                        "." + MovieContract.ProductionCompanyEntry._ID +

                        " LEFT OUTER JOIN " + MovieContract.ProductionCountryEntry.TABLE_NAME +
                        " ON " + MovieContract.MovieEntry.TABLE_NAME +
                        "." + MovieContract.MovieEntry.COLUMN_ID_PRODUCTION_COUNTRY +
                        " = " + MovieContract.ProductionCountryEntry.TABLE_NAME +
                        "." + MovieContract.ProductionCountryEntry._ID +

                        " LEFT OUTER JOIN " + MovieContract.SpokenLanguageEntry.TABLE_NAME +
                        " ON " + MovieContract.MovieEntry.TABLE_NAME +
                        "." + MovieContract.MovieEntry.COLUMN_ID_SPOKEN_LANGUAGE +
                        " = " + MovieContract.SpokenLanguageEntry.TABLE_NAME +
                        "." + MovieContract.SpokenLanguageEntry.COLUMN_ISO_639_1 +

                        " LEFT OUTER JOIN " + MovieContract.TrailerEntry.TABLE_NAME +
                        " ON " + MovieContract.MovieEntry.TABLE_NAME +
                        "." + MovieContract.MovieEntry.COLUMN_ID_TRAILER +
                        " = " + MovieContract.TrailerEntry.TABLE_NAME +
                        "." + MovieContract.TrailerEntry.COLUMN_SOURCE +

                        " LEFT OUTER JOIN " + MovieContract.ReviewEntry.TABLE_NAME +
                        " ON " + MovieContract.MovieEntry.TABLE_NAME +
                        "." + MovieContract.MovieEntry.COLUMN_ID_REVIEW +
                        " = " + MovieContract.ReviewEntry.TABLE_NAME +
                        "." + MovieContract.ReviewEntry._ID
        );
    }

    private static final String sMovieSelectionById =
            MovieContract.MovieEntry.TABLE_NAME +
                    "." + MovieContract.MovieEntry._ID + " = ? ";

    private static final String[] sTrailersProjection = {
            MovieContract.TrailerEntry.TABLE_NAME + "." + MovieContract.TrailerEntry.COLUMN_NAME    + "," +
            MovieContract.TrailerEntry.TABLE_NAME + "." + MovieContract.TrailerEntry.COLUMN_SOURCE  + "," +
            MovieContract.TrailerEntry.TABLE_NAME + "." + MovieContract.TrailerEntry.COLUMN_TYPE    + "," +
            MovieContract.TrailerEntry.TABLE_NAME + "." + MovieContract.TrailerEntry.COLUMN_SIZE
    };

    private static final String[] sReviewsProjection = {
            MovieContract.ReviewEntry.TABLE_NAME + "." + MovieContract.ReviewEntry._ID        + "," +
            MovieContract.ReviewEntry.TABLE_NAME + "." + MovieContract.ReviewEntry.COLUMN_URL       + "," +
            MovieContract.ReviewEntry.TABLE_NAME + "." + MovieContract.ReviewEntry.COLUMN_CONTENT   + "," +
            MovieContract.ReviewEntry.TABLE_NAME + "." + MovieContract.ReviewEntry.COLUMN_AUTHOR
    };

    static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE, MOVIE);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE + "/#", MOVIE_ID);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_TRAILER, TRAILER);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_TRAILER + "/#", TRAILER_ID);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_REVIEW, REVIEW);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_REVIEW + "/#", REVIEW_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mMovieDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIE:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_ID:
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;
            case TRAILER:
                return MovieContract.TrailerEntry.CONTENT_TYPE;
            case TRAILER_ID:
                return MovieContract.TrailerEntry.CONTENT_ITEM_TYPE;
            case REVIEW:
                return MovieContract.TrailerEntry.CONTENT_TYPE;
            case REVIEW_ID:
                return MovieContract.TrailerEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    private Cursor getMovieByMovieId(Uri uri, String[] projection) {
        long movieId = MovieContract.MovieEntry.getMovieIdFromUri(uri);

        return sMovieQueryBuilder.query(mMovieDbHelper.getReadableDatabase(),
                projection,
                sMovieSelectionById,
                new String[]{Long.toString(movieId)},
                null,
                null,
                null
        );
    }

    private Cursor getTrailersByMovieId(Uri uri, String sortOrder) {
        long movieId = MovieContract.MovieEntry.getMovieIdFromUri(uri);

        return sMovieQueryBuilder.query(mMovieDbHelper.getReadableDatabase(),
                sTrailersProjection,
                sMovieSelectionById,
                new String[]{Long.toString(movieId)},
                null,
                null,
                sortOrder
        );
    }

    private Cursor getTrailerById(Uri uri, String[] projection) {
        long movieId = MovieContract.TrailerEntry.getTrailerIdFromUri(uri);

        return sMovieQueryBuilder.query(mMovieDbHelper.getReadableDatabase(),
                projection,
                sMovieSelectionById,
                new String[]{Long.toString(movieId)},
                null,
                null,
                null
        );
    }

    private Cursor getReviewsByMovieId(Uri uri, String sortOrder) {
        long movieId = MovieContract.MovieEntry.getMovieIdFromUri(uri);

        return sMovieQueryBuilder.query(mMovieDbHelper.getReadableDatabase(),
                sReviewsProjection,
                sMovieSelectionById,
                new String[]{Long.toString(movieId)},
                null,
                null,
                sortOrder
        );
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor = null;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
            {
                retCursor = mMovieDbHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case MOVIE_ID: {
                retCursor = getMovieByMovieId(uri, projection);
                break;
            }

            case TRAILER: {
                retCursor = getTrailersByMovieId(uri, sortOrder);
                break;
            }

            case TRAILER_ID: {
                retCursor = getTrailerById(uri, projection);
                break;
            }

            case REVIEW: {
                retCursor = getReviewsByMovieId(uri, sortOrder);
                break;
            }

            case REVIEW_ID: {
                Log.d(TAG, "Query REVIEW_ID: Not really necessary");
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOVIE: {
                long _id = db.insertWithOnConflict(MovieContract.MovieEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if ( _id > 0 )
                    returnUri = MovieContract.MovieEntry.buildMovieUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TRAILER: {
                long _id = db.insertWithOnConflict(MovieContract.TrailerEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if(_id > 0)
                    returnUri = MovieContract.TrailerEntry.buildTrailerUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case REVIEW: {
                long _id = db.insertWithOnConflict(MovieContract.ReviewEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if(_id > 0)
                    returnUri = MovieContract.ReviewEntry.buildReviewUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case PRODUCTION_COMPANY: {
                long _id = db.insertWithOnConflict(MovieContract.ProductionCompanyEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if(_id > 0)
                    returnUri = MovieContract.ProductionCompanyEntry.buildProductionCompanyUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case PRODUCTION_COUNTRY: {
                long _id = db.insertWithOnConflict(MovieContract.ProductionCountryEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if(_id > 0)
                    returnUri = MovieContract.ProductionCountryEntry.buildProductionCountryUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case SPOKEN_LANGUAGE: {
                long _id = db.insertWithOnConflict(MovieContract.SpokenLanguageEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if(_id > 0)
                    returnUri = MovieContract.SpokenLanguageEntry.buildSpokenLanguageUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        int deletedRows;

        if(null == selection) selection = "1";
        switch (match) {
            case MOVIE: {
                deletedRows = db.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case TRAILER: {
                deletedRows = db.delete(MovieContract.TrailerEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case REVIEW: {
                deletedRows = db.delete(MovieContract.ReviewEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case PRODUCTION_COMPANY: {
                deletedRows = db.delete(MovieContract.ProductionCompanyEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case PRODUCTION_COUNTRY: {
                deletedRows = db.delete(MovieContract.ProductionCountryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case SPOKEN_LANGUAGE: {
                deletedRows = db.delete(MovieContract.SpokenLanguageEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(deletedRows != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return deletedRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        int updatedRows;

        switch (match) {
            case MOVIE: {
                updatedRows = db.update(MovieContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            case TRAILER: {
                updatedRows = db.update(MovieContract.TrailerEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            case REVIEW: {
                updatedRows = db.update(MovieContract.ReviewEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            case PRODUCTION_COMPANY: {
                updatedRows = db.update(MovieContract.ProductionCompanyEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            case PRODUCTION_COUNTRY: {
                updatedRows = db.update(MovieContract.ProductionCountryEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            case SPOKEN_LANGUAGE: {
                updatedRows = db.update(MovieContract.SpokenLanguageEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(updatedRows != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return updatedRows;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIE:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insertWithOnConflict(MovieContract.MovieEntry.TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_REPLACE);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

}
