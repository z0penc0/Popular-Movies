package ar.com.mobiledieguinho.popularmovies.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ar.com.mobiledieguinho.popularmovies.contentprovider.MovieContract.MovieEntry;
import ar.com.mobiledieguinho.popularmovies.contentprovider.MovieContract.ProductionCompanyEntry;
import ar.com.mobiledieguinho.popularmovies.contentprovider.MovieContract.ProductionCountryEntry;
import ar.com.mobiledieguinho.popularmovies.contentprovider.MovieContract.SpokenLanguageEntry;
import ar.com.mobiledieguinho.popularmovies.contentprovider.MovieContract.TrailerEntry;
import ar.com.mobiledieguinho.popularmovies.contentprovider.MovieContract.ReviewEntry;

/**
 * Created by Dieguinho on 27/09/2015.
 */
public class MovieDbHelper extends SQLiteOpenHelper{

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "movies.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
//                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieEntry._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                MovieEntry.COLUMN_TITLE + " TEXT, " +
                MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT, " +
                MovieEntry.COLUMN_BACKDROP_PATH + " TEXT, " +
                MovieEntry.COLUMN_POSTER_PATH + " TEXT, " +
                MovieEntry.COLUMN_SYNOPSIS + " TEXT, " +
                MovieEntry.COLUMN_RELEASE_DATE + " TEXT, " +
                MovieEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT, " +
                MovieEntry.COLUMN_ADULT+ " INTEGER, " +
                MovieEntry.COLUMN_BUDGET + " LONG, " +
                MovieEntry.COLUMN_HOMEPAGE + " TEXT, " +
                MovieEntry.COLUMN_ID_IMDB + " TEXT, " +
                MovieEntry.COLUMN_POPULARITY + " DOUBLE, " +
                MovieEntry.COLUMN_ID_PRODUCTION_COMPANY + " LONG, " +
                MovieEntry.COLUMN_ID_PRODUCTION_COUNTRY + " LONG, " +
                MovieEntry.COLUMN_REVENUE + " LONG, " +
                MovieEntry.COLUMN_RUNTIME + " LONG, " +
                MovieEntry.COLUMN_ID_SPOKEN_LANGUAGE + " LONG, " +
                MovieEntry.COLUMN_STATUS + " TEXT, " +
                MovieEntry.COLUMN_TAGLINE + " TEXT, " +
                MovieEntry.COLUMN_VIDEO + " INTEGER, " +
                MovieEntry.COLUMN_VOTE_AVERAGE + " DOUBLE, " +
                MovieEntry.COLUMN_VOTE_COUNT + " LONG, " +
                MovieEntry.COLUMN_FAVOURITE + " INTEGER, " +

//                MovieEntry.COLUMN_ID + " INTEGER NOT NULL, " +
//                MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
//                MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
//                MovieEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, " +
//                MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
//                MovieEntry.COLUMN_SYNOPSIS + " TEXT NOT NULL, " +
//                MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
//                MovieEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
//                MovieEntry.COLUMN_ADULT+ " INTEGER NOT NULL, " +
//                MovieEntry.COLUMN_BUDGET + " LONG NOT NULL, " +
//                MovieEntry.COLUMN_HOMEPAGE + " TEXT NOT NULL, " +
//                MovieEntry.COLUMN_ID_IMDB + " TEXT NOT NULL, " +
//                MovieEntry.COLUMN_POPULARITY + " DOUBLE NOT NULL, " +
//                MovieEntry.COLUMN_ID_PRODUCTION_COMPANY + " LONG NOT NULL, " +
//                MovieEntry.COLUMN_ID_PRODUCTION_COUNTRY + " LONG NOT NULL, " +
//                MovieEntry.COLUMN_REVENUE + " LONG NOT NULL, " +
//                MovieEntry.COLUMN_RUNTIME + " LONG NOT NULL, " +
//                MovieEntry.COLUMN_ID_SPOKEN_LANGUAGE + " LONG NOT NULL, " +
//                MovieEntry.COLUMN_STATUS + " TEXT NOT NULL, " +
//                MovieEntry.COLUMN_TAGLINE + " TEXT NOT NULL, " +
//                MovieEntry.COLUMN_VIDEO + " INTEGER NOT NULL, " +
//                MovieEntry.COLUMN_VOTE_AVERAGE + " DOUBLE NOT NULL, " +
//                MovieEntry.COLUMN_VOTE_COUNT + " LONG NOT NULL, " +
//                MovieEntry.COLUMN_FAVOURITE + " INTEGER NOT NULL, " +
//                MovieEntry.COLUMN_ID_TRAILER + " LONG NOT NULL, " +
//                MovieEntry.COLUMN_ID_REVIEW + " LONG NOT NULL); ";

