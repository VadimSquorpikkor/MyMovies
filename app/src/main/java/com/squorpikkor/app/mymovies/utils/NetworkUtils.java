package com.squorpikkor.app.mymovies.utils;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {

    //Учетка: htpps://www.themoviedb.org
    //Squorpikkor
    //uniqueemovie
    //ud@

    //Пример API-запроса https://api.themoviedb.org/3/movie/550?api_key=d65a76f91a0d1e2911264b29198fb636

    //Ключ доступа к API (v4 auth)
    private static final String API_KEY_4 = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNjVhNzZmOTFhMGQxZTI5MTEyNjRiMjkxOThmYjYzNiIsInN1YiI6IjYwNGQwOWMyMWQ1Mzg2MDA1MzEwMTg4MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.m9dxsnrQuxOBPpBxpLwqlVirvZpsNjedEfMuvHZnyDE";

    private static final String BASE_URL = "https://api.themoviedb.org/3/discover/movie";


    private static final String PARAMS_API_KEY = "api_key";
    private static final String PARAMS_LANGUAGE = "language";
    private static final String PARAMS_SORT_BY = "sort_by";
    private static final String PARAMS_PAGE = "page";

    //Ключ API (v3 auth)
    private static final String API_KEY = "d65a76f91a0d1e2911264b29198fb636";
    private static final String LANGUAGE_VALUE = "ru-RU";
    private static final String SORT_BY_POPULARITY = "popularity.desc";
    private static final String SORT_BY_TOP_RATED = "vote_average.desc";

    public static final int POPULARITY = 0;
    public static final int TOP_RATED = 1;

    private static URL buildURL(int sortBy, int page) {
        URL result = null;
        String methodOfSort;
        switch (sortBy) {
            case 0: methodOfSort = SORT_BY_POPULARITY;break;
            case 1: methodOfSort = SORT_BY_TOP_RATED;break;
            default: methodOfSort = SORT_BY_POPULARITY;
        }
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, LANGUAGE_VALUE)
                .appendQueryParameter(PARAMS_SORT_BY, methodOfSort)
                .appendQueryParameter(PARAMS_PAGE, Integer.toString(page))
                .build();
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONObject getJSONFromNetwork(int sortBy, int page) {
        JSONObject result = null;
        URL url = buildURL(sortBy, page);
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class JSONLoadTask extends AsyncTask<URL, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result = null;
            if (urls == null || urls.length == 0) {
                return result;
            }
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) urls[0].openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String line = reader.readLine();
                while (line!=null) {
                    builder.append(line);
                    line = reader.readLine();
                }
                result = new JSONObject(builder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            return result;
        }
    }
}
