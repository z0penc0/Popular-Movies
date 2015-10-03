package ar.com.mobiledieguinho.popularmovies;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MovieListActivity extends ActionBarActivity {
    private final static String TAG = MovieListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
    }
}
