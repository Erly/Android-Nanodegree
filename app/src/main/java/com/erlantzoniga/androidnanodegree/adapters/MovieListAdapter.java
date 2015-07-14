package com.erlantzoniga.androidnanodegree.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.erlantzoniga.androidnanodegree.R;
import com.erlantzoniga.androidnanodegree.model.MovieBase;
import com.squareup.picasso.Picasso;

/**
 * Created by Erlantz on 13/07/2015.
 */
public class MovieListAdapter extends BaseAdapter {
    private Context mContext;
    private MovieBase[] movies = new MovieBase[0];

    public MovieListAdapter(Context c) {
        mContext = c;
    }

    public MovieListAdapter(Context c, MovieBase[] m) {
        mContext = c;
        movies = m;
    }

    public void setMovies(MovieBase[] movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public MovieBase[] getMovies() {
        return movies;
    }

    @Override
    public int getCount() {
        return movies.length;
    }

    @Override
    public Object getItem(int position) {
        return movies[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView v;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            v = (ImageView) inflater.inflate(R.layout.movie_grid_element, null);
        } else {
            v = (ImageView) convertView;
        }
        Picasso.with(mContext).load(movies[position].posterUri).into(v);

        return v;
    }
}
