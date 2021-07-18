package com.squorpikkor.app.mymovies.utils;

import com.squorpikkor.app.mymovies.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {

    public static final String KEY_RESULTS = "results";
    public static final String KEY_VOTE_COUNT = "vote_count";
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ORIGINAL_TITLE = "original_title";
    public static final String KEY_OVERVIEW = "overview";
    public static final String KEY_POSTER_PATH = "poster_path";
    public static final String KEY_BACKDROP_PATH = "backdrop_path";
    public static final String KEY_VOTE_AVERAGE = "vote_average";
    public static final String KEY_RELEASE_DATE = "release_date";

    private static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p";
    private static final String SMALL_POSTER_SIZE = "w185";
    private static final String BIG_POSTER_SIZE = "w780";

    public static ArrayList<Movie> getMoviesFromJSON(JSONObject jsonObject) {
        ArrayList<Movie> result = new ArrayList<>();
        if (jsonObject == null) return result;
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objectMovie = jsonArray.getJSONObject(i);
                int id = objectMovie.getInt(KEY_ID);
                int voteCount = objectMovie.getInt(KEY_VOTE_COUNT);
                String title = objectMovie.getString(KEY_TITLE);
                String originalTitle = objectMovie.getString(KEY_ORIGINAL_TITLE);
                String overview = objectMovie.getString(KEY_OVERVIEW);
                String posterPath = BASE_POSTER_URL + SMALL_POSTER_SIZE + objectMovie.getString(KEY_POSTER_PATH);
                String bigPosterPath = BASE_POSTER_URL + BIG_POSTER_SIZE + objectMovie.getString(KEY_POSTER_PATH);
                String backDropPath = objectMovie.getString(KEY_BACKDROP_PATH);
                double voteAverage = objectMovie.getDouble(KEY_VOTE_AVERAGE);
                String releaseDate = objectMovie.getString(KEY_RELEASE_DATE);
                result.add(new Movie(id, voteCount, title, originalTitle, overview, posterPath, bigPosterPath, backDropPath, voteAverage, releaseDate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
