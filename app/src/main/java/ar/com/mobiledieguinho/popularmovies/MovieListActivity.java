package ar.com.mobiledieguinho.popularmovies;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MovieListActivity extends ActionBarActivity implements MovieListActivityFragment.Callback{
    private final static String TAG = MovieListActivity.class.getSimpleName();
    private static final String MOVIE_DETAIL_ACTIVITY_FRAGMENT_TAG = "MDAF";
    private boolean mTwoPane = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        if (findViewById(R.id.fragment_movie_detail_container) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public void onItemSelected(int movieId) {
        Bundle bundle = new Bundle();
        bundle.putInt(MovieDetailActivityFragment.MOVIE_DETAIL_ID, movieId);
        if (mTwoPane) {
            MovieDetailActivityFragment fragment = new MovieDetailActivityFragment();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_movie_detail_container, fragment, MOVIE_DETAIL_ACTIVITY_FRAGMENT_TAG)
                    .commit();
        }else{
            Intent detailIntent = new Intent(this, MovieDetailActivity.class);
            detailIntent.putExtras(bundle);
            startActivity(detailIntent);
        }
    }
}
