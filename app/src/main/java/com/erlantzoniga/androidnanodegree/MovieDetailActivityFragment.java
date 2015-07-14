package com.erlantzoniga.androidnanodegree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.erlantzoniga.androidnanodegree.model.MovieBase;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {

    public MovieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            MovieBase movie = intent.getExtras().getParcelable("movie");
            ImageView moviePoster = (ImageView) v.findViewById(R.id.movie_poster);
            Picasso.with(getActivity()).load(movie.posterUri).into(moviePoster);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(movie.title);
            TextView movieReleaseDate = (TextView) v.findViewById(R.id.release_date);
            DateFormat df = DateFormat.getDateInstance();
            movieReleaseDate.setText(df.format(movie.releaseDate));
            TextView movieSynopsis = (TextView) v.findViewById(R.id.synopsis);
            movieSynopsis.setText(movie.synopsis);
            RatingBar movieRating = (RatingBar) v.findViewById(R.id.rating);
            movieRating.setRating(((float) movie.rating / 2));
        }

        return v;
    }
}
