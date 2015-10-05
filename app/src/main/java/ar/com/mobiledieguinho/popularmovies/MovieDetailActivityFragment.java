package ar.com.mobiledieguinho.popularmovies;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.internal.view.menu.MenuItemImpl;
import android.support.v7.widget.ShareActionProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

import ar.com.mobiledieguinho.popularmovies.contentprovider.MovieContract;
import ar.com.mobiledieguinho.popularmovies.task.FetchReviewsTask;
import ar.com.mobiledieguinho.popularmovies.task.FetchTrailersTask;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private final static String TAG = "MovieDetailActivityFrag";

    final static String MOVIE_DETAIL_ID = "SELECTED_MOVIE_ID";

    private final static int MOVIE_LOADER = 0;
    private final static int MOVIE_TRAILERS_LOADER = 1;
    private final static int MOVIE_REVIEWS_LOADER = 2;

    private LinearLayout mainLayout;
    private ImageView imageViewBackdrop;
    private ImageView imageViewPoster;
    private TextView textViewTitle;
    private TextView textViewGenres;
    private TextView textViewReleaseDate;
    private TextView textViewSynopsis;
    private TextView textViewUserRating;
    private TextView textViewFavourite;

    private LinearLayout trailersLayout;
    private LinearLayout reviewsLayout;

    private View root;
    private long selectedMovieId;
    private ShareActionProvider mShareActionProvider;
    private Uri shareTrailerUri;

    public MovieDetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        selectedMovieId = getActivity().getIntent().getLongExtra("selectedMovieId", 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        FetchTrailersTask fetchTrailersTask = new FetchTrailersTask(getActivity());
        FetchReviewsTask fetchReviewsTask = new FetchReviewsTask(getActivity());
        fetchTrailersTask.execute(selectedMovieId);
        fetchReviewsTask.execute(selectedMovieId);
//        fetchMovieData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            selectedMovieId = arguments.getInt(MovieDetailActivityFragment.MOVIE_DETAIL_ID);
        }

        root = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        mainLayout = (LinearLayout) root.findViewById(R.id.linearLayout_mainLayout);
        trailersLayout = (LinearLayout) root.findViewById(R.id.linearLayout_trailers);
        reviewsLayout = (LinearLayout) root.findViewById(R.id.linearLayout_reviews);
        imageViewBackdrop = (ImageView)root.findViewById(R.id.imageView_backdrop);
        imageViewPoster = (ImageView)root.findViewById(R.id.imageView_poster);
        textViewTitle = (TextView)root.findViewById(R.id.textView_title);
        textViewReleaseDate = (TextView)root.findViewById(R.id.textView_release_date);
        textViewSynopsis = (TextView)root.findViewById(R.id.textView_synopsis);
        textViewUserRating = (TextView)root.findViewById(R.id.textView_user_rating);
        textViewFavourite = (TextView)root.findViewById(R.id.textView_favourite);

        imageViewBackdrop.setAdjustViewBounds(true);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(MOVIE_LOADER, savedInstanceState, this);
        getLoaderManager().initLoader(MOVIE_TRAILERS_LOADER, savedInstanceState, this);
        getLoaderManager().initLoader(MOVIE_REVIEWS_LOADER, savedInstanceState, this);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        getActivity().getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
//        MenuItem item = menu.findItem(R.id.menu_item_share);
//
//        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
//        if (shareTrailerUri != null) {
//            mShareActionProvider.setShareIntent(createShareTrailerIntent());
//        }
//    }

    private Intent createShareTrailerIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.setData(shareTrailerUri);
        return shareIntent;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch(id){
            case MOVIE_LOADER:
                Uri moviesUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, selectedMovieId);
                return new CursorLoader(getActivity(),
                        moviesUri,
                        MovieContract.MovieEntry.ALL_COLUMNS,
                        null,
                        null,
                        null);
            case MOVIE_TRAILERS_LOADER:
                Uri trailersUri = MovieContract.TrailerEntry.CONTENT_URI.buildUpon().appendPath(MovieContract.PATH_MOVIE).appendQueryParameter(MovieContract.TrailerEntry.COLUMN_MOVIE_ID, String.valueOf(selectedMovieId)).build();
                return new CursorLoader(getActivity(),
                        trailersUri,
                        MovieContract.TrailerEntry.ALL_COLUMNS,
                        null,
                        null,
                        null);
            case MOVIE_REVIEWS_LOADER:
                Uri reviewsUri = MovieContract.ReviewEntry.CONTENT_URI.buildUpon().appendPath(MovieContract.PATH_MOVIE).appendQueryParameter(MovieContract.TrailerEntry.COLUMN_MOVIE_ID, String.valueOf(selectedMovieId)).build();
                return new CursorLoader(getActivity(),
                        reviewsUri,
                        MovieContract.ReviewEntry.ALL_COLUMNS,
                        null,
                        null,
                        null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
        LayoutInflater inflater;
        switch(loader.getId()){
            case MOVIE_LOADER:
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
                break;
            case MOVIE_TRAILERS_LOADER:
                Log.d(TAG, "Buscando los trailers...cantidad: " + data.getCount());
                if (!data.moveToFirst() || data.getCount() < 1) {
                    return;
                }

                inflater = getActivity().getLayoutInflater();
                View trailerView;

                trailersLayout.removeAllViewsInLayout();
                for(int i = 0; i < data.getCount(); i++){
                    String source = data.getString(MovieContract.TrailerEntry.COLUMN_INDEX_SOURCE);
                    final Uri trailerUri = Uri.parse("http://www.youtube.com/watch?v=" + source);
                    if(i == 0) shareTrailerUri = trailerUri;
                    trailerView = inflater.inflate(R.layout.item_trailer, null);
                    ImageView imageViewTrailerIcon = (ImageView)trailerView.findViewById(R.id.imageView_trailer_icon);
                    TextView textViewTrailerTitle = (TextView)trailerView.findViewById(R.id.textView_title);

                    Picasso.with(getActivity()).load("http://img.youtube.com/vi/" + source + "/0.jpg").into(imageViewTrailerIcon);
                    textViewTrailerTitle.setText(data.getString(MovieContract.TrailerEntry.COLUMN_INDEX_NAME) + " - " + data.getString(MovieContract.TrailerEntry.COLUMN_INDEX_SIZE));
                    trailerView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(Intent.ACTION_VIEW, trailerUri));
                        }
                    });
                    trailersLayout.addView(trailerView);
                    data.moveToNext();
                }
                break;
            case MOVIE_REVIEWS_LOADER:
                Log.d(TAG, "Buscando las reviews...");
                if (!data.moveToFirst() || data.getCount() < 1) {
                    return;
                }

                inflater = getActivity().getLayoutInflater();
                View reviewView;

                reviewsLayout.removeAllViewsInLayout();
                for(int i = 0; i < data.getCount(); i++){
                    String author = data.getString(MovieContract.ReviewEntry.COLUMN_INDEX_AUTHOR);
                    String content = data.getString(MovieContract.ReviewEntry.COLUMN_INDEX_CONTENT);

                    reviewView = inflater.inflate(R.layout.item_review, null);
                    TextView textViewContent = (TextView)reviewView.findViewById(R.id.textView_content);
                    TextView textViewAuthor = (TextView)reviewView.findViewById(R.id.textView_author);

                    textViewContent.setText(content);
                    textViewAuthor.setText(author);
                    reviewsLayout.addView(reviewView);
                    data.moveToNext();
                }
                break;
            default:
                Log.e(TAG, "No Loader with ID " + loader.getId() + " found.");
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) { }
}
