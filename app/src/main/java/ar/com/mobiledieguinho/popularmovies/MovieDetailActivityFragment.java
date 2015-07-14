package ar.com.mobiledieguinho.popularmovies;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ar.com.mobiledieguinho.popularmovies.entity.Movie;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {
    private Movie selectedMovie;
    private View root;

    public MovieDetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedMovie = getActivity().getIntent().getParcelableExtra("selectedMovie");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        ImageView imageViewBackdrop = (ImageView)root.findViewById(R.id.imageView_backdrop);
        ImageView imageViewPoster = (ImageView)root.findViewById(R.id.imageView_poster);
        TextView textViewTitle = (TextView)root.findViewById(R.id.textView_title);
        TextView textViewReleaseDate = (TextView)root.findViewById(R.id.textView_release_date);
        TextView textViewSynopsis = (TextView)root.findViewById(R.id.textView_synopsis);
        TextView textViewUserRating = (TextView)root.findViewById(R.id.textView_user_rating);

        imageViewBackdrop.setAdjustViewBounds(true);
        String urlBackdrop = Constants.URL_BASE_MOVIE_DATABASE_IMAGE + Constants.IMAGE_SIZE_342 + selectedMovie.getBackdropPath();
        Picasso.with(getActivity()).load(urlBackdrop).into(imageViewBackdrop);

        if(selectedMovie.getPosterPath().equals("")){
            imageViewPoster.setImageResource(R.drawable.poster_not_available);
        }else {
            String urlPoster = Constants.URL_BASE_MOVIE_DATABASE_IMAGE + Constants.IMAGE_SIZE_185 + selectedMovie.getPosterPath();
            Picasso.with(getActivity()).load(urlPoster).into(imageViewPoster);
        }

        textViewTitle.setText(this.selectedMovie.getTitle());
        textViewReleaseDate.setText(this.selectedMovie.getReleaseDate());
        textViewSynopsis.setText(this.selectedMovie.getSynopsis());
        textViewUserRating.setText(String.valueOf(this.selectedMovie.getUserRating()));

        return root;
    }
}
