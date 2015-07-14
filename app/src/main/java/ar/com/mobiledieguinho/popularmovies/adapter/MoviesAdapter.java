package ar.com.mobiledieguinho.popularmovies.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.List;

import ar.com.mobiledieguinho.popularmovies.Constants;
import ar.com.mobiledieguinho.popularmovies.R;
import ar.com.mobiledieguinho.popularmovies.entity.Movie;

/**
 * Created by Dieguinho on 11/07/2015.
 */
public class MoviesAdapter extends BaseAdapter {
    private List<Movie> movies;
    private Context context;
    private int itemLayout;

    public MoviesAdapter(Context context, int itemLayout, List<Movie> resources){
        this.context = context;
        this.itemLayout = itemLayout;
        this.movies = resources;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return movies.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = (ImageView) convertView;
        if (view == null) {
            view = new ImageView(context);
        }
        view.setAdjustViewBounds(true);
        if(((Movie) getItem(position)).getPosterPath().equals("")){
            view.setImageResource(R.drawable.poster_not_available);
        }else {
            String url = Constants.URL_BASE_MOVIE_DATABASE_IMAGE + Constants.IMAGE_SIZE_185 + ((Movie) getItem(position)).getPosterPath();
            Picasso.with(context).load(url).into(view);
        }
        return view;
    }

//    public void clear(){
//        this.movies.clear();
//        notifyDataSetChanged();
//    }
//
//    public void add(Movie movie){
//        this.movies.add(movie);
//        notifyDataSetChanged();
//    }
//
//    public void addAll(Collection<Movie> movies){
//        this.movies.addAll(movies);
//        notifyDataSetChanged();
//    }
}
