package ar.com.mobiledieguinho.popularmovies;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import ar.com.mobiledieguinho.popularmovies.contentprovider.MovieContract;


public class MovieListActivity extends ActionBarActivity {
    private final static String TAG = MovieListActivity.class.getSimpleName();
    private final int MENU_SORT_BY_POPULARITY = 0;
    private final int MENU_SORT_BY_USER_RATING = 1;
    private final int MENU_FAVOURITES_BY_POPULARITY = 2;
    private final int MENU_FAVOURITES_BY_USER_RATING= 3;

    private final String SORT_BY_POPULARITY = "popularity.desc";
    private final String SORT_BY_USER_RATING= "vote_average.desc";

    private String selection;
    private String[] selectionArgs;
    private String sortBy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        sortBy = SORT_BY_POPULARITY;
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_movie_list, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            Intent intentSettings = new Intent(this, SettingsActivity.class);
////            startActivity(intentSettings);
////            return true;
////        }
//
//        switch(id){
//            case R.id.action_user_rating:
//                setFavouriteSelection(false);
//                sortBy = SORT_BY_USER_RATING;
//                break;
//            case R.id.action_popularity:
//                setFavouriteSelection(false);
//                sortBy = SORT_BY_POPULARITY;
//                break;
//            case R.id.action_favourites_popularity:
//                setFavouriteSelection(true);
//                sortBy = SORT_BY_POPULARITY;
//                break;
//            case R.id.action_favourites_user_rating:
//                setFavouriteSelection(true);
//                sortBy = SORT_BY_USER_RATING;
//                break;
//            default:
//                Log.d(TAG, "Option not available, menu action: " + id);
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    private void setFavouriteSelection(boolean favourited){
//        if(favourited) {
//            selection = MovieContract.MovieEntry.COLUMN_FAVOURITE + "=?";
//            selectionArgs = new String[]{String.valueOf(true)};
//        }else{
//            selection = null;
//            selectionArgs = null;
//        }
//    }
}
