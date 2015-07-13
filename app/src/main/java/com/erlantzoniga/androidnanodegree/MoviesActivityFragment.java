package com.erlantzoniga.androidnanodegree;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.erlantzoniga.androidnanodegree.adapters.MovieListAdapter;
import com.erlantzoniga.androidnanodegree.model.MovieBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesActivityFragment extends Fragment {

    private MovieListAdapter mMovieAdapter;

    public MoviesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        GridView movieGridView = (GridView) view.findViewById(R.id.movies_grid);
        mMovieAdapter = new MovieListAdapter(getActivity());
        movieGridView.setAdapter(mMovieAdapter);
        movieGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra("movie", (MovieBase) mMovieAdapter.getItem(position));
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortByPref = sharedPrefs.getString(getString(R.string.pref_movies_sort_by_key), getString(R.string.pref_movies_sort_by_popularity));
        new FetchMoviesTask().execute(sortByPref);
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, MovieBase[]> {

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();
        private final String MOVIES_BASE_URL = "api.themoviedb.org";
        private final String MOVIES_API_KEY = "26c6918de9c727d0a1fc0addb647a278";

        @Override
        protected MovieBase[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String moviesJsonStr = null;

            try {
                Uri.Builder uriBuilder = new Uri.Builder();
                uriBuilder.scheme("http")
                        .authority(MOVIES_BASE_URL)
                        .appendPath("3")
                        .appendPath("discover")
                        .appendPath("movie")
                        .appendQueryParameter("api_key", MOVIES_API_KEY)
                        .appendQueryParameter("sort_by", params[0]);

                URL url = new URL(uriBuilder.build().toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) return null;

                reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) return null;
                moviesJsonStr = buffer.toString();

                return getMovieArrayFromJson(moviesJsonStr);

            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Error with bad URL", e);
                return null;
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error", e);
                return null;
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error parsing Json", e);
                return null;
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(MovieBase[] result) {
            if (result != null && result.length > 0) {
                mMovieAdapter.setMovies(result);
            }
        }

        private MovieBase[] getMovieArrayFromJson(String moviesJsonStr) throws JSONException {
            final String POSTER_BASE_URL = "image.tmdb.org";
            final String TMDB_LIST = "results";
            final String TMDB_ID = "id";
            final String TMDB_TITLE = "original_title";
            final String TMDB_POSTER_PATH = "poster_path";
            final String TMDB_SYNOPSIS = "overview";
            final String TMDB_RATING = "vote_average";
            final String TMDB_RELEASE_DATE = "release_date";

            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            JSONArray moviesJsonArray = moviesJson.getJSONArray(TMDB_LIST);
            int arraySize = moviesJsonArray.length();

            MovieBase[] moviesArray = new MovieBase[arraySize];

            for(int i = 0; i < arraySize; i++) {
                JSONObject movie = moviesJsonArray.getJSONObject(i);
                String posterPath = movie.getString(TMDB_POSTER_PATH);
                Uri.Builder uriBuilder = new Uri.Builder();
                uriBuilder.scheme("http")
                        .authority(POSTER_BASE_URL)
                        .appendPath("t")
                        .appendPath("p")
                        .appendPath("w342")
                        .appendPath(posterPath.substring(1));
                String synopsis = "";
                if (!movie.isNull(TMDB_SYNOPSIS)) {
                    synopsis = movie.getString(TMDB_SYNOPSIS);
                }

                Date releaseDate = new Date();
                try {
                    if (!movie.isNull(TMDB_RELEASE_DATE)) {
                        releaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(movie.getString(TMDB_RELEASE_DATE));
                    }
                } catch (ParseException e) {
                    Log.e(LOG_TAG, "Error parsing release date", e);
                }

                moviesArray[i] = new MovieBase(movie.getInt(TMDB_ID), movie.getString(TMDB_TITLE),
                        uriBuilder.build(), synopsis, movie.getDouble(TMDB_RATING), releaseDate);
            }

            return moviesArray;
        }
    }
}