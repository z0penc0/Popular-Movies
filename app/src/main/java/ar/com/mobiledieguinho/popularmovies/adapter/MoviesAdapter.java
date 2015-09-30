package ar.com.mobiledieguinho.popularmovies.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.List;

import ar.com.mobiledieguinho.popularmovies.Constants;
import ar.com.mobiledieguinho.popularmovies.MovieListActivityFragment;
import ar.com.mobiledieguinho.popularmovies.R;
import ar.com.mobiledieguinho.popularmovies.contentprovider.MovieContract;
import ar.com.mobiledieguinho.popularmovies.entity.Movie;

/**
 * Created by Dieguinho on 11/07/2015.
 */
public class MoviesAdapter extends CursorAdapter {
    private List<Movie> movies;
    private Context context;
    private int itemLayout;

    public MoviesAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

//    public MoviesAdapter(Context context, int itemLayout, List<Movie> resources){
//        this.context = context;
//        this.itemLayout = itemLayout;
//        this.movies = resources;
//    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView view = (ImageView) convertView;
//        if (view == null) {
//            view = new ImageView(context);
//        }
//        view.setAdjustViewBounds(true);
//        if(((Movie) getItem(position)).getPosterPath().equals("")){
//            view.setImageResource(R.drawable.poster_not_available);
//        }else {
//            String url = Constants.URL_BASE_MOVIE_DATABASE_IMAGE + Constants.IMAGE_SIZE_185 + ((Movie) getItem(position)).getPosterPath();
//            Picasso.with(context).load(url).into(view);
//        }
//        return view;
//    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_movie, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder)view.getTag();

        int position = cursor.getPosition();

        String posterPath = cursor.getString(MovieListActivityFragment.COLUMN_POSTER_PATH);
        if(TextUtils.isEmpty(posterPath)) {
            viewHolder.posterView.setImageResource(R.drawable.poster_not_available);
        }else {
            String url = Constants.URL_BASE_MOVIE_DATABASE_IMAGE + Constants.IMAGE_SIZE_185 + posterPath;
            Picasso.with(context).load(url).into(viewHolder.posterView);
        }
    }

    public static class ViewHolder {
        public final ImageView posterView;
        public final Movie movie = null;

        public ViewHolder(View view) {
            posterView = (ImageView) view.findViewById(R.id.imageView_movie);
            posterView.setAdjustViewBounds(true);
        }
    }
}