                " FOREIGN KEY (" + MovieEntry.COLUMN_ID_PRODUCTION_COMPANY + ") REFERENCES " +
                ProductionCompanyEntry.TABLE_NAME + " (" + ProductionCompanyEntry._ID + "), " +

                " FOREIGN KEY (" + MovieEntry.COLUMN_ID_PRODUCTION_COUNTRY + ") REFERENCES " +
                ProductionCountryEntry.TABLE_NAME + " (" + ProductionCountryEntry._ID + "), " +

                " FOREIGN KEY (" + MovieEntry.COLUMN_ID_SPOKEN_LANGUAGE + ") REFERENCES " +
                SpokenLanguageEntry.TABLE_NAME + " (" + SpokenLanguageEntry.COLUMN_ISO_639_1 + "), " +

                "UNIQUE (" + MovieEntry._ID + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);

        final String SQL_CREATE_PRODUCTION_COMPANY_TABLE = "CREATE TABLE " + ProductionCompanyEntry.TABLE_NAME + " (" +
                ProductionCompanyEntry._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                ProductionCompanyEntry.COLUMN_NAME+ " TEXT NOT NULL, " +
                " UNIQUE (" + ProductionCompanyEntry._ID + ") ON CONFLICT REPLACE);";
        sqLiteDatabase.execSQL(SQL_CREATE_PRODUCTION_COMPANY_TABLE);

        final String SQL_CREATE_PRODUCTION_COUNTRY_TABLE = "CREATE TABLE " + ProductionCountryEntry.TABLE_NAME + " (" +
                ProductionCountryEntry._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                ProductionCountryEntry.COLUMN_NAME+ " TEXT NOT NULL, " +
                " UNIQUE (" + ProductionCountryEntry._ID + ") ON CONFLICT REPLACE);";
        sqLiteDatabase.execSQL(SQL_CREATE_PRODUCTION_COUNTRY_TABLE);

        final String SQL_CREATE_SPOKEN_LANGUAGE_TABLE = "CREATE TABLE " + SpokenLanguageEntry.TABLE_NAME + " (" +
                SpokenLanguageEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SpokenLanguageEntry.COLUMN_ISO_639_1 + " TEXT NOT NULL, " +
                SpokenLanguageEntry.COLUMN_NAME+ " TEXT NOT NULL, " +
                " UNIQUE (" + SpokenLanguageEntry.COLUMN_ISO_639_1 + ") ON CONFLICT REPLACE);";
        sqLiteDatabase.execSQL(SQL_CREATE_SPOKEN_LANGUAGE_TABLE);

        final String SQL_CREATE_TRAILER_TABLE = "CREATE TABLE " + TrailerEntry.TABLE_NAME + " (" +
                TrailerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TrailerEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                TrailerEntry.COLUMN_SOURCE + " TEXT NOT NULL, " +
                TrailerEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                TrailerEntry.COLUMN_SIZE + " TEXT NOT NULL, " +
                TrailerEntry.COLUMN_TYPE + " TEXT NOT NULL, " +
                " FOREIGN KEY (" + TrailerEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                MovieEntry.TABLE_NAME + " (" + MovieEntry._ID+ "), " +
                " UNIQUE (" + TrailerEntry.COLUMN_SOURCE + ") ON CONFLICT REPLACE);";
        sqLiteDatabase.execSQL(SQL_CREATE_TRAILER_TABLE);

        final String SQL_CREATE_REVIEW_TABLE = "CREATE TABLE " + ReviewEntry.TABLE_NAME + " (" +
                ReviewEntry._ID + " TEXT PRIMARY KEY NOT NULL, " +
                ReviewEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                ReviewEntry.COLUMN_AUTHOR + " TEXT NOT NULL, " +
                ReviewEntry.COLUMN_CONTENT + " TEXT NOT NULL, " +
                ReviewEntry.COLUMN_URL + " TEXT NOT NULL, " +
                " FOREIGN KEY (" + ReviewEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                MovieEntry.TABLE_NAME + " (" + MovieEntry._ID + ")," +
                " UNIQUE (" + ReviewEntry._ID + ") ON CONFLICT REPLACE);";
        sqLiteDatabase.execSQL(SQL_CREATE_REVIEW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ProductionCompanyEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ProductionCountryEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SpokenLanguageEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TrailerEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ReviewEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
